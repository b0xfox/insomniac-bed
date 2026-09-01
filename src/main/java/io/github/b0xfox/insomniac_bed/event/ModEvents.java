package io.github.b0xfox.insomniac_bed.event;

import io.github.b0xfox.insomniac_bed.InsomniacBedUtil;
import io.github.b0xfox.insomniac_bed.data.BedConfig;
import io.github.b0xfox.insomniac_bed.data.BedConfigData;
import io.github.b0xfox.insomniac_bed.data.BedConfigState;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class ModEvents {

    public static void initialize() {
        EntitySleepEvents.ALLOW_SLEEP_TIME.register(ModEvents::allowSleepTime);
        ServerPlayConnectionEvents.JOIN.register(ModEvents::syncBedConfigOnJoin);
    }

    private static ActionResult allowSleepTime(PlayerEntity player, BlockPos sleepingPos, boolean vanillaResult) {

        if (player instanceof ServerPlayerEntity serverPlayer) {

            if (BedConfig.isLimitsEnabled(serverPlayer) == false || InsomniacBedUtil.isSleepingInInsomniacBed(player)) {

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    private static void syncBedConfigOnJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player = handler.getPlayer();
        BedConfigState state = BedConfigState.getServerState(player.getServerWorld());
        BedConfigData data = state.getByUUID(player.getUuid());
        BedConfig.sync(player, data);
    }
}
