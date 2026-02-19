package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.client.CaveLoopState;
import net.minecraft.client.sound.MusicTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class MusicTrackerMixin {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void spooky$pauseMusicWhileCaveLoop(CallbackInfo ci) {
		if (CaveLoopState.shouldSuppressMusic()) {
			((MusicTracker)(Object)this).stop();
			ci.cancel();
		}
	}
}
