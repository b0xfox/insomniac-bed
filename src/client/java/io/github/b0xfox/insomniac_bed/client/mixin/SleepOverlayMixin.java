package io.github.b0xfox.insomniac_bed.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.b0xfox.insomniac_bed.client.data.ClientBedConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(InGameHud.class)
public class SleepOverlayMixin {

    @Inject(method = "renderSleepOverlay", at = @At("HEAD"), cancellable = true)
    private void cancelSleepOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || !client.player.isSleeping())
            return;

        if (ClientBedConfig.isDarknessEnabled(client.player) == false)
            ci.cancel();
    }
}
