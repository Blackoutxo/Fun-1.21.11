package me.blackout.mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireChargeItem.class)
public class FireChargeItemMixin {
    @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
    public void useOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        cir.setReturnValue(InteractionResult.PASS);
    }
}
