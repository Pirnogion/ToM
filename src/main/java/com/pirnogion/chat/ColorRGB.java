package com.pirnogion.chat;

import com.pirnogion.utils.MathHelper;

public final class ColorRGB
{
	public final int red, green, blue, alpha;
	
	public ColorRGB(final int inthexcolor)
	{
		this.red = inthexcolor & 0xFF;
		this.green = (inthexcolor >> 8) & 0xFF;
		this.blue = (inthexcolor >> 16) & 0xFF;
		this.alpha = inthexcolor >> 24;
	}
	
	public ColorRGB(final ColorHex hex)
	{
		this.red = hex.inthexcolor & 0xFF;
		this.green = (hex.inthexcolor >> 8) & 0xFF;
		this.blue = (hex.inthexcolor >> 16) & 0xFF;
		this.alpha = hex.inthexcolor >> 24;
	}
	
	public ColorRGB(final ColorRGB rgb)
	{
		this.red = rgb.red;
		this.green = rgb.green;
		this.blue = rgb.blue;
		this.alpha = rgb.alpha;
	}
	
	public ColorRGB(final int red, final int green, final int blue, final int alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public ColorHex getHex()
	{
		return new ColorHex(red, green, blue, alpha);
	}
	
	public int getIntHex()
	{
		return (alpha << 24) & (blue << 16) & (green << 8) & red;
	}
}
