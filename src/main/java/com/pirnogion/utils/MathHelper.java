package com.pirnogion.utils;

public final class MathHelper
{
	public static short encodeToBCD(byte valueH, byte valueL)
	{
		return (short) ((valueH << 8) + valueL);
	}
	
	public static Pair<Byte> decodeFromBCD(short BCDValue)
	{
		return new Pair<Byte>( (byte)(BCDValue >> 8), (byte)BCDValue );
	}
	
	public static double recalcValueToPercents(final double currentValue, final double maxValue)
	{
		return (maxValue < Double.MIN_VALUE) ? 0.0d : (currentValue * 100.0d) / maxValue;
	}
	
	public static double recalcPercentsToValue(final double percents, final double maxValue )
	{
		return (maxValue * percents) / 100.0d;
	}
}
