package com.nukethemfromorbit.spooky.sound;

import com.nukethemfromorbit.spooky.Spooky;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
	public static final SoundEvent SPOOKY_INCEPTION = register("inception");
	public static final SoundEvent SPOOKY_I_SEE_YOU = register("i_see_you");
	public static final SoundEvent SPOOKY_ANGRY_GHOST_VOICE = register("angry_ghost_voice");
	public static final SoundEvent SPOOKY_CREEPY_GHOST_SCREAM = register("creepy_ghost_scream");
	public static final SoundEvent SPOOKY_GHOST_BREATH = register("ghost_breath");
	public static final SoundEvent SPOOKY_CRISPY_STATIC = register("crispy_static");
	public static final SoundEvent SPOOKY_HORROR_BACKGROUND_ATMOSPHERE = register("horror_background_atmosphere");
	public static final SoundEvent SPOOKY_SCARY_MUSIC_BOX = register("scary_music_box");
	public static final SoundEvent SPOOKY_RADIO_STATIC = register("radio_static");
	public static final SoundEvent SPOOKY_RUSTY_SWINGS = register("rusty_swings");
	public static final SoundEvent SPOOKY_AVOID = register("avoid");
	public static final SoundEvent SPOOKY_FEMALE_HORROR_LAUGHTER = register("female_horror_laughter");
	public static final SoundEvent SPOOKY_FLESH_GROWING = register("flesh_growing");
	public static final SoundEvent SPOOKY_OPEN_THE_DOOR = register("open_the_door");
	public static final SoundEvent SPOOKY_QUICK_SCREECH = register("quick_screech");
	public static final SoundEvent SPOOKY_COMBINED_SPOOKY_RECIPES = register("combined_spooky_recipes");
	public static final SoundEvent SPOOKY_PREPARE_TO_DIE = register("prepare_to_die");
	public static final SoundEvent SPOOKY_THUNDER_CRACK = register("thunder_crack");
	public static final SoundEvent SPOOKY_LOL_MILK = register("lol_milk");
	public static final SoundEvent SPOOKY_NIGHTTIME_THUNDER = register("nighttime_thunder");
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
