package io.github.b0xfox.insomniac_bed.block;

import java.util.ArrayList;
import java.util.List;

import io.github.b0xfox.insomniac_bed.InsomniacBed;
import io.github.b0xfox.insomniac_bed.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;

public class ModBlocks {

    public static final List<Block> BEDS = new ArrayList<>();

    public static BlockEntityType<InsomniacBedBlockEntity> BLOCK_ENTITY_TYPE;

    public static final Block WHITE_BED = registerBed(DyeColor.WHITE);
    public static final Block LIGHT_GRAY_BED = registerBed(DyeColor.LIGHT_GRAY);
    public static final Block GRAY_BED = registerBed(DyeColor.GRAY);
    public static final Block BLACK_BED = registerBed(DyeColor.BLACK);
    public static final Block BROWN_BED = registerBed(DyeColor.BROWN);
    public static final Block RED_BED = registerBed(DyeColor.RED);
    public static final Block ORANGE_BED = registerBed(DyeColor.ORANGE);
    public static final Block YELLOW_BED = registerBed(DyeColor.YELLOW);
    public static final Block LIME_BED = registerBed(DyeColor.LIME);
    public static final Block GREEN_BED = registerBed(DyeColor.GREEN);
    public static final Block CYAN_BED = registerBed(DyeColor.CYAN);
    public static final Block LIGHT_BLUE_BED = registerBed(DyeColor.LIGHT_BLUE);
    public static final Block BLUE_BED = registerBed(DyeColor.BLUE);
    public static final Block PURPLE_BED = registerBed(DyeColor.PURPLE);
    public static final Block MAGENTA_BED = registerBed(DyeColor.MAGENTA);
    public static final Block PINK_BED = registerBed(DyeColor.PINK);

    public static void initialize() {
        
        BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                        InsomniacBed.id("bed"),
                        FabricBlockEntityTypeBuilder.create(InsomniacBedBlockEntity::new, BEDS.toArray(Block[]::new))
                                .build());

    }

    public static Block registerBed(DyeColor color) {

        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, InsomniacBed.id(color.getName() + "_bed"));

        Block block = new InsomniacBedBlock(color, Settings.copy(Blocks.WHITE_BED).mapColor(color).registryKey(blockKey));
        Registry.register(Registries.BLOCK, blockKey, block);

        RegistryKey<ItemGroup> groupKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, InsomniacBed.id("bed_items"));
        ModItems.registerBed(block, color, groupKey);

        BEDS.add(block);

        return block;
    }
}
