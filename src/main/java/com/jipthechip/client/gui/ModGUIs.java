package com.jipthechip.client.gui;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.client.gui.block.QuernScreenHandler;
import com.jipthechip.network.BlockPosPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModGUIs {


    public static final ScreenHandlerType<QuernScreenHandler> QUERN_SCREEN_HANDLER = register(RegistryNames.QUERN, QuernScreenHandler::new, BlockPosPayload.PACKET_CODEC);


    public static <T extends ScreenHandler, P extends CustomPayload> ExtendedScreenHandlerType<T, P> register(String name,
        ExtendedScreenHandlerType.ExtendedFactory<T, P> factory, PacketCodec<? super RegistryByteBuf, P> codec){
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Fermecraft.MOD_ID, name), new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static void load(){}
}
