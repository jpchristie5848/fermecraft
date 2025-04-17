package com.jipthechip.block;

import com.jipthechip.block.base.GeoBlockBase;
import com.jipthechip.block.blockentity.MasonJarBlockEntity;
import com.jipthechip.item.ModItems;
import com.jipthechip.util.InventoryUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MasonJarBlock extends GeoBlockBase {
    protected MasonJarBlock(Settings settings, String registryName, FabricBlockEntityTypeBuilder.Factory blockEntityFactory, BlockEntityTicker ticker) {
        super(settings, registryName, blockEntityFactory, ticker);
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof MasonJarBlockEntity masonJarBlockEntity){
            ItemStack stackInHand = player.getStackInHand(Hand.MAIN_HAND);
            Item itemInHand = stackInHand.getItem();

            if(masonJarBlockEntity.takeYeast()){
                if(stackInHand == ItemStack.EMPTY){
                    player.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.YEAST));
                }else if(itemInHand == ModItems.YEAST && stackInHand.getCount() < stackInHand.getMaxCount()){
                    stackInHand.setCount(stackInHand.getCount()+1);
                }else{
                    world.spawnEntity(new ItemEntity(world, pos.getX()+0.5, pos.getY() + 0.25, pos.getZ() + 0.5, new ItemStack(ModItems.YEAST), 0,0,0));
                }
            }else if(itemInHand == Items.PAPER){
                if(masonJarBlockEntity.cover()){
                    player.setStackInHand(Hand.MAIN_HAND, InventoryUtil.takeOneFromStack(stackInHand));
                }
            }else if(itemInHand == Items.WATER_BUCKET){
                masonJarBlockEntity.setHasWater(true);
            }else if(itemInHand == Items.WHEAT){
                if(masonJarBlockEntity.addWheat()){
                    player.setStackInHand(Hand.MAIN_HAND, InventoryUtil.takeOneFromStack(stackInHand));
                }
            }else if(itemInHand == Items.SUGAR){
                if(masonJarBlockEntity.addSugar()){
                    player.setStackInHand(Hand.MAIN_HAND, InventoryUtil.takeOneFromStack(stackInHand));
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(world.getBlockEntity(pos) instanceof MasonJarBlockEntity masonJarBlockEntity){
            if(masonJarBlockEntity.isCovered()){
                world.spawnEntity(new ItemEntity(world, pos.getX()+0.5, pos.getY() + 0.25, pos.getZ() + 0.5, new ItemStack(Items.PAPER), 0,0,0));
            }
            if(masonJarBlockEntity.hasWheat()){
                world.spawnEntity(new ItemEntity(world, pos.getX()+0.5, pos.getY() + 0.25, pos.getZ() + 0.5, new ItemStack(Items.WHEAT), 0,0,0));
            }
            if(masonJarBlockEntity.hasSugar()){
                world.spawnEntity(new ItemEntity(world, pos.getX()+0.5, pos.getY() + 0.25, pos.getZ() + 0.5, new ItemStack(Items.SUGAR), 0,0,0));
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    public static VoxelShape makeShape() {
        return VoxelShapes.union(
                VoxelShapes.cuboid(0.3125, 0, 0.375, 0.6875, 0.0625, 0.625),
                VoxelShapes.cuboid(0.625, 0.0625, 0.375, 0.6875, 0.3125, 0.625),
                VoxelShapes.cuboid(0.3125, 0.0625, 0.375, 0.375, 0.3125, 0.625),
                VoxelShapes.cuboid(0.375, 0.0625, 0.3125, 0.625, 0.3125, 0.375),
                VoxelShapes.cuboid(0.375, 0.0625, 0.625, 0.625, 0.3125, 0.6875),
                VoxelShapes.cuboid(0.375, 0, 0.3125, 0.625, 0.0625, 0.375),
                VoxelShapes.cuboid(0.375, 0, 0.625, 0.625, 0.0625, 0.6875),
                VoxelShapes.cuboid(0.375, 0.3125, 0.3125, 0.625, 0.375, 0.6875),
                VoxelShapes.cuboid(0.3125, 0.3125, 0.375, 0.375, 0.375, 0.625),
                VoxelShapes.cuboid(0.625, 0.3125, 0.375, 0.6875, 0.375, 0.625)
        );
    }
}
