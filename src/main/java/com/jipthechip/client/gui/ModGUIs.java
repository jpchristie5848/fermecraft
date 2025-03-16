package com.jipthechip.client.gui;

import com.jipthechip.RegistryNames;
import com.jipthechip.client.gui.block.QuernScreenHandler;
import com.jipthechip.client.gui.block.ScreenHandlerContainer;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.screen.ScreenHandlerType;
import com.jipthechip.client.gui.block.QuernScreen;

import java.util.HashMap;

public class ModGUIs {

    public static HashMap<String, ScreenHandlerType> screenHandlerMap = new HashMap<>(){{
        //put(RegistryNames.QUERN, register(RegistryNames.QUERN, QuernScreenHandler::new, BlockPosPayload.PACKET_CODEC));
    }};
    public static HashMap<String, ScreenHandlerContainer> screenHandlerContainerMap = new HashMap<>(){{
        put(RegistryNames.QUERN, new ScreenHandlerContainer(RegistryNames.QUERN,
                (syncId, playerInventory, payload) -> new QuernScreenHandler(syncId, playerInventory, (BlockPosPayload) payload), BlockPosPayload.PACKET_CODEC,
                (handler, inventory, title) -> new QuernScreen((QuernScreenHandler) handler, inventory, title)));
    }};

//    public static <T extends ScreenHandler, P extends CustomPayload> ExtendedScreenHandlerType<T, P> register(String name,
//        ExtendedScreenHandlerType.ExtendedFactory<T, P> factory, PacketCodec<? super RegistryByteBuf, P> codec){
//        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Fermecraft.MOD_ID, name), new ExtendedScreenHandlerType<>(factory, codec));
//    }

    public static void load(){}
}
