package com.jipthechip.client.gui.block;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.client.gui.ModGUIs;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

public class ScreenHandlerContainer<S extends Screen & ScreenHandlerProvider<H>, H extends ScreenHandler, P extends CustomPayload> {

    private final String registryName;
    private final ExtendedScreenHandlerType.ExtendedFactory<H, P> screenHandlerFactory;

    private final PacketCodec<? super RegistryByteBuf, P> codec;

    private final HandledScreens.Provider<H, S> screenFactory;

    public ScreenHandlerContainer(String registryName, ExtendedScreenHandlerType.ExtendedFactory<H, P> screenHandlerFactory,
                                  PacketCodec<? super RegistryByteBuf, P> codec, HandledScreens.Provider<H, S> screenFactory){
        this.registryName = registryName;
        this.screenHandlerFactory = screenHandlerFactory;
        this.codec = codec;
        this.screenFactory = screenFactory;
        register();
    }

    public void register(){
        ModGUIs.screenHandlerMap.put(registryName, Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Fermecraft.MOD_ID, registryName), new ExtendedScreenHandlerType<>(screenHandlerFactory, codec)));
    }

    public void registerClient(){
        HandledScreens.register(ModGUIs.screenHandlerMap.get(registryName), screenFactory);
    }
}
