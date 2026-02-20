package com.nukethemfromorbit.spooky.util;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public final class SpookyEffects {
	private static final int EFFECT_DURATION_TICKS = 2000;
	private static final int EFFECT_AMPLIFIER = 0;

	private SpookyEffects() {
	}

	public static void applyCraftingTableEffects(ServerPlayerEntity player) {
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.INFESTED, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.OOZING, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.RAID_OMEN, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.UNLUCK, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAVING, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.WIND_CHARGED, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
	}
}
