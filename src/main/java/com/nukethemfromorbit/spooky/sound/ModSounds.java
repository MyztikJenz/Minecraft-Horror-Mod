package com.nukethemfromorbit.spooky.sound;

import com.nukethemfromorbit.spooky.Spooky;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
	public static final SoundEvent SPOOKY_INCEPTION = register("inception");

	private ModSounds() {
	}

	private static SoundEvent register(String id) {
		Identifier identifier = Identifier.of(Spooky.MOD_ID, id);
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}

	public static void registerModSounds() {
		// Intentionally empty; class loading registers constants.
	}
}
