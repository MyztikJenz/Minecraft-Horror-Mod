package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "respawnPlayer", at = @At("RETURN"))
	private void spooky$onRespawnPlayer(ServerPlayerEntity player, boolean alive, Entity.RemovalReason removalReason, CallbackInfoReturnable<ServerPlayerEntity> cir) {
		if (alive) {
			return;
		}

		ServerPlayerEntity respawned = cir.getReturnValue();
		if (respawned == null) {
			return;
		}

		respawned.playSoundToPlayer(ModSounds.SPOOKY_I_SEE_YOU, SoundCategory.PLAYERS, 1.0f, 1.0f);
	}
}
