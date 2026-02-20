package com.nukethemfromorbit.spooky.sound.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class CaveLoopSound extends MovingSoundInstance {
	private static final int FADE_TICKS = 20;

	private final ClientPlayerEntity player;
	private final float baseVolume;
	private int fadeTicksRemaining;
	private boolean fadingOut;

	public CaveLoopSound(ClientPlayerEntity player, SoundEvent sound, float volume, float pitch) {
		super(sound, SoundCategory.AMBIENT, SoundInstance.createRandom());
		this.player = player;
		this.baseVolume = volume;
		this.repeat = true;
		this.repeatDelay = 0;
		this.relative = true;
		this.attenuationType = SoundInstance.AttenuationType.NONE;
		this.volume = Math.max(0.001f, baseVolume / (float)FADE_TICKS);
		this.pitch = pitch;
		this.fadeIn();
	}

	@Override
	public void tick() {
		if (player.isRemoved() || player.getHealth() <= 0.0f) {
			this.setDone();
			return;
		}

		this.x = player.getX();
		this.y = player.getY();
		this.z = player.getZ();

		if (fadeTicksRemaining > 0) {
			fadeTicksRemaining--;
			float step = baseVolume / (float)FADE_TICKS;
			if (fadingOut) {
				this.volume = Math.max(0.0f, this.volume - step);
				if (this.volume <= 0.0f) {
					this.setDone();
				}
			} else {
				this.volume = Math.min(baseVolume, this.volume + step);
			}
		} else if (!fadingOut) {
			this.volume = baseVolume;
		}
	}

	public void stop() {
		this.setDone();
	}

	public boolean isFadingOut() {
		return fadingOut;
	}

	public void fadeOut() {
		fadingOut = true;
		fadeTicksRemaining = FADE_TICKS;
	}

	public void fadeIn() {
		fadingOut = false;
		fadeTicksRemaining = FADE_TICKS;
		this.volume = Math.max(this.volume, baseVolume / (float)FADE_TICKS);
	}
}
