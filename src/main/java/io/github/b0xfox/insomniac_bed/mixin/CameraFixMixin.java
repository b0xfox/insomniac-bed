package io.github.b0xfox.insomniac_bed.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.b0xfox.insomniac_bed.InsomniacBedUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(LivingEntity.class)
public abstract class CameraFixMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setPitch(F)V"))
    private void allowPitchWhileSleeping(LivingEntity self, float pitch) {

        if (self instanceof PlayerEntity player && InsomniacBedUtil.isSleepingInInsomniacBed(player))
            return;

        self.setPitch(0.0F);
    }
}
