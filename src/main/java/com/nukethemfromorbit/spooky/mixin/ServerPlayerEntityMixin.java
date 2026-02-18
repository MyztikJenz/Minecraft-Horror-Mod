package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import java.util.Collection;
import net.minecraft.entity.Entity;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Unique
	private RegistryKey<World> spooky$teleportOrigin;
	@Unique
	private int spooky$timeBand = Integer.MIN_VALUE;
	@Unique
	private boolean spooky$nightTriggered;

	@Inject(method = "unlockRecipes(Ljava/util/Collection;)I", at = @At("RETURN"))
	private void spooky$onUnlockRecipes(Collection<RecipeEntry<?>> recipes, CallbackInfoReturnable<Integer> cir) {
		Integer unlocked = cir.getReturnValue();
		if (unlocked == null || unlocked <= 0) {
			return;
		}

		ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
		player.playSoundToPlayer(ModSounds.SPOOKY_INCEPTION, SoundCategory.PLAYERS, 1.0f, 1.0f);
	}

	@Inject(method = "teleportTo", at = @At("HEAD"))
	private void spooky$captureTeleportOrigin(TeleportTarget teleportTarget, CallbackInfoReturnable<Entity> cir) {
		ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
		spooky$teleportOrigin = player.getWorld().getRegistryKey();
	}

	@Inject(method = "teleportTo", at = @At("TAIL"))
	private void spooky$onTeleportTo(TeleportTarget teleportTarget, CallbackInfoReturnable<Entity> cir) {
		if (cir.getReturnValue() == null) {
			return;
		}

		if (teleportTarget.world().getRegistryKey() == World.END && spooky$teleportOrigin != World.END) {
			ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
			player.playSoundToPlayer(ModSounds.SPOOKY_INCEPTION, SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void spooky$updateTimeBand(CallbackInfo ci) {
		ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
		if (player.getWorld().isClient) {
			return;
		}

		if (player.getWorld().getRegistryKey() != World.OVERWORLD) {
			return;
		}

		int blockX = player.getBlockX();
		int band = Math.floorDiv(blockX, 20);
		if (blockX > 0 && blockX % 20 == 0) {
			band -= 1;
		}

		if (band == spooky$timeBand) {
			return;
		}

		spooky$timeBand = band;
		boolean day = (band & 1) == 0;
		ServerWorld world = (ServerWorld)player.getWorld();
		world.setTimeOfDay(day ? 1000L : 18000L);
		if (!day && !spooky$nightTriggered) {
			spooky$nightTriggered = true;
			world.setWeather(0, 600, true, true);
			player.playSoundToPlayer(ModSounds.SPOOKY_INCEPTION, SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
	}
}
