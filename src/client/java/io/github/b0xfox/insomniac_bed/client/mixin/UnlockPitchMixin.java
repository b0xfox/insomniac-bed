package io.github.b0xfox.insomniac_bed.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.b0xfox.insomniac_bed.client.data.ClientBedConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class UnlockPitchMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setPitch(F)V"))
    private void allowPitchWhileSleeping(LivingEntity self, float pitch) {

        if (self instanceof ClientPlayerEntity player && player.isSleeping() && ClientBedConfig.isGuiEnabled(player) == false)
            return;

        self.setPitch(0.0F);
    }
}
