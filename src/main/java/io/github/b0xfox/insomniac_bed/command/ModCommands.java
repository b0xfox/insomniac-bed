package io.github.b0xfox.insomniac_bed.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register(BedConfigCommand::register);
    }
}
