package com.jipthechip.block;

import com.jipthechip.block.base.GeoBlockBase;
import com.jipthechip.block.blockentity.MalterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MalterBlock extends GeoBlockBase {
    public MalterBlock(Settings settings, String registryName, FabricBlockEntityTypeBuilder.Factory blockEntityFactory, BlockEntityTicker ticker) {
        super(settings, registryName, blockEntityFactory, ticker);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof MalterBlockEntity malterBlockEntity){
            if(player.isSneaking() && !world.isClient){
                player.openHandledScreen(malterBlockEntity);
                return ActionResult.SUCCESS;
            }
            else if(player.getStackInHand(Hand.MAIN_HAND).getItem() == Items.WATER_BUCKET){
                if(malterBlockEntity.addWater()){
                    player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.BUCKET));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}
