package io.github.b0xfox.insomniac_bed.mixin;

import io.github.b0xfox.insomniac_bed.InsomniacBedUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.SleepManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(SleepManager.class)
public class IgnoreSleepMixin {

    @ModifyVariable(
        method = "update",
        at = @At("HEAD"),
        argsOnly = true
    )
    private List<ServerPlayerEntity> filterExemptSleepers(List<ServerPlayerEntity> players) {
        return players.stream()
                .filter(player -> !InsomniacBedUtil.isSleepingInInsomniacBed(player))
                .collect(Collectors.toList());
    }
}
