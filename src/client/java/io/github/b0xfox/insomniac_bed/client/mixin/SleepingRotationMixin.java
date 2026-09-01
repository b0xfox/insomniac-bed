package io.github.b0xfox.insomniac_bed.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.b0xfox.insomniac_bed.client.data.ClientBedConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public abstract class SleepingRotationMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Inject(method = "updateRenderState", at = @At("TAIL"))
    private void forceSleepingHeadState(T livingEntity, S state, float tickDelta, CallbackInfo ci) {

        if (livingEntity instanceof ClientPlayerEntity player) {
            if (livingEntity.isSleeping() && ClientBedConfig.isGuiEnabled(player) == false) {
                state.pitch = 0.0F;
                state.yawDegrees = 0.0F;
            }
        }
    }
}
