package com.pirnogion.piet.core;

import com.pirnogion.utils.MathHelper;
import com.pirnogion.utils.Pair;

public final class PietColorset
{
	public static final byte reservedColors = 2;

	public static final byte colormapWidth = 6;
	public static final byte colormapHeight = 3;
	public static final byte countOfColors = colormapWidth*colormapHeight+reservedColors-1;

	public static final int[][] colormap =
	{
		{0xFFC0C0, 0xFFFFC0, 0xC0FFC0, 0xC0FFFF, 0xC0C0FF, 0xFFC0FF},
		{0xFF0000, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF, 0xFF00FF},
		{0xC00000, 0xC0C000, 0x00C000, 0x00C000, 0x0000C0, 0xC000C0}
	};
	
	public static int getEdge() { return -1; }
	public static int getBlackColor() { return 0x000000; }
	public static int getWhiteColor() { return 0xFFFFFF; }
	
	public static int getColorFrom(int index)
	{
		if ( index < 0 || index > colormapHeight*colormapWidth+1) throw new IndexOutOfBoundsException();

		if ( index == 0 ) { return getBlackColor(); }
		if ( index == 1 ) { return getWhiteColor(); }
		
		int x = (index-reservedColors) % colormapWidth;
		int y = (index-reservedColors) / colormapWidth;
		
		return colormap[y][x];
	}
	
	public static int getIndexFrom(int color)
	{
		if ( color == getBlackColor() ) { return 0; }
		if ( color == getWhiteColor() ) { return 1; }
		
		Pair<Byte> colorSatHue = getColorIndexes(color);
		
		return colormapWidth * colorSatHue.getValue1() + colorSatHue.getValue2() + reservedColors;
	}
	
	public static Pair<Byte> getColorIndexes(int color)
	{
		for (byte sat = 0; sat < colormapHeight; ++sat)
			for (byte hue = 0; hue < colormapWidth; ++hue)
				if ( color == colormap[sat][hue] ) return new Pair<Byte>(sat, hue);
		
		return null;
	}
	
	public static short getCommandSignature(int firstColor, int secondColor)
	{
		/* pc - [p]rev. [c]olor; cc - [c]urrent [c]olor; */
		
		Pair<Byte> pcSatHue = getColorIndexes(firstColor);
		Pair<Byte> ccSatHue = getColorIndexes(secondColor);
		
		byte pcSat = pcSatHue.getValue1();
		byte pcHue = pcSatHue.getValue2();
		
		byte ccSat = ccSatHue.getValue1();
		byte ccHue = ccSatHue.getValue2();
		
		byte satLoops = (byte) ((pcSat <= ccSat) ? ccSat - pcSat : (ccSat - pcSat) + colormapHeight);
		byte hueLoops = (byte) ((pcHue <= ccHue) ? ccHue - pcHue : (ccHue - pcHue) + colormapWidth);
		
		return MathHelper.encodeToBCD(satLoops, hueLoops);
	}
}
