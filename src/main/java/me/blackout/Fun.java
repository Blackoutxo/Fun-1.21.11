package me.blackout;

import me.blackout.feature.Zeus;
import me.blackout.util.PlayerUtil;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fun implements ClientModInitializer {
	public static MinecraftClient mc;
	public static final String FUN = "FUN";
	public static final Logger LOGGER = LoggerFactory.getLogger(FUN);


	static {
			LOGGER.info("Hello Fabric world!");
	}

	@Override
	public void onInitializeClient() {

		// Global client accessor
		mc = MinecraftClient.getInstance();

		// ZEUS!!!!
		Zeus.strike();

		// check them messages
		PlayerUtil.onMessageSent();
	}
}