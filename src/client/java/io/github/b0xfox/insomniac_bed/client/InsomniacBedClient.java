package io.github.b0xfox.insomniac_bed.client;

import io.github.b0xfox.insomniac_bed.block.ModBlocks;
import io.github.b0xfox.insomniac_bed.client.block.InsomniacBedBlockEntityRenderer;
import io.github.b0xfox.insomniac_bed.client.data.ClientBedConfig;
import io.github.b0xfox.insomniac_bed.client.event.ModClientEvents;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class InsomniacBedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModClientEvents.initialize();
        ClientBedConfig.initialize();

        BlockEntityRendererFactories.register(ModBlocks.BLOCK_ENTITY_TYPE, InsomniacBedBlockEntityRenderer::new);

    }
}
