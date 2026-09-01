package io.github.b0xfox.insomniac_bed.client.data;

import io.github.b0xfox.insomniac_bed.data.BedConfigData;
import io.github.b0xfox.insomniac_bed.data.BedConfigPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientBedConfig {

    private static BedConfigData clientBedConfig = BedConfigData.createDefault();

    public static void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(BedConfigPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                clientBedConfig = payload.toData();
            });
        });
    }

    public static BedConfigData get() {
        return clientBedConfig;
    }

    public static boolean isDarknessEnabled(ClientPlayerEntity player) {
        return clientBedConfig.darknessEnabled();
    }

    public static boolean isTimeSkippingEnabled(ClientPlayerEntity player) {
        return clientBedConfig.timeEnabled();
    }

    public static boolean isLimitsEnabled(ClientPlayerEntity player) {
        return clientBedConfig.limitsEnabled();
    }

    public static boolean isGuiEnabled(ClientPlayerEntity player) {
        return clientBedConfig.guiEnabled();
    }
}
