package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.sound.ModSounds;
import java.util.Collection;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Inject(method = "unlockRecipes(Ljava/util/Collection;)I", at = @At("RETURN"))
	private void spooky$onUnlockRecipes(Collection<RecipeEntry<?>> recipes, CallbackInfoReturnable<Integer> cir) {
		Integer unlocked = cir.getReturnValue();
		if (unlocked == null || unlocked <= 0) {
			return;
		}

		ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
		player.playSoundToPlayer(ModSounds.SPOOKY_INCEPTION, SoundCategory.PLAYERS, 1.0f, 1.0f);
	}
}
