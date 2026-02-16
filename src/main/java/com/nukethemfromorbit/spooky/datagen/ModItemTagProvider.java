package com.nukethemfromorbit.spooky.datagen;

import com.nukethemfromorbit.spooky.item.ModItems;
import com.nukethemfromorbit.spooky.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // https://youtu.be/ELHvhvuGF3U?si=Apbpw4hdbKIL_6ga&t=157
//        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
//                .add(// CustomBlock)

        getOrCreateTagBuilder(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.PINK_GARNET)
                .add(ModItems.RAW_PINK_GARNET);
    }
}
