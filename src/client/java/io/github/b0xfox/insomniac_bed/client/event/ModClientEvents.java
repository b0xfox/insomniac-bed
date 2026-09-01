package io.github.b0xfox.insomniac_bed.client.event;

import io.github.b0xfox.insomniac_bed.InsomniacBedUtil;
import io.github.b0xfox.insomniac_bed.client.data.ClientBedConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class ModClientEvents {

    private static boolean exitBedPacket = false;

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(ModClientEvents::wakeUp);
    }

    public static void wakeUp(MinecraftClient client) {

        if (client.player == null || !client.player.isSleeping()) {
            exitBedPacket = false;
            return;
        }

        if (ClientBedConfig.isGuiEnabled(client.player) == false || InsomniacBedUtil.isSleepingInInsomniacBed(client.player)) {
            boolean isSneaking = client.options.sneakKey.isPressed();
            boolean isFlying = client.player.getAbilities().flying;
            boolean exitBed = isSneaking || isFlying;

            if (exitBed && !exitBedPacket) {
                client.player.networkHandler.sendPacket(new ClientCommandC2SPacket(client.player, ClientCommandC2SPacket.Mode.STOP_SLEEPING));
            }

            exitBedPacket = exitBed;
            return;
        }

        exitBedPacket = false;
    }
}
