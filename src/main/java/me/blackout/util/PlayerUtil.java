package me.blackout.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import static me.blackout.Fun.mc;

public class PlayerUtil {
    public static BlockPos LookingAt(){
        HitResult rt = mc.hitResult;

        double x = (rt.getLocation().x);
        double y = (rt.getLocation().y);
        double z = (rt.getLocation().z);

        double xla = Minecraft.getInstance().player.getLookAngle().x;
        double yla = Minecraft.getInstance().player.getLookAngle().y;
        double zla = Minecraft.getInstance().player.getLookAngle().z;

        if ((x%1==0)&&(xla<0))x-=0.01;
        if ((y%1==0)&&(yla<0))y-=0.01;
        if ((z%1==0)&&(zla<0))z-=0.01;

        BlockPos ps = new BlockPos((int) x,(int) y,(int) z);
        BlockState bl = Minecraft.getInstance().level.getBlockState(ps);

        return ps;
    }
}
