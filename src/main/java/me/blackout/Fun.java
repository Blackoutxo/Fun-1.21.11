package me.blackout;

import me.blackout.feature.BlockDownDissapear;
import me.blackout.feature.Zeus;
import me.blackout.util.KeyInput;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.blackout.util.PlayerUtil.LookingAt;

public class Fun implements ClientModInitializer {
	public static Minecraft mc;
	public static final String FUN = "FUN";
	public static final Logger LOGGER = LoggerFactory.getLogger(FUN);


	static {
			LOGGER.info("Hello Fabric world!");
	}

	@Override
	public void onInitializeClient() {

		// Global client accessor
		mc = Minecraft.getInstance();

		//Blocks below vanish
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				// Block Dissapear
				BlockDownDissapear.disappear(player);

				// BEHOLD YE POWER OF THUNDER
				if(KeyInput.zeusT) Zeus.strike();
			}
		});

		// Register key inputs
		KeyInput.register();
	}
}