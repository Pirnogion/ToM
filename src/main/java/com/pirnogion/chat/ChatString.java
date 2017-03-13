package com.pirnogion.chat;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatString
{	
	private static final String hexcolorPattern = "#[0-9a-fA-F]{8}";
	private static final String wordPattern = "((?!#[0-9a-fA-F]{8}).)*";
	private ArrayList<ChatColoredString> message;
	
	public ChatString(String rawString)
	{
		this.message = parseRawString(rawString);
	}
	
	public static ArrayList<ChatColoredString> parseRawString(String rawString)
	{
		ArrayList<ChatColoredString> result = new ArrayList<ChatColoredString>();
		
		String[] words  = rawString.split(hexcolorPattern);
		String[] colors = rawString.split(hexcolorPattern);
		
		return result;
	}
	
	public int getSize()
	{
		return message.size();
	}
	
	public ChatColoredString getString(int id)
	{
		return message.get(id);
	}
}
