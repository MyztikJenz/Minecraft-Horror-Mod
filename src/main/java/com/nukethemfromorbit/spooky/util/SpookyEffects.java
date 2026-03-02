package com.nukethemfromorbit.spooky.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public final class SpookyEffects {
	private static final int EFFECT_DURATION_TICKS = 2000;
	private static final int EFFECT_AMPLIFIER = 0;
	private static final int EFFECT_SPACING_TICKS = 20;

	private SpookyEffects() {
	}

	public static void applyCraftingTableEffects(ServerPlayerEntity player) {
		int delayTicks = 0;
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.LEVITATION);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.WITHER);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.BLINDNESS);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.ABSORPTION);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.BAD_OMEN);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.CONDUIT_POWER);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.DARKNESS);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.DOLPHINS_GRACE);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.FIRE_RESISTANCE);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.GLOWING);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.HASTE);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.HEALTH_BOOST);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.HERO_OF_THE_VILLAGE);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.HUNGER);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.INFESTED);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.INVISIBILITY);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.JUMP_BOOST);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.LUCK);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.MINING_FATIGUE);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.NAUSEA);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.NIGHT_VISION);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.OOZING);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.POISON);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.RAID_OMEN);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.REGENERATION);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.RESISTANCE);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.SLOW_FALLING);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.SLOWNESS);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.SPEED);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.STRENGTH);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.TRIAL_OMEN);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.UNLUCK);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.WATER_BREATHING);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.WEAKNESS);
		delayTicks = applyCraftingTableEffect(player, delayTicks, StatusEffects.WEAVING);
		applyCraftingTableEffect(player, delayTicks, StatusEffects.WIND_CHARGED);
	}

	private static int applyCraftingTableEffect(ServerPlayerEntity player, int delayTicks, RegistryEntry<StatusEffect> effect) {
		ServerScheduler.schedule(delayTicks, () -> {
			if (player.isRemoved()) {
				return;
			}
			player.addStatusEffect(new StatusEffectInstance(effect, EFFECT_DURATION_TICKS, EFFECT_AMPLIFIER, false, true, true));
		});
		return delayTicks + EFFECT_SPACING_TICKS;
	}
}
