package io.github.b0xfox.insomniac_bed.command;

import java.util.Optional;

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
import net.minecraft.world.GameRules;

public class BedConfigCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                CommandManager.literal("bed").executes(BedConfigCommand::runRoot)

                        .then(
                                CommandManager.literal("darkness")
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                                .executes(BedConfigCommand::runToggleDarkness)))

                        .then(
                                CommandManager.literal("advance_time")
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                                .executes(BedConfigCommand::runToggleTimeSkipping)))
                        .then(
                                CommandManager.literal("limits")
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                                .executes(BedConfigCommand::runToggleLimits)))

                        .then(
                                CommandManager.literal("gui")
                                        .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                                                .executes(BedConfigCommand::runToggleGUI))));
    }

    public static int runRoot(CommandContext<ServerCommandSource> ctx) {

        ServerCommandSource source = ctx.getSource();
        ServerPlayerEntity player = source.getPlayer();

        if (player != null) {

            BedConfigState state = BedConfigState.getServerState(player.getServerWorld());
            BedConfigData data = state.getByUUID(player.getUuid());

            if (state == null || data == null)
                return 0;

            listConfigData(source, data);

            return 1;
        }

        return 0;
    }

    public static int runToggleDarkness(CommandContext<ServerCommandSource> ctx) {

        boolean enabled = BoolArgumentType.getBool(ctx, "enabled");

        ServerCommandSource source = ctx.getSource();
        Optional<BedConfigData> data = BedConfig.updateOption(source.getPlayer(), BedConfig.Option.DARKNESS, enabled);

        if (!data.isEmpty()) {
            listConfigData(source, data.get());
            return enabled ? 1 : 0;
        }

        source.sendMessage(Text.literal("Failed to retrieve data").formatted(Formatting.RED));
        return 0;
    }

    public static int runToggleTimeSkipping(CommandContext<ServerCommandSource> ctx) {

        boolean enabled = BoolArgumentType.getBool(ctx, "enabled");

        ServerCommandSource source = ctx.getSource();
        Optional<BedConfigData> data = BedConfig.updateOption(source.getPlayer(), BedConfig.Option.ADVANCE_TIME, enabled);

        if (!data.isEmpty()) {
            listConfigData(source, data.get());
            return enabled ? 1 : 0;
        }

        source.sendMessage(Text.literal("Failed to retrieve data").formatted(Formatting.RED));
        return 0;
    }

    public static int runToggleLimits(CommandContext<ServerCommandSource> ctx) {

        boolean enabled = BoolArgumentType.getBool(ctx, "enabled");

        ServerCommandSource source = ctx.getSource();
        Optional<BedConfigData> data = BedConfig.updateOption(source.getPlayer(), BedConfig.Option.LIMITS, enabled);

        if (!data.isEmpty()) {
            listConfigData(source, data.get());
            return enabled ? 1 : 0;
        }

        source.sendMessage(Text.literal("Failed to retrieve data").formatted(Formatting.RED));
        return 0;
    }

    public static int runToggleGUI(CommandContext<ServerCommandSource> ctx) {

        boolean enabled = BoolArgumentType.getBool(ctx, "enabled");

        ServerCommandSource source = ctx.getSource();
        Optional<BedConfigData> data = BedConfig.updateOption(source.getPlayer(), BedConfig.Option.GUI, enabled);

        if (!data.isEmpty()) {
            listConfigData(source, data.get());
            return enabled ? 1 : 0;
        }

        source.sendMessage(Text.literal("Failed to retrieve data").formatted(Formatting.RED));
        return 0;
    }

    public static void listConfigData(ServerCommandSource source, BedConfigData data) {

        if (source == null || data == null)
            return;

        boolean sendCommandFeedback = source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK);

        if (source.getEntity() instanceof ServerPlayerEntity || sendCommandFeedback == true) {

            source.sendMessage(Text
                    .literal("Bed Config Options").formatted(Formatting.YELLOW)
                    .append(Text.literal(" -- ").formatted(Formatting.YELLOW))
                    .append(Text.literal(source.getName()).formatted(Formatting.WHITE))
                    .append(Text.literal("\n> ").formatted(Formatting.WHITE))
                    .append(Text.literal("Darkness: ").formatted(Formatting.GOLD))
                    .append(data.darknessEnabled() ? Text.literal("true").formatted(Formatting.GREEN) : Text.literal("false").formatted(Formatting.RED))
                    .append(Text.literal("\n> ").formatted(Formatting.WHITE))
                    .append(Text.literal("Time Skipping: ").formatted(Formatting.GOLD))
                    .append(data.timeEnabled() ? Text.literal("true").formatted(Formatting.GREEN) : Text.literal("false").formatted(Formatting.RED))
                    .append(Text.literal("\n> ").formatted(Formatting.WHITE))
                    .append(Text.literal("Sleep Limitations: ").formatted(Formatting.GOLD))
                    .append(data.limitsEnabled() ? Text.literal("true").formatted(Formatting.GREEN) : Text.literal("false").formatted(Formatting.RED))
                    .append(Text.literal("\n> ").formatted(Formatting.WHITE))
                    .append(Text.literal("Leave Bed GUI: ").formatted(Formatting.GOLD))
                    .append(data.guiEnabled() ? Text.literal("true").formatted(Formatting.GREEN) : Text.literal("false").formatted(Formatting.RED)));
        }

    }
}
