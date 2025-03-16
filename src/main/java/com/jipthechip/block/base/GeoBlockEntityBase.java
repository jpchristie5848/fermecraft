package com.jipthechip.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.awt.*;
import java.util.Optional;

public class GeoBlockEntityBase extends BlockEntity implements GeoBlockEntity {

    protected String registryName;
    private AnimatableInstanceCache instanceCache = GeckoLibUtil.createInstanceCache(this);

    public GeoBlockEntityBase(BlockEntityType type, BlockPos pos, BlockState state, String registryName) {
        super(type, pos, state);
        this.registryName = registryName;
    }

    public String getRegistryName(){
        return this.registryName;
    }

    public Optional<RecipeBookType> getRecipeBookType() {
        return Optional.empty();
    }
    public Optional<Point> getInventoryGuiPos(){
        return Optional.empty();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return instanceCache;
    }

    public float getCraftingProgress() {
        return 0;
    }

    public float getMaxCraftingProgress(){
        return 0;
    }
}
