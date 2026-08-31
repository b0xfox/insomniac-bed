package io.github.b0xfox.insomniac_bed.client.block;

import io.github.b0xfox.insomniac_bed.block.InsomniacBedBlockEntity;
import io.github.b0xfox.insomniac_bed.block.ModBlocks;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class InsomniacBedBlockEntityRenderer implements BlockEntityRenderer<InsomniacBedBlockEntity> {
    private final Model bedHead;
    private final Model bedFoot;

    public InsomniacBedBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        var models = ctx.getLoadedEntityModels();
        this.bedHead = new Model.SinglePartModel(models.getModelPart(EntityModelLayers.BED_HEAD), RenderLayer::getEntitySolid);
        this.bedFoot = new Model.SinglePartModel(models.getModelPart(EntityModelLayers.BED_FOOT), RenderLayer::getEntitySolid);
    }

    @Override
    public void render(InsomniacBedBlockEntity bedBlockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = bedBlockEntity.getWorld();
        if (world != null) {
            SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getBedTextureId(bedBlockEntity.getColor());
            BlockState blockState = bedBlockEntity.getCachedState();
            
            DoubleBlockProperties.PropertySource<? extends InsomniacBedBlockEntity> propertySource = DoubleBlockProperties.toPropertySource(
                ModBlocks.BLOCK_ENTITY_TYPE,
                BedBlock::getBedPart,
                BedBlock::getOppositePartDirection,
                ChestBlock.FACING,
                blockState,
                world,
                bedBlockEntity.getPos(),
                (worldx, pos) -> false
            );
            
            int combinedLight = ((Int2IntFunction) propertySource.apply(new LightmapCoordinatesRetriever<>())).get(light);
            this.renderPart(matrices, vertexConsumers, blockState.get(BedBlock.PART) == BedPart.HEAD ? this.bedHead : this.bedFoot, blockState.get(BedBlock.FACING), spriteIdentifier, combinedLight, overlay, false);
        }
    }

    public void renderAsItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, SpriteIdentifier textureId) {
        this.renderPart(matrices, vertexConsumers, this.bedHead, Direction.SOUTH, textureId, light, overlay, false);
        this.renderPart(matrices, vertexConsumers, this.bedFoot, Direction.SOUTH, textureId, light, overlay, true);
    }

    private void renderPart(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Model model, Direction direction, SpriteIdentifier sprite, int light, int overlay, boolean isFoot) {
        matrices.push();
        matrices.translate(0.0F, 0.5625F, isFoot ? -1.0F : 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        matrices.translate(0.5F, 0.5F, 0.5F);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F + direction.getPositiveHorizontalDegrees()));
        matrices.translate(-0.5F, -0.5F, -0.5F);
        VertexConsumer vertexConsumer = sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        model.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
    }
}
