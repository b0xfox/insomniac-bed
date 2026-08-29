package io.github.b0xfox.insomniac_bed;

import io.github.b0xfox.insomniac_bed.block.InsomniacBedBlockEntityRenderer;
import io.github.b0xfox.insomniac_bed.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class InsomniacBedClient implements ClientModInitializer {

    private boolean sentPacket = false;

    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null || !client.player.isSleeping()) {
                sentPacket = false;
                return;
            }

            boolean isSneaking = client.options.sneakKey.isPressed();
            boolean isFlying = client.player.getAbilities().flying;
            boolean exitBed = isSneaking || isFlying;

            if (exitBed && !sentPacket) {
                client.player.networkHandler.sendPacket(
                        new ClientCommandC2SPacket(client.player, ClientCommandC2SPacket.Mode.STOP_SLEEPING));
            }

            sentPacket = exitBed;
        });

        BlockEntityRendererFactories.register(ModBlocks.BLOCK_ENTITY_TYPE, InsomniacBedBlockEntityRenderer::new);
    }
}
