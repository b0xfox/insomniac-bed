package io.github.b0xfox.insomniac_bed.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;

import io.github.b0xfox.insomniac_bed.data.BedConfig;
import io.github.b0xfox.insomniac_bed.data.BedConfigData;
import io.github.b0xfox.insomniac_bed.data.BedConfigState;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BedConfigCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                CommandManager.literal("bed").executes(BedConfigCommand::runRoot)

                        .then(
                                CommandManager.literal("darkness")
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool()))
                                        .executes(BedConfigCommand::runToggleDarkness))

                        .then(
                                CommandManager.literal("time_skip")
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool()))
                                        .executes(BedConfigCommand::runToggleTimeSkipping))

                        .then(
                                CommandManager.literal("gui")
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool()))
                                        .executes(BedConfigCommand::runToggleGUI)));
    }

    public static int runRoot(CommandContext<ServerCommandSource> ctx) {

        ServerPlayerEntity serverPlayer = ctx.getSource().getPlayer();

        if (serverPlayer != null) {
            BedConfigState state = BedConfigState.getServerState(serverPlayer.getServerWorld());
            BedConfigData data = state.getByUUID(serverPlayer.getUuid());

            if (state == null || data == null)
                return 0;

            listConfigData(serverPlayer, data);

            return 1;
        }

        return 0;
    }

    public static int runToggleDarkness(CommandContext<ServerCommandSource> ctx) {

        ServerPlayerEntity serverPlayer = ctx.getSource().getPlayer();

        if (serverPlayer != null) {
            BedConfigState state = BedConfigState.getServerState(serverPlayer.getServerWorld());
            BedConfigData data = state.getByUUID(serverPlayer.getUuid());

            if (state == null || data == null)
                return 0;

            BedConfigData updatedData = new BedConfigData(
                    !data.darknessEnabled(),
                    data.timeEnabled(),
                    data.guiEnabled());

            state.setByUUID(serverPlayer.getUuid(), updatedData);
            BedConfig.sync(serverPlayer, data);
            listConfigData(serverPlayer, updatedData);

            return updatedData.darknessEnabled() ? 1 : 0;
        }

        return 0;
    }

    public static int runToggleTimeSkipping(CommandContext<ServerCommandSource> ctx) {

        ServerPlayerEntity serverPlayer = ctx.getSource().getPlayer();

        if (serverPlayer != null) {
            BedConfigState state = BedConfigState.getServerState(serverPlayer.getServerWorld());
            BedConfigData data = state.getByUUID(serverPlayer.getUuid());

            if (state == null || data == null)
                return 0;

            BedConfigData updatedData = new BedConfigData(
                    data.darknessEnabled(),
                    !data.timeEnabled(),
                    data.guiEnabled());

            state.setByUUID(serverPlayer.getUuid(), updatedData);
            BedConfig.sync(serverPlayer, data);
            listConfigData(serverPlayer, updatedData);

            return updatedData.timeEnabled() ? 1 : 0;
        }

        return 0;
    }

    public static int runToggleGUI(CommandContext<ServerCommandSource> ctx) {

        ServerPlayerEntity serverPlayer = ctx.getSource().getPlayer();

        if (serverPlayer != null) {
            BedConfigState state = BedConfigState.getServerState(serverPlayer.getServerWorld());
            BedConfigData data = state.getByUUID(serverPlayer.getUuid());

            if (state == null || data == null)
                return 0;

            BedConfigData updatedData = new BedConfigData(
                    data.darknessEnabled(),
                    data.timeEnabled(),
                    !data.guiEnabled());

            state.setByUUID(serverPlayer.getUuid(), updatedData);
            BedConfig.sync(serverPlayer, data);
            listConfigData(serverPlayer, updatedData);

            return updatedData.guiEnabled() ? 1 : 0;
        }

        return 0;
    }

    public static void listConfigData(ServerPlayerEntity player, BedConfigData data){

        if (player == null || data == null)
            return;

        player.sendMessage(Text
        .literal("Bed Config Options").formatted(Formatting.YELLOW, Formatting.BOLD)
        .append(Text.literal(" -- ").formatted(Formatting.YELLOW,Formatting.RESET))
        .append(Text.literal(player.getName().getString()).formatted(Formatting.WHITE))
        .append(Text.literal("\n> ").formatted(Formatting.WHITE))
            .append(Text.literal("Darkness: ").formatted(Formatting.GOLD))
            .append(data.darknessEnabled() ? Text.literal("true").formatted(Formatting.GREEN) : Text.literal("false").formatted(Formatting.RED))
        .append(Text.literal("\n> ").formatted(Formatting.WHITE))
            .append(Text.literal("Time Skipping: ").formatted(Formatting.GOLD))
            .append(data.timeEnabled() ? Text.literal("true").formatted(Formatting.GREEN) : Text.literal("false").formatted(Formatting.RED))
        .append(Text.literal("\n> ").formatted(Formatting.WHITE))
            .append(Text.literal("Leave Bed GUI: ").formatted(Formatting.GOLD))
            .append(data.guiEnabled() ? Text.literal("true").formatted(Formatting.GREEN) : Text.literal("false").formatted(Formatting.RED))
        );
    }
}
