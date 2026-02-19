package com.nukethemfromorbit.spooky.mixin;

import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Unique
	private boolean spooky$hadBlindnessBeforeAdd;
	@Unique
	private static List<EntityType<? extends HostileEntity>> spooky$hostileTypes;

	@Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"))
	private void spooky$recordBlindnessBeforeAdd(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
		if (effect.getEffectType() == StatusEffects.BLINDNESS) {
			LivingEntity entity = (LivingEntity)(Object)this;
			spooky$hadBlindnessBeforeAdd = entity.hasStatusEffect(StatusEffects.BLINDNESS);
		} else {
			spooky$hadBlindnessBeforeAdd = false;
		}
	}

	@Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("TAIL"))
	private void spooky$onAddStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		if (effect.getEffectType() != StatusEffects.BLINDNESS || spooky$hadBlindnessBeforeAdd) {
			return;
		}

		if (!((Object)this instanceof ServerPlayerEntity player)) {
			return;
		}

		if (player.getWorld().isClient) {
			return;
		}

		spooky$spawnBlindnessHostiles(player);
	}

	@Unique
	private void spooky$spawnBlindnessHostiles(ServerPlayerEntity player) {
		ServerWorld world = (ServerWorld)player.getWorld();
		Random random = world.getRandom();
		int spawned = 0;
		int attempts = 0;
		int targetCount = 8;

		while (spawned < targetCount && attempts < 30) {
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
	private EntityType<? extends HostileEntity> spooky$getRandomHostileType(ServerWorld world, Random random) {
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
