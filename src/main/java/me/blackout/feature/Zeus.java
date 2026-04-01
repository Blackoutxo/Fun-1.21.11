package me.blackout.feature;

import me.blackout.util.PlayerUtil;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class Zeus {
    public static void strike() {
        UseItemCallback.EVENT.register(((playerEntity, world, hand) -> {
            ItemStack stack = playerEntity.getStackInHand(hand);

            String itemName = stack.getName().getString();

            boolean isStick = stack.getItem() == Items.STICK;
            boolean hasCustomName = itemName.equals("Zeus");
            System.out.println(itemName);

            if (isStick && hasCustomName && !world.isClient()) {

                // Cool-Down
                if (playerEntity.getItemCooldownManager().isCoolingDown(stack)) {
                    return ActionResult.PASS;
                }

                // Create the entity
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);

                // Update and set position
                lightning.updatePosition(PlayerUtil.LookingAt().getX(), PlayerUtil.LookingAt().getY(), PlayerUtil.LookingAt().getZ());
                lightning.setPos(PlayerUtil.LookingAt().getX(), PlayerUtil.LookingAt().getY(), PlayerUtil.LookingAt().getZ());

                // Spawn the entity with explosion
                world.spawnEntity(lightning);
                world.createExplosion(lightning, lightning.getX(), lightning.getY(), lightning.getZ(), 2, World.ExplosionSourceType.TNT);

                // Play sound
                world.playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.WEATHER);

                // Add the Cool-down time
                playerEntity.getItemCooldownManager().set(stack, 20);

                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        }));
    }
}
