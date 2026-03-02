package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Unique
	private boolean spooky$hadBlindnessBeforeAdd;
	@Unique
	private static List<EntityType<? extends HostileEntity>> spooky$hostileTypes;

	@Shadow
	protected abstract SoundEvent getDeathSound();

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

	@Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDeathSound()Lnet/minecraft/sound/SoundEvent;"))
	private SoundEvent spooky$replaceAnimalDeathSoundDuringDamage(LivingEntity entity) {
		return spooky$getReplacementDeathSound(entity);
	}

	@Redirect(method = "handleStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDeathSound()Lnet/minecraft/sound/SoundEvent;"))
	private SoundEvent spooky$replaceAnimalDeathSoundDuringStatus(LivingEntity entity) {
		return spooky$getReplacementDeathSound(entity);
	}


	@Unique
	private SoundEvent spooky$getReplacementDeathSound(LivingEntity entity) {
		if (entity instanceof AnimalEntity) {
			return ModSounds.SPOOKY_ANIMAL_DYING;
		}

		return this.getDeathSound();
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
			int maxY = Math.min(world.getTopY() - 1, baseY + 16);
			int minY = Math.max(world.getBottomY() + 1, baseY - 32);

			BlockPos.Mutable pos = new BlockPos.Mutable();
			boolean found = false;
			for (int y = maxY; y >= minY; y--) {
				pos.set(x, y, z);
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
