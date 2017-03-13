package com.pirnogion.chat;

public class ChatColoredString
{
	private String string;
	private ColorHex color;
	
	public ChatColoredString(String string, ColorHex color)
	{
		this.string = string; this.color = color;
	}
	
	public String getString() { return string; }
	public ColorHex getColor() { return color; }
	
	public void setString(String string) { this.string = string; }
	public void setColor(ColorHex color) { this.color = color; }
}
