package me.blackout.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static me.blackout.Fun.mc;
import static me.blackout.util.PlayerUtil.LookingAt;

public class Zeus {
    public static void strike() {
        if (mc.mouseHandler.isLeftPressed()) {
            spawnLightning(LookingAt().getX(), LookingAt().getY(), LookingAt().getZ());
        }
    }

    private static void spawnLightning(double x, double y, double z) {
        LightningBolt lightning;
        if (mc.level != null) {
            lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, mc.level);
            lightning.setPos(x, y, z);
            lightning.setPosRaw(x, y, z);
            mc.level.addEntity(lightning);
        }
    }
}
