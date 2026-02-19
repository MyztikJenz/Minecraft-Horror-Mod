package com.nukethemfromorbit.spooky.sound.client;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;

public class CaveLoopSound extends MovingSoundInstance {
	private final ClientPlayerEntity player;

	public CaveLoopSound(ClientPlayerEntity player) {
		super(ModSounds.SPOOKY_CRISPY_STATIC, SoundCategory.AMBIENT, SoundInstance.createRandom());
		this.player = player;
		this.repeat = true;
		this.repeatDelay = 0;
		this.relative = true;
		this.attenuationType = SoundInstance.AttenuationType.NONE;
		this.volume = 0.7f;
		this.pitch = 1.0f;
	}

	@Override
	public void tick() {
		this.x = player.getX();
		this.y = player.getY();
		this.z = player.getZ();
	}

	public void stop() {
		this.setDone();
	}
}
