package com.jipthechip.block;

import com.jipthechip.block.base.GeoBlockBase;
import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.block.blockentity.QuernBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FermentingBarrelBlock extends GeoBlockBase {
    public FermentingBarrelBlock(Settings settings, String registryName, FabricBlockEntityTypeBuilder.Factory blockEntityFactory, BlockEntityTicker ticker) {
        super(settings, registryName, blockEntityFactory, ticker);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof FermentingBarrelBlockEntity fermentingBarrelBlockEntity){
            if(player.isSneaking()){
                if(!world.isClient){
                    player.openHandledScreen(fermentingBarrelBlockEntity);
                }
                fermentingBarrelBlockEntity.open();
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hit);
    }
}
