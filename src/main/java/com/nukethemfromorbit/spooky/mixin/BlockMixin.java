package com.nukethemfromorbit.spooky.mixin;

import com.nukethemfromorbit.spooky.Spooky;
import com.nukethemfromorbit.spooky.sound.ModSounds;
import com.nukethemfromorbit.spooky.util.SpookyEffects;
import com.nukethemfromorbit.spooky.util.ServerScheduler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
	private static final String FIRST_CHEST_TAG = "spooky_first_chest";
	private static final String FIRST_CRAFTING_TAG = "spooky_first_crafting_table";

	@Inject(method = "onPlaced", at = @At("HEAD"))
	private void spooky$onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
		if (world.isClient || !(placer instanceof ServerPlayerEntity player)) {
			return;
		}

		if (state.isOf(Blocks.CHEST) || state.isOf(Blocks.TRAPPED_CHEST)) {
			if (!player.getCommandTags().contains(FIRST_CHEST_TAG)) {
				player.addCommandTag(FIRST_CHEST_TAG);
				Spooky.LOGGER.info("First chest placed by {}", player.getName().getString());
			}
			return;
		}

		if (state.isOf(Blocks.CRAFTING_TABLE)) {
			if (!player.getCommandTags().contains(FIRST_CRAFTING_TAG)) {
				player.addCommandTag(FIRST_CRAFTING_TAG);
				SpookyEffects.applyCraftingTableEffects(player);
				ServerScheduler.schedule(100, () -> {
					if (!player.isRemoved()) {
						player.playSoundToPlayer(ModSounds.SPOOKY_ANGRY_GHOST_VOICE, SoundCategory.PLAYERS, 0.5f, 1.0f);
					}
				});
				Spooky.LOGGER.info("First crafting table placed by {}", player.getName().getString());
			}
		}
	}
}
