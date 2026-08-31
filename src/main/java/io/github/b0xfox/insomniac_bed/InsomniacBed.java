package io.github.b0xfox.insomniac_bed;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.b0xfox.insomniac_bed.block.ModBlocks;
import io.github.b0xfox.insomniac_bed.command.ModCommands;
import io.github.b0xfox.insomniac_bed.data.BedConfig;
import io.github.b0xfox.insomniac_bed.event.ModEvents;
import io.github.b0xfox.insomniac_bed.item.ModItemGroups;

public class InsomniacBed implements ModInitializer {

	public static final String MOD_ID = "insomniac-bed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

        ModItemGroups.initialize();
        ModBlocks.initialize();
        ModCommands.initialize();
        ModEvents.initialize();
        BedConfig.initialize();

	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
