package com.pirnogion.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.pirnogion.events.HoloEvent;
import com.pirnogion.events.MainEventsHandler;
import com.pirnogion.tom.BlockRegistration;
import com.pirnogion.tom.ItemRegistration;

public class CommonProxy
{
	public void registerEventHandlers()
	{
		//! MinecraftForge.EVENT_BUS.register(target); !//
		MinecraftForge.EVENT_BUS.register(new MainEventsHandler());
		//MinecraftForge.EVENT_BUS.register(new HoloEvent());
	}
	
	public void init()
	{
		// Nothing
	}

	public void registerRenderes()
	{
		ItemRegistration.registerRenderes();
		BlockRegistration.registerRenderes();
	}
}
