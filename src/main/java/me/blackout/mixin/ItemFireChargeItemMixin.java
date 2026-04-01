package me.blackout.mixin;

import me.blackout.util.PlayerUtil;
import me.blackout.util.StringSeperator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.blackout.Fun.mc;

@Mixin(Item.class)
public class ItemFireChargeItemMixin {

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if ((Object)this instanceof FireChargeItem) {
            ItemStack itemStack = user.getStackInHand(hand);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

            // Get Input for Explosions from chat
            int explosion = 3;
            String m = PlayerUtil.message == null ? "3" : PlayerUtil.message.toUpperCase();
            if (StringSeperator.letters(m).equals("FIREBALLSET") && !m.isEmpty()) {
                explosion = StringSeperator.numbers(m);
                mc.player.sendMessage(Text.of("Boom " + explosion), true);
            }

            if (!world.isClient()) {

                Vec3d vec3d = user.getRotationVec(1.0F);
                FireballEntity fireballEntity = new FireballEntity(world, user, vec3d,explosion);

                // Set position & velocity
                fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                fireballEntity.setPosition(user.getX(), user.getBodyY(0.5)/*+ 0.5*/, user.getZ());

                // Spawn the FIREBALL
                world.spawnEntity(fireballEntity);

                // Fix stats
                user.incrementStat(Stats.USED.getOrCreateStat((FireChargeItem) (Object) this));
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                cir.setReturnValue(ActionResult.SUCCESS.withNewHandStack(itemStack));

            } else {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }
}

