package com.jipthechip.client.gui;

import com.jipthechip.RegistryNames;
import com.jipthechip.client.gui.block.*;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.screen.ScreenHandlerType;

import java.util.HashMap;

public class ModGUIs {

    public static HashMap<String, ScreenHandlerType> screenHandlerMap = new HashMap<>(){{
        //put(RegistryNames.QUERN, register(RegistryNames.QUERN, QuernScreenHandler::new, BlockPosPayload.PACKET_CODEC));
    }};
    public static HashMap<String, ScreenHandlerContainer> screenHandlerContainerMap = new HashMap<>(){{
        put(RegistryNames.QUERN, new ScreenHandlerContainer(RegistryNames.QUERN,
                (syncId, playerInventory, payload) -> new QuernScreenHandler(syncId, playerInventory, (BlockPosPayload) payload), BlockPosPayload.PACKET_CODEC,
                (handler, inventory, title) -> new QuernScreen((QuernScreenHandler) handler, inventory, title)));

        put(RegistryNames.FERMENTING_BARREL, new ScreenHandlerContainer(RegistryNames.FERMENTING_BARREL,
                (syncId, playerInventory, payload) -> new FermentingBarrelScreenHandler(syncId, playerInventory, (BlockPosPayload) payload), BlockPosPayload.PACKET_CODEC,
                (handler, inventory, title) -> new FermentingBarrelScreen((FermentingBarrelScreenHandler) handler, inventory, title)));

        put(RegistryNames.MALT_ROASTER, new ScreenHandlerContainer(RegistryNames.MALT_ROASTER,
                (syncId, playerInventory, payload) -> new MaltRoasterScreenHandler(syncId, playerInventory, (BlockPosPayload) payload), BlockPosPayload.PACKET_CODEC,
                (handler, inventory, title) -> new MaltRoasterScreen((MaltRoasterScreenHandler) handler, inventory, title)));

        put(RegistryNames.MALTER, new ScreenHandlerContainer(RegistryNames.MALTER,
                (syncId, playerInventory, payload) -> new MalterScreenHandler(syncId, playerInventory, (BlockPosPayload) payload), BlockPosPayload.PACKET_CODEC,
                (handler, inventory, title) -> new MalterScreen((MalterScreenHandler) handler, inventory, title)));

        put(RegistryNames.MASHER, new ScreenHandlerContainer(RegistryNames.MASHER,
                (syncId, playerInventory, payload) -> new MasherScreenHandler(syncId, playerInventory, (BlockPosPayload) payload), BlockPosPayload.PACKET_CODEC,
                (handler, inventory, title) -> new MasherScreen((MasherScreenHandler) handler, inventory, title)));
    }};

//    public static <T extends ScreenHandler, P extends CustomPayload> ExtendedScreenHandlerType<T, P> register(String name,
//        ExtendedScreenHandlerType.ExtendedFactory<T, P> factory, PacketCodec<? super RegistryByteBuf, P> codec){
//        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Fermecraft.MOD_ID, name), new ExtendedScreenHandlerType<>(factory, codec));
//    }

    public static void load(){}
}
