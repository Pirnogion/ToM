package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandInChar extends IPietCommand
{
	public CommandInChar(PietProgram interpreter, PietStack stack, int value)
		{ super(interpreter, stack, value); this.signature = 0x0005; }
	
	@Override
	public boolean execute()
	{
		// TODO: Nothing
		
		return true;
	}
}
