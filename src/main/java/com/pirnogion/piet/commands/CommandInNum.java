package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandInNum extends IPietCommand
{
	public CommandInNum(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0204; }
	
	@Override
	public boolean execute()
	{
		// TODO: Nothing
		
		return true;
	}
}
