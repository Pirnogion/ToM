package com.pirnogion.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class NetworkPacket implements IMessage
{
	int waterLevelData;
	Vec3d positionData;
	
	public NetworkPacket(int waterLevel, Vec3d position)
	{
		this.waterLevelData = waterLevel;
		this.positionData = position;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			
			waterLevelData = buf.readInt();
			positionData = new Vec3d(x, y, z);
		}
		catch (IndexOutOfBoundsException ioe)
		{
			System.out.println("Error decode data packet.");
			return;
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeDouble(positionData.xCoord);
		buf.writeDouble(positionData.yCoord);
		buf.writeDouble(positionData.zCoord);
		
		buf.writeInt(waterLevelData);
	}

	@Override
	public String toString()
	{
		return "Message[position=" + String.valueOf(positionData) + ", " + String.valueOf(waterLevelData) + "]";
	}
}
