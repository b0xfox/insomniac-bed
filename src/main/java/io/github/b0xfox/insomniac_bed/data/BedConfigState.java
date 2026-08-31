package io.github.b0xfox.insomniac_bed.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class BedConfigState extends PersistentState{

    private final Map<UUID, BedConfigData> players = new HashMap<>();

    public static final PersistentState.Type<BedConfigState> TYPE = new PersistentState.Type<>(BedConfigState::new, BedConfigState::createFromNbt, null);

    public BedConfigState() {}
    
    public static BedConfigState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        BedConfigState state = new BedConfigState();
        NbtCompound mapNbt = nbt.getCompound("Players");

        for (String uuidString : mapNbt.getKeys()) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                NbtCompound playerNbt = mapNbt.getCompound(uuidString);
                state.players.put(uuid, BedConfigData.fromNbt(playerNbt));
            } catch (IllegalArgumentException ignored) {}
        }
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound mapNbt = new NbtCompound();

        for (Map.Entry<UUID, BedConfigData> entry : players.entrySet()) {
            mapNbt.put(entry.getKey().toString(), entry.getValue().toNbt());
        }

        nbt.put("Players", mapNbt);
        return nbt;
    }

    public BedConfigData getByUUID(UUID playerUuid) {
        return players.getOrDefault(playerUuid, BedConfigData.createDefault());
    }

    public void setByUUID(UUID playerUuid, BedConfigData data) {
        players.put(playerUuid, data);
        this.markDirty();
    }

    public static BedConfigState getServerState(ServerWorld world) {
        PersistentStateManager stateManager = world.getServer().getWorld(World.OVERWORLD).getPersistentStateManager();
        return stateManager.getOrCreate(TYPE, "insomniac_bed_data");
    }
}
