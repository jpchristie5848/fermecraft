package com.jipthechip.block.base;

import com.jipthechip.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GeoBlockBase extends BlockWithEntity {

    private final FabricBlockEntityTypeBuilder.Factory blockEntityFactory;
    private String registryName;
    private final Settings settings;

    private final BlockEntityTicker ticker;

    public GeoBlockBase(Settings settings, String registryName, FabricBlockEntityTypeBuilder.Factory blockEntityFactory, BlockEntityTicker ticker) {
        super(settings);
        this.registryName = registryName;
        this.settings = settings;
        this.blockEntityFactory = blockEntityFactory;
        this.ticker = ticker;
    }

    private GeoBlockBase getBlock(Settings settings){
        return this;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(this::getBlock);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityFactory.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlocks.blockEntityMap.get(registryName), ticker);
    }
}
