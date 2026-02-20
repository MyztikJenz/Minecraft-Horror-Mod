package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import com.nukethemfromorbit.spooky.util.ServerScheduler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
	private static final int BLINDNESS_REAPPLY_TICKS = 999999;

	@Inject(method = "finishUsing", at = @At("HEAD"))
	private void spooky$trackBlindness(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (world.isClient || !(user instanceof ServerPlayerEntity player)) {
			return;
		}

		if (!player.hasStatusEffect(StatusEffects.BLINDNESS)) {
			return;
		}

		ServerScheduler.schedule(20, () -> {
			if (!player.isRemoved() && !player.hasStatusEffect(StatusEffects.BLINDNESS)) {
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, BLINDNESS_REAPPLY_TICKS, 1, false, true, true));
				player.playSoundToPlayer(ModSounds.SPOOKY_LOL_MILK, SoundCategory.PLAYERS, 1.0f, 1.0f);
				player.playSoundToPlayer(ModSounds.SPOOKY_FEMALE_HORROR_LAUGHTER, SoundCategory.PLAYERS, 1.0f, 1.0f);
			}
		});
	}
}
