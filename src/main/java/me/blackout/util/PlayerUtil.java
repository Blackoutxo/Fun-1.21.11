package me.blackout.util;

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static me.blackout.Fun.mc;

public class PlayerUtil {
    public static String message;

    public static void onMessageSent() {
        ClientSendMessageEvents.CHAT.register(messageL -> {
            message = messageL;
        });
    }

    public static BlockPos LookingAt(){
        HitResult rt = mc.crosshairTarget;

        double x = (rt.getPos().x);
        double y = (rt.getPos().y);
        double z = (rt.getPos().z);

        double xla = MinecraftClient.getInstance().crosshairTarget.getPos().x;
        double yla = MinecraftClient.getInstance().crosshairTarget.getPos().y;
        double zla = MinecraftClient.getInstance().crosshairTarget.getPos().z;

        if ((x%1==0)&&(xla<0))x-=0.01;
        if ((y%1==0)&&(yla<0))y-=0.01;
        if ((z%1==0)&&(zla<0))z-=0.01;

        BlockPos ps = new BlockPos((int) x,(int) y,(int) z);
        BlockState bl = MinecraftClient.getInstance().world.getBlockState(ps);

        return ps;
    }
}
