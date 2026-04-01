package me.blackout.mixin;

import me.blackout.util.PlayerUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticlesMode;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

import static me.blackout.Fun.mc;

@Mixin(BowItem.class)
public class RailGunBowMixin {
    @Unique
    private static float progress;
    @Inject(at = @At("HEAD"), method = "getPullProgress", cancellable = true)
    private static void progressPull(int i, CallbackInfoReturnable<Float> cir) {
        progress = (i * 100.0f) / 20.0f;

        cir.setReturnValue(progress);
    }

    @Inject(at = @At("HEAD"), method = "onStoppedUsing", cancellable = true)
    public void onShoot(ItemStack itemStack, World world, LivingEntity player, int i, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof PlayerEntity && !world.isClient()) {

            // Items
            ItemStack bowItem = player.getStackInHand(((PlayerEntity) player).getActiveHand());
            ItemStack arrowStack = player.getProjectileType(itemStack);
            ArrowItem arrowItem = (ArrowItem) (arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);

            // Create the arrow
            PersistentProjectileEntity arrow = arrowItem.createArrow(world, arrowStack, player, null);

            // Conditions
            boolean isBow = bowItem.getItem() == Items.BOW;

            if (isBow) {

                if (((PlayerEntity) player).getItemCooldownManager().isCoolingDown(bowItem)) {
                    ((PlayerEntity) player).sendMessage(Text.literal("Rail gun is recharging..."), true);
                    cir.setReturnValue(false); // Cancel the shot
                    return;
                }

                // set velocity and have perfect accuracy
                arrow.setVelocity(arrow, player.getPitch(), player.getYaw(), 0.0F, 30.0f, 0);
                arrow.setNoGravity(true);

                // Set sound
                world.playSound(null, arrow.getBlockPos(), SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.NEUTRAL);

                // Add particles
                spawnParticles(player, world);

                // add knockback to player
                Vec3d lookDir = player.getRotationVec(1.0F);

                double strength = -0.6;
                player.addVelocity(lookDir.x * strength, 0.2, lookDir.z * strength);

                // Add cooldown
                ((PlayerEntity) player).getItemCooldownManager().set(bowItem, 100);

                //Send server packet
                if (player instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));
                }

                // 100% damage lol
                arrow.setDamage(100.0);
            }
        }
    }

    public void spawnParticles(LivingEntity player, World world) {
        // Create a line of particles
        Vec3d start = player.getEyePos();
        Vec3d direction = player.getRotationVec(1.0F);

        for (int j = 0; j < 50; j++) { // Draw for 50 blocks
            Vec3d point = start.add(direction.multiply(j));
            // Trail
            ((ServerWorld)world).spawnParticles(
                    ParticleTypes.ELECTRIC_SPARK,
                    point.x, point.y, point.z,
                    1, 0, 0, 0, 0.0
            );
        }

        for (int i = 0; i < 3; i++) {
            Vec3d point = start.add(direction.multiply(i));

            // Create few sonic booms
            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.SONIC_BOOM,
                    point.x, point.y, point.z,
                    1, 0, 0, 0, 0.0
            );
        }
    }
}
