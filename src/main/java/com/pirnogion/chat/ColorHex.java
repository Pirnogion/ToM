package com.pirnogion.chat;

public class ColorHex
{
	public final int inthexcolor;
	
	public ColorHex(final ColorHex hexcolor)
	{
		this.inthexcolor = hexcolor.inthexcolor;
	}
	
	public ColorHex(final int inthexcolor)
	{
		this.inthexcolor = inthexcolor;
	}
	
	public ColorHex(final int red, final int green, final int blue, final int alpha)
	{
		this.inthexcolor = (alpha << 24) & (blue << 16) & (green << 8) & red;
	}
	
	public ColorHex(final ColorRGB rgb)
	{
		this.inthexcolor = rgb.getIntHex();
	}
	
	public ColorRGB getRGB()
	{
		return new ColorRGB(inthexcolor);
	}
}
