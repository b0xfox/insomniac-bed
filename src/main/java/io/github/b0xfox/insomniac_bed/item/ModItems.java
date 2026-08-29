package io.github.b0xfox.insomniac_bed.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import io.github.b0xfox.insomniac_bed.InsomniacBed;

public class ModItems {

    public static Item registerBed(Block block, DyeColor color, RegistryKey<ItemGroup> itemGroupKey) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, InsomniacBed.id(color.getName() + "_bed"));
        Item item = new BedItem(block, new Item.Settings().registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);

        ItemGroupEvents.modifyEntriesEvent(itemGroupKey).register(entries -> { entries.add(item); });

        return item;
    }

    public static void initialize() {}
}
