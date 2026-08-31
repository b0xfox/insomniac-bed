package io.github.b0xfox.insomniac_bed.block;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InsomniacBedBlock extends BedBlock {

    public InsomniacBedBlock(DyeColor color, Settings settings) {
        super(color, settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (state.get(PART) != BedPart.HEAD) {
            pos = pos.offset(state.get(FACING));
            state = world.getBlockState(pos);
            if (!state.isOf(this)) {
                return ActionResult.CONSUME;
            }
        }

        if (player.isSleeping()) {
            player.wakeUp();
            return ActionResult.SUCCESS_SERVER;
        }

        if ((Boolean) state.get(OCCUPIED)) {
            return ActionResult.SUCCESS_SERVER;
        } else {
            player.sleep(pos);
            if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
                player.incrementStat(Stats.SLEEP_IN_BED);
                Criteria.SLEPT_IN_BED.trigger(serverPlayer);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 0.15f, 1.5f);
        }

        return ActionResult.SUCCESS_SERVER;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InsomniacBedBlockEntity(pos, state, getColor());
    }
}
