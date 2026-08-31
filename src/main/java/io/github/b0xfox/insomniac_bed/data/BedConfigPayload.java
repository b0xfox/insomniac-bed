package io.github.b0xfox.insomniac_bed.data;

import io.github.b0xfox.insomniac_bed.InsomniacBed;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record BedConfigPayload(boolean darknessEnabled, boolean timeEnabled, boolean guiEnabled) implements CustomPayload {

    public static final CustomPayload.Id<BedConfigPayload> ID = new CustomPayload.Id<>(InsomniacBed.id("bed_config_sync"));

    public static final PacketCodec<RegistryByteBuf, BedConfigPayload> CODEC = PacketCodec.tuple(
        PacketCodecs.BOOLEAN, BedConfigPayload::darknessEnabled,
        PacketCodecs.BOOLEAN, BedConfigPayload::timeEnabled,
        PacketCodecs.BOOLEAN, BedConfigPayload::guiEnabled,
        BedConfigPayload::new
    );

    public BedConfigPayload(BedConfigData data) {
        this(data.darknessEnabled(), data.timeEnabled(), data.guiEnabled());
    }

    public BedConfigData toData() {
        return new BedConfigData(darknessEnabled, timeEnabled, guiEnabled);
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
