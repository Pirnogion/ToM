package com.pirnogion.chat;

import java.util.ArrayList;

public class ChatHistory
{
	public static final int historyMaxSize = 3;
	
	private ArrayList<ChatString> history = new ArrayList<ChatString>();
	
	public void saveMessage(ChatString message)
	{
		history.add(message);
		
		if ( history.size() > historyMaxSize )
			history.remove(0);
	}
	
	public int getSize()
	{
		return history.size();
	}
	
	public ChatString getMessage(int id)
	{
		if ( id < history.size() )
			return history.get(id);
		
		return null;
	}
}
