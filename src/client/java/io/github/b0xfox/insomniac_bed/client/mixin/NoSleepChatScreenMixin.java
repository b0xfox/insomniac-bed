package io.github.b0xfox.insomniac_bed.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SleepingChatScreen;
import io.github.b0xfox.insomniac_bed.client.data.ClientBedConfig;

@Mixin(MinecraftClient.class)
public class NoSleepChatScreenMixin {

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void preventSleepChat(Screen screen, CallbackInfo ci) {

        if (screen instanceof SleepingChatScreen) {

            MinecraftClient client = (MinecraftClient) (Object) this;

            if (client.player == null)
                return;

            if (!client.player.isSleeping())
                return;

            if (!ClientBedConfig.isGuiEnabled(client.player))
                ci.cancel();
        }
    }
}
