package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandOutChar extends IPietCommand
{
	public CommandOutChar(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0205; }
	
	@Override
	public boolean execute()
	{
		// TODO: Nothing
		
		return true;	
	}
}
