package io.github.b0xfox.insomniac_bed.data;

import java.util.Optional;

import io.github.b0xfox.insomniac_bed.command.BedConfigCommand;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class BedConfig {

    public enum Option {
        ADVANCE_TIME,
        DARKNESS,
        LIMITS,
        GUI
    }

    public static void initialize() {
        PayloadTypeRegistry.playS2C().register(BedConfigPayload.ID, BedConfigPayload.CODEC);
    }

    public static Optional<BedConfigData> updateOption(ServerPlayerEntity player, BedConfig.Option option, boolean enabled){

        if (player == null) return Optional.empty();

        BedConfigState state = BedConfigState.getServerState(player.getServerWorld());
        BedConfigData data = state.getByUUID(player.getUuid());

        if (state == null || data == null) return Optional.empty();

        boolean darkness = data.darknessEnabled();
        boolean time = data.timeEnabled();
        boolean limits = data.limitsEnabled();
        boolean gui = data.guiEnabled();

        switch (option) {

            case ADVANCE_TIME:
                time = enabled;
                break;

            case DARKNESS:
                darkness = enabled;
                break;

            case LIMITS:
                limits = enabled;
                break;

            case GUI:
                gui = enabled;
                break;

            default:
                return Optional.empty();
        }

        BedConfigData updatedData = new BedConfigData(darkness, time, limits, gui);
        state.setByUUID(player.getUuid(), updatedData);
        BedConfig.sync(player, updatedData);

        return Optional.of(updatedData);
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
