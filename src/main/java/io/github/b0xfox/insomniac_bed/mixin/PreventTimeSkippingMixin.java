package io.github.b0xfox.insomniac_bed.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.SleepManager;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.b0xfox.insomniac_bed.data.BedConfig;

@Mixin(SleepManager.class)
public class PreventTimeSkippingMixin {

    @Inject(method = "canResetTime", at = @At("HEAD"), cancellable = true)
    private void preventNightResetForInsomniacs(int percentage, List<ServerPlayerEntity> players, CallbackInfoReturnable<Boolean> cir) {
        for (ServerPlayerEntity player : players) {
            if (player.isSleeping() && BedConfig.isTimeSkippingEnabled(player) == false) {
                cir.setReturnValue(false);
                return;
            }
        }
    }
}
