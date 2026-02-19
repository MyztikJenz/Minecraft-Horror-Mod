package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import com.nukethemfromorbit.spooky.sound.client.CaveLoopSound;
import com.nukethemfromorbit.spooky.sound.client.CaveLoopState;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeEffectSoundPlayer.class)
public class BiomeEffectSoundPlayerMixin {
	private static final float MOOD_SPEED_MULTIPLIER = 2.0F;
	private static final float MOOD_WARNING_THRESHOLD = 0.7F;

	@Shadow
	private float moodPercentage;
	@Shadow
	private Optional<BiomeMoodSound> moodSound;
	@Shadow
	private ClientPlayerEntity player;
	@Shadow
	private SoundManager soundManager;
	@Shadow
	private Random random;
	@Unique
	private boolean spooky$playedMoodWarning;
	@Unique
	private CaveLoopSound spooky$caveLoop;

//	@Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/sound/BiomeMoodSound;getCultivationTicks()I"))
//	private int spooky$boostMoodCultivation(BiomeMoodSound sound) {
//		int base = sound.getCultivationTicks();
//		int boosted = Math.max(1, (int)(base / MOOD_SPEED_MULTIPLIER));
//		return boosted;
//	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void spooky$playMoodWarning(CallbackInfo ci) {
		if (moodSound.isEmpty()) {
			spooky$updateCaveLoop();
			return;
		}

		if (moodPercentage >= MOOD_WARNING_THRESHOLD) {
			if (!spooky$playedMoodWarning) {
				soundManager.play(
					new PositionedSoundInstance(ModSounds.SPOOKY_CREEPY_GHOST_SCREAM, SoundCategory.AMBIENT, 1.0f, 1.0f, random, player.getX(), player.getY(), player.getZ())
				);
				spooky$playedMoodWarning = true;
			}
		} else {
			spooky$playedMoodWarning = false;
		}

		spooky$updateCaveLoop();
	}

	@Unique
	private void spooky$updateCaveLoop() {
		boolean inCaveArea = spooky$isInCaveArea();
		CaveLoopState.setActive(inCaveArea);
		if (inCaveArea) {
			if (spooky$caveLoop == null || spooky$caveLoop.isDone()) {
				spooky$caveLoop = new CaveLoopSound(player);
				soundManager.play(spooky$caveLoop);
			}
		} else if (spooky$caveLoop != null) {
			soundManager.stop(spooky$caveLoop);
			spooky$caveLoop.stop();
			spooky$caveLoop = null;
		}
	}

	@Unique
	private boolean spooky$isInCaveArea() {
		World world = player.getWorld();
		if (world.getRegistryKey() != World.OVERWORLD) {
			return false;
		}

		BlockPos pos = BlockPos.ofFloored(player.getX(), player.getEyeY(), player.getZ());
		return world.getLightLevel(LightType.SKY, pos) <= 0;
	}

}
