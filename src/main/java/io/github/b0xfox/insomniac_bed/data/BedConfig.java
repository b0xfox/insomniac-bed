package io.github.b0xfox.insomniac_bed.data;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class BedConfig {

    public static void initialize() {
        PayloadTypeRegistry.playS2C().register(BedConfigPayload.ID, BedConfigPayload.CODEC);
    }

    public static boolean isDarknessEnabled(ServerPlayerEntity player) {

        BedConfigState state = BedConfigState.getServerState(player.getServerWorld());
        BedConfigData data = state.getByUUID(player.getUuid());

        if (data != null) {
            return data.darknessEnabled();
        }

        return true;
    }

    public static boolean isTimeSkippingEnabled(ServerPlayerEntity player) {

        BedConfigState state = BedConfigState.getServerState(player.getServerWorld());
        BedConfigData data = state.getByUUID(player.getUuid());

        if (data != null) {
            return data.timeEnabled();
        }

        return true;
    }

    public static boolean isLimitsEnabled(ServerPlayerEntity player) {

        BedConfigState state = BedConfigState.getServerState(player.getServerWorld());
        BedConfigData data = state.getByUUID(player.getUuid());

        if (data != null) {
            return data.limitsEnabled();
        }

        return true;
    }

    public static boolean isGuiEnabled(ServerPlayerEntity player) {

        BedConfigState state = BedConfigState.getServerState(player.getServerWorld());
        BedConfigData data = state.getByUUID(player.getUuid());

        if (data != null) {
            return data.guiEnabled();
        }

        return true;
    }

    public static void sync(ServerPlayerEntity player, BedConfigData data) {
        if (player.networkHandler != null) {
            ServerPlayNetworking.send(player, new BedConfigPayload(data));
        }
    }
}
