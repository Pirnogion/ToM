package com.pirnogion.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import org.lwjgl.input.Keyboard;

import com.pirnogion.events.HoloEvent;
import com.pirnogion.events.MainEventsHandler;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding PIET_KEY;

    public void registerEventHandlers()
    {
    	super.registerEventHandlers();
    	
        FMLCommonHandler.instance().bus().register(new MainEventsHandler());
        //FMLCommonHandler.instance().bus().register(new HoloEvent());
    }

    public void init()
    {
        super.init();

        /* KEY BINDINGS */
        PIET_KEY = new KeyBinding("key.openpietgui", Keyboard.KEY_P, "key.categories.ToM");
        ClientRegistry.registerKeyBinding(PIET_KEY);
    }
}
