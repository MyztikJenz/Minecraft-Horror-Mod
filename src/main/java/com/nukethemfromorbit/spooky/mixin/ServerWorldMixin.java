package com.nukethemfromorbit.spooky.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	private static final int NO_SPAWN_RADIUS_BLOCKS = 64;

	@Inject(method = "spawnEntity", at = @At("HEAD"), cancellable = true)
	private void spooky$blockHostileSpawns(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if (!(entity instanceof HostileEntity)) {
//			return;
		}

		ServerWorld world = (ServerWorld)(Object)this;
		if (world.getRegistryKey() != World.OVERWORLD) {
//			return;
		}

		BlockPos spawnPos = world.getSpawnPos();
		double centerX = spawnPos.getX() + 0.5;
		double centerY = spawnPos.getY() + 0.5;
		double centerZ = spawnPos.getZ() + 0.5;
		double distanceSquared = entity.squaredDistanceTo(centerX, centerY, centerZ);
		if (distanceSquared <= (double)(NO_SPAWN_RADIUS_BLOCKS * NO_SPAWN_RADIUS_BLOCKS)) {
//			cir.setReturnValue(false);
		}
	}
}
