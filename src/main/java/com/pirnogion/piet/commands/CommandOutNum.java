package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandOutNum extends IPietCommand
{
	public CommandOutNum(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0105; }
	
	@Override
	public boolean execute()
	{
		System.out.println("Number: " + stack.pop().toString());
		
		return true;	
	}
}
