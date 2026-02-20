package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import com.nukethemfromorbit.spooky.sound.client.CaveLoopSound;
import com.nukethemfromorbit.spooky.sound.client.CaveLoopState;
import java.util.Optional;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeEffectSoundPlayer.class)
public class BiomeEffectSoundPlayerMixin {
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
	private CaveLoopSound spooky$caveStaticLoop;
	@Unique
	private CaveLoopSound spooky$caveMusicBoxLoop;
	@Unique
	private CaveLoopSound spooky$netherAtmosphereLoop;
	@Unique
	private CaveLoopSound spooky$netherStaticLoop;
	@Unique
	private CaveLoopSound spooky$endStaticLoop;
	@Unique
	private CaveLoopSound spooky$endRadioStaticLoop;
	@Unique
	private CaveLoopSound spooky$endRustySwingsLoop;

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
			spooky$caveStaticLoop = spooky$ensureLoop(spooky$caveStaticLoop, ModSounds.SPOOKY_CRISPY_STATIC, 0.7f, 1.0f);
			spooky$caveMusicBoxLoop = spooky$ensureLoop(spooky$caveMusicBoxLoop, ModSounds.SPOOKY_SCARY_MUSIC_BOX, 0.7f, 1.0f);
		} else {
			spooky$stopLoop(spooky$caveStaticLoop);
			spooky$stopLoop(spooky$caveMusicBoxLoop);
			spooky$caveStaticLoop = spooky$cleanupLoop(spooky$caveStaticLoop);
			spooky$caveMusicBoxLoop = spooky$cleanupLoop(spooky$caveMusicBoxLoop);
		}

		boolean inNether = player.getWorld().getRegistryKey() == World.NETHER;
		if (inNether) {
			spooky$netherAtmosphereLoop = spooky$ensureLoop(spooky$netherAtmosphereLoop, ModSounds.SPOOKY_HORROR_BACKGROUND_ATMOSPHERE, 0.7f, 1.0f);
			spooky$netherStaticLoop = spooky$ensureLoop(spooky$netherStaticLoop, ModSounds.SPOOKY_CRISPY_STATIC, 0.7f, 1.0f);
		} else {
			spooky$stopLoop(spooky$netherAtmosphereLoop);
			spooky$stopLoop(spooky$netherStaticLoop);
			spooky$netherAtmosphereLoop = spooky$cleanupLoop(spooky$netherAtmosphereLoop);
			spooky$netherStaticLoop = spooky$cleanupLoop(spooky$netherStaticLoop);
		}

		boolean inEnd = player.getWorld().getRegistryKey() == World.END;
		if (inEnd) {
			spooky$endStaticLoop = spooky$ensureLoop(spooky$endStaticLoop, ModSounds.SPOOKY_CRISPY_STATIC, 0.8f, 1.0f);
			spooky$endRadioStaticLoop = spooky$ensureLoop(spooky$endRadioStaticLoop, ModSounds.SPOOKY_RADIO_STATIC, 0.8f, 1.0f);
			spooky$endRustySwingsLoop = spooky$ensureLoop(spooky$endRustySwingsLoop, ModSounds.SPOOKY_RUSTY_SWINGS, 0.2f, 1.0f);
		} else {
			spooky$stopLoop(spooky$endStaticLoop);
			spooky$endStaticLoop = spooky$cleanupLoop(spooky$endStaticLoop);
			spooky$stopLoop(spooky$endRadioStaticLoop);
			spooky$endRadioStaticLoop = spooky$cleanupLoop(spooky$endRadioStaticLoop);
			spooky$stopLoop(spooky$endRustySwingsLoop);
			spooky$endRustySwingsLoop = spooky$cleanupLoop(spooky$endRustySwingsLoop);
		}
	}

	@Unique
	private CaveLoopSound spooky$ensureLoop(CaveLoopSound current, SoundEvent sound, float volume, float pitch) {
		if (current == null || current.isDone()) {
			current = new CaveLoopSound(player, sound, volume, pitch);
			soundManager.play(current);
		} else if (current.isFadingOut()) {
			current.fadeIn();
		}
		return current;
	}

	@Unique
	private void spooky$stopLoop(CaveLoopSound loop) {
		if (loop == null) {
			return;
		}
		if (!loop.isFadingOut()) {
			loop.fadeOut();
		}
	}

	@Unique
	private CaveLoopSound spooky$cleanupLoop(CaveLoopSound loop) {
		if (loop != null && loop.isDone()) {
			return null;
		}
		return loop;
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
