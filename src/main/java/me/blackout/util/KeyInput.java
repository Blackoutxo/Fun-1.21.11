package me.blackout.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.player.Input;
import org.lwjgl.glfw.GLFW;

import static com.sun.jna.platform.KeyboardUtils.isPressed;
import static me.blackout.Fun.mc;

public class KeyInput {
    public static final KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("fun", "fun"));
    public static final String KEY_ZEUS = "Zeus";
    public static final String KEY_FIREBALL = "Fireball";

    public static KeyMapping block_vanish, zeus, fireball;
    public static boolean blockVanish, zeusT, fireballG;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            zeusT = zeus.isDown();
        });
    }

    public static void register() {
        zeus = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_ZEUS,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                category
        ));

        registerKeyInputs();
    }


}
