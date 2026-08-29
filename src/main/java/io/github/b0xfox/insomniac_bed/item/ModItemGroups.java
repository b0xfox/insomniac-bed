package io.github.b0xfox.insomniac_bed.item;

import io.github.b0xfox.insomniac_bed.InsomniacBed;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups {

    public static final ItemGroup BED_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            InsomniacBed.id("bed_items"), FabricItemGroup.builder()
                    .icon(() -> new ItemStack(Items.RED_BED))
                    .displayName(Text.translatable("itemgroup.insomniac-bed.bed_items"))
                    .build());

    public static void initialize() {
        InsomniacBed.LOGGER.atInfo().log("Registering Item Group for " + InsomniacBed.MOD_ID);
    }
}
