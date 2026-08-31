package io.github.b0xfox.insomniac_bed.data;

import net.minecraft.nbt.NbtCompound;

public record BedConfigData(boolean darknessEnabled, boolean timeEnabled, boolean guiEnabled) {

    public static BedConfigData createDefault() {
        return new BedConfigData(true, true, true);
    }

    public static BedConfigData fromNbt(NbtCompound nbt) {
        return new BedConfigData(
                nbt.getBoolean("darknessEnabled"),
                nbt.getBoolean("timeEnabled"),
                nbt.getBoolean("guiEnabled"));
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean("darknessEnabled", this.darknessEnabled);
        nbt.putBoolean("timeEnabled", this.timeEnabled);
        nbt.putBoolean("guiEnabled", this.guiEnabled);
        return nbt;
    }

}
