package io.github.b0xfox.insomniac_bed;

import io.github.b0xfox.insomniac_bed.block.InsomniacBedBlock;
import net.minecraft.entity.player.PlayerEntity;

public class InsomniacBedUtil{

    public static boolean isSleepingInInsomniacBed(PlayerEntity player) {

        if (player == null) return false;

        if (!player.isSleeping()) return false;

        if (player.getBlockStateAtPos().getBlock() instanceof InsomniacBedBlock) return true;

        return false;
    }
}
