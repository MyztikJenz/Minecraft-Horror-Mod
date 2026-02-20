package com.nukethemfromorbit.spooky.mixin;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.nukethemfromorbit.spooky.sound.ModSounds;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {
	private static final Identifier ENTER_THE_END = Identifier.of("minecraft", "story/enter_the_end");
	private static final Identifier ENTER_THE_NETHER = Identifier.of("minecraft", "story/enter_the_nether");
	@Shadow
	private ServerPlayerEntity owner;

	@Inject(method = "grantCriterion", at = @At("TAIL"))
	private void spooky$onGrantCriterion(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		if (advancement.id().getPath().startsWith("recipes/")) {
			return;
		}

//		if (!ENTER_THE_END.equals(advancement.id()) && !ENTER_THE_NETHER.equals(advancement.id())) {
//			return;
//		}

		AdvancementProgress progress = ((PlayerAdvancementTracker)(Object)this).getProgress(advancement);
		if (!progress.isDone()) {
			return;
		}

		if (ENTER_THE_END.equals(advancement.id())) {
			owner.playSoundToPlayer(ModSounds.SPOOKY_GHOST_BREATH, SoundCategory.PLAYERS, 1.0f, 1.0f);
		} else if (ENTER_THE_NETHER.equals(advancement.id())) {
			owner.playSoundToPlayer(ModSounds.SPOOKY_INCEPTION, SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
		else {
			owner.playSoundToPlayer(ModSounds.SPOOKY_FEMALE_HORROR_LAUGHTER, SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
	}
}
