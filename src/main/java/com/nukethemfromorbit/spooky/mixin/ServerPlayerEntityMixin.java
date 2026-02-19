package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
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
	@Unique
	private static List<EntityType<? extends HostileEntity>> spooky$hostileTypes;

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

		spooky$removeNearbyHostiles(player);

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

	@Unique
	private void spooky$removeNearbyHostiles(ServerPlayerEntity player) {
		boolean blindnessActive = player.hasStatusEffect(StatusEffects.BLINDNESS);
		double radius = 8.0;
		Box box = Box.of(player.getPos(), radius * 2.0, radius * 2.0, radius * 2.0);
		int removed = 0;
		for (HostileEntity hostile : player.getWorld().getEntitiesByClass(HostileEntity.class, box, entity -> true)) {
			if (!hostile.isRemoved()) {
				hostile.remove(Entity.RemovalReason.DISCARDED);
				removed++;
			}
		}

		if (blindnessActive && removed > 0) {
			spooky$spawnReplacementHostiles(player, removed);
		}
	}

	@Unique
	private void spooky$spawnReplacementHostiles(ServerPlayerEntity player, int count) {
		ServerWorld world = (ServerWorld)player.getWorld();
		Random random = world.getRandom();
		int spawned = 0;
		int attempts = 0;

		while (spawned < count && attempts < count * 20) {
			attempts++;
			double angle = random.nextDouble() * Math.PI * 2.0;
			double radius = 9.0 + random.nextDouble() * 7.0;
			int x = MathHelper.floor(player.getX() + Math.cos(angle) * radius);
			int z = MathHelper.floor(player.getZ() + Math.sin(angle) * radius);
			int baseY = MathHelper.floor(player.getY());

			BlockPos.Mutable pos = new BlockPos.Mutable();
			boolean found = false;
			for (int dy = 2; dy >= -2; dy--) {
				pos.set(x, baseY + dy, z);
				if (world.getBlockState(pos).isAir()
					&& world.getBlockState(pos.up()).isAir()
					&& world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
					found = true;
					break;
				}
			}

			if (!found) {
				continue;
			}

			EntityType<? extends HostileEntity> type = spooky$getRandomHostileType(world, random);
			HostileEntity entity = type.create(world, null, pos, SpawnReason.EVENT, false, false);
			if (entity == null) {
				continue;
			}

			if (!world.isSpaceEmpty(entity)) {
				continue;
			}

			if (world.spawnEntity(entity)) {
				spawned++;
			}
		}
	}

	@Unique
	private static EntityType<? extends HostileEntity> spooky$getRandomHostileType(ServerWorld world, Random random) {
		List<EntityType<? extends HostileEntity>> types = spooky$getHostileTypes(world);
		if (types.isEmpty()) {
			return EntityType.ZOMBIE;
		}

		return types.get(random.nextInt(types.size()));
	}

	@Unique
	private static List<EntityType<? extends HostileEntity>> spooky$getHostileTypes(ServerWorld world) {
		if (spooky$hostileTypes != null) {
			return spooky$hostileTypes;
		}

		List<EntityType<? extends HostileEntity>> types = new ArrayList<>();
		for (EntityType<?> type : Registries.ENTITY_TYPE) {
			if (type.getSpawnGroup() != SpawnGroup.MONSTER) {
				continue;
			}

			Entity entity = type.create(world);
			if (entity instanceof HostileEntity) {
				types.add((EntityType<? extends HostileEntity>)type);
			}
		}

		spooky$hostileTypes = types;
		return spooky$hostileTypes;
	}
}
