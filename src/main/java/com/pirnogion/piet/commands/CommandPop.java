package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandPop extends IPietCommand
{
	public CommandPop(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0200; }
	
	@Override
	public boolean execute()
	{
		stack.pop();
		
		return true;
	}
}
