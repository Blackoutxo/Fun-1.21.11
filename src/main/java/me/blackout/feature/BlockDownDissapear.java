package me.blackout.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import static me.blackout.Fun.mc;

public class BlockDownDissapear {
    private static int timer;

    public static void disappear(Player player) {
        if (player.onGround()) {
            Level world = player.level();

            // Position
            if (mc.player != null) {

                if (timer >= 20) {
                    int x = mc.player.getBlockX();
                    int y = mc.player.getBlockY();
                    int z = mc.player.getBlockZ();

                    BlockPos pos = new BlockPos(x, y, z).below();

                    if (!world.getBlockState(pos).isAir()
                            || !world.getBlockState(pos.east()).isAir()
                            || !world.getBlockState(pos.west()).isAir()
                            || !world.getBlockState(pos.north()).isAir()
                            || !world.getBlockState(pos.south()).isAir()

                            || !world.getBlockState(pos.east().north()).isAir()
                            || !world.getBlockState(pos.east().south()).isAir()
                            || !world.getBlockState(pos.west().north()).isAir()
                            || !world.getBlockState(pos.west().south()).isAir()) {
                        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                        // Cardinal Directions
                        world.setBlockAndUpdate(pos.east(), Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(pos.west(), Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(pos.north(), Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(pos.south(), Blocks.AIR.defaultBlockState());

                        // Diagonal
                        world.setBlockAndUpdate(pos.east().south(), Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(pos.east().north(), Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(pos.west().south(), Blocks.AIR.defaultBlockState());
                        world.setBlockAndUpdate(pos.west().north(), Blocks.AIR.defaultBlockState());
                    }
                    timer = 0;
                }
                timer++;
            }
        }
    }
}
