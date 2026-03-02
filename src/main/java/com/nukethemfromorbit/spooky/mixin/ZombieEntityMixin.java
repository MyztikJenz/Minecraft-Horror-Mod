package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.Spooky;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {
	private static final Identifier SPOOKY_BABY_SPEED_MODIFIER_ID = Identifier.of(Spooky.MOD_ID, "baby_speed_bonus");
	private static final EntityAttributeModifier SPOOKY_BABY_SPEED_BONUS = new EntityAttributeModifier(
		SPOOKY_BABY_SPEED_MODIFIER_ID, 0.85, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
	);

	@Inject(method = "setBaby", at = @At("TAIL"))
	private void spooky$boostBabySpeed(boolean baby, CallbackInfo ci) {
		ZombieEntity zombie = (ZombieEntity)(Object)this;
		if (zombie.getWorld() == null || zombie.getWorld().isClient) {
			return;
		}

		EntityAttributeInstance speedAttribute = zombie.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (speedAttribute == null) {
			return;
		}

		speedAttribute.removeModifier(SPOOKY_BABY_SPEED_MODIFIER_ID);
		if (baby) {
			speedAttribute.addTemporaryModifier(SPOOKY_BABY_SPEED_BONUS);
		}
	}
}
