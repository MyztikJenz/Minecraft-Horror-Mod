package com.nukethemfromorbit.spooky.mixin;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.nukethemfromorbit.spooky.sound.ModSounds;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {
	private static final Identifier ENTER_THE_END = Identifier.of("minecraft", "story/enter_the_end");
	private static final int END_BLINDNESS_DURATION_TICKS = 2000;
	private static final int END_BLINDNESS_AMPLIFIER = 0;

	@Shadow
	private ServerPlayerEntity owner;

	@Inject(method = "grantCriterion", at = @At("TAIL"))
	private void spooky$onGrantCriterion(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		if (!ENTER_THE_END.equals(advancement.id())) {
			return;
		}

		AdvancementProgress progress = ((PlayerAdvancementTracker)(Object)this).getProgress(advancement);
		if (!progress.isDone()) {
			return;
		}

//		owner.addStatusEffect(
//			new StatusEffectInstance(StatusEffects.BLINDNESS, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true)
//		);
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.INFESTED, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.OOZING, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.RAID_OMEN, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.UNLUCK, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAVING, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.WIND_CHARGED, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));
		owner.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, END_BLINDNESS_DURATION_TICKS, END_BLINDNESS_AMPLIFIER, false, true, true));

//		owner.playSoundToPlayer(SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.PLAYERS, 1.0f, 0.8f);
		owner.playSoundToPlayer(ModSounds.SPOOKY_INCEPTION, SoundCategory.PLAYERS, 1.0f, 1.0f);
	}
}
