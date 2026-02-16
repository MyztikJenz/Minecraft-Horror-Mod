package com.nukethemfromorbit.spooky.util;

import com.nukethemfromorbit.spooky.Spooky;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Spooky.MOD_ID, name));
        }

    }

    public static class Items {
        // When you want to use this: https://youtu.be/lVpV3B3yFsg?si=1BoOEyBY7d_c3BdS&t=220
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Spooky.MOD_ID, name));
        }
    }

}
