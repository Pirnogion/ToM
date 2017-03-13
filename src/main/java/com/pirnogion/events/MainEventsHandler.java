package com.pirnogion.events;

import com.pirnogion.GUI.PietGUI;
import com.pirnogion.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class MainEventsHandler
{
	@SubscribeEvent
	public void KeyPress(InputEvent.KeyInputEvent event)
	{
		if ( ClientProxy.PIET_KEY.isPressed() )
		{
			Minecraft.getMinecraft().displayGuiScreen( new PietGUI() );
		}
	}
}
