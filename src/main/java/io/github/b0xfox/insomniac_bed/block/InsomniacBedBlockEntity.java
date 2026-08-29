package io.github.b0xfox.insomniac_bed.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class InsomniacBedBlockEntity extends BlockEntity {

    private final DyeColor color;

    public InsomniacBedBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, getColorFromState(state));
    }

    public InsomniacBedBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(ModBlocks.BLOCK_ENTITY_TYPE, pos, state);
        this.color = color;
    }

    private static DyeColor getColorFromState(BlockState state) {
        if (state.getBlock() instanceof InsomniacBedBlock bed) {
            return bed.getColor();
        }
        return DyeColor.WHITE;
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
