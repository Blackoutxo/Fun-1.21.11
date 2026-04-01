package me.blackout.mixin;

import net.minecraft.item.FireChargeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireChargeItem.class)
public class FireChargeItemMixin {
    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    public void useOnBlock(ItemUsageContext useOnContext, CallbackInfoReturnable<ActionResult> cir) {
        cir.setReturnValue(ActionResult.PASS);
    }
}
