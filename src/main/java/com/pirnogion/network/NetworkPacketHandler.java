package com.pirnogion.network;

import java.util.Random;

import com.pirnogion.blocks.specialRendereres.SpecialRendererBarrel;
import com.pirnogion.tileEntity.TileEntityBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class NetworkPacketHandler implements IMessageHandler<NetworkPacket, IMessage>
{
	
	@Override
	public IMessage onMessage(final NetworkPacket message, MessageContext ctx) {
		Minecraft minecraft = Minecraft.getMinecraft();
		final WorldClient worldClient = minecraft.world;
		
		minecraft.addScheduledTask(new Runnable()
		{
			public void run() {
				processMessage(worldClient, message);
			}
		});
		
		return null;
	}

	void processMessage(WorldClient worldClient, NetworkPacket message)
	{	
		System.out.println( message.toString() );
		
		return;
	}
}
