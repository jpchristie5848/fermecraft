package com.jipthechip.block;

import com.jipthechip.block.base.GeoBlockBase;
import com.jipthechip.block.blockentity.QuernBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class QuernBlock extends GeoBlockBase {
    public QuernBlock(Settings settings, String registryName, FabricBlockEntityTypeBuilder.Factory blockEntityFactory, BlockEntityTicker ticker) {
        super(settings, registryName, blockEntityFactory, ticker);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof QuernBlockEntity quernBlockEntity){
            if(player.isSneaking() && !world.isClient){
                player.openHandledScreen(quernBlockEntity);
                return ActionResult.SUCCESS;
            }
            else if(! player.isSneaking() && quernBlockEntity.triggerUpdate()){
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    public static VoxelShape makeShape() {
        return VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.5, 1),
                VoxelShapes.cuboid(0.125, 0.5, 0.5625, 0.875, 0.75, 0.875),
                VoxelShapes.cuboid(0.25, 0.75, 0.25, 0.3125, 1, 0.3125),
                VoxelShapes.cuboid(0.125, 0.5, 0.125, 0.875, 0.75, 0.4375),
                VoxelShapes.cuboid(0.5625, 0.5, 0.4375, 0.875, 0.75, 0.5625),
                VoxelShapes.cuboid(0.125, 0.5, 0.4375, 0.4375, 0.75, 0.5625)
        );
    }
}
