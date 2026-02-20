package com.nukethemfromorbit.spooky;

import com.nukethemfromorbit.spooky.item.ModItems;
import com.nukethemfromorbit.spooky.sound.ModSounds;
import com.nukethemfromorbit.spooky.util.ServerScheduler;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Spooky implements ModInitializer {
	public static final String MOD_ID = "spooky";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();
		ModSounds.registerModSounds();
		ServerScheduler.register();
	}
}
