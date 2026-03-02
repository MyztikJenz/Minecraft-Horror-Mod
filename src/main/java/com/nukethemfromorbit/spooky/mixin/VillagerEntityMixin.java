package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
	@Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
	private void spooky$replaceInteractionAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
		VillagerEntity villager = (VillagerEntity)(Object)this;
		if (villager.hasCustomer()) {
			cir.setReturnValue(ModSounds.SPOOKY_VILLAGER_INTERACTION);
		}
	}

	@Inject(method = "sayNo", at = @At("HEAD"), cancellable = true)
	private void spooky$replaceNoSound(CallbackInfo ci) {
		VillagerEntity villager = (VillagerEntity)(Object)this;
		villager.setHeadRollingTimeLeft(40);
		if (!villager.getWorld().isClient()) {
			villager.playSound(ModSounds.SPOOKY_VILLAGER_INTERACTION);
		}
		ci.cancel();
	}
}
