package me.blackout.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.logging.Logger;

@Mixin(Item.class)
public class ItemFireChargeItemMixin {

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void use(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if ((Object)this instanceof FireChargeItem) {
            ItemStack itemStack = user.getItemInHand(hand);
            world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), SoundEvents.GHAST_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide()) {

                //float vec3d = user.getPreciseBodyRotation(1.0F);
                LargeFireball fireballEntity = new LargeFireball(EntityType.FIREBALL, world);
                fireballEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
                fireballEntity.setPos(user.getX(), user.getY(0.5) /*+ 0.5*/, user.getZ());
                world.addFreshEntity(fireballEntity);
            }
            user.awardStat(Stats.ITEM_USED.get((FireChargeItem) (Object) this));
            if (!user.getAbilities().invulnerable) {
                itemStack.shrink(1);
            }
            cir.setReturnValue(InteractionResult.SUCCESS.heldItemTransformedTo(itemStack));
        }
    }
}
