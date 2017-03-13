package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandPush extends IPietCommand
{
	public CommandPush(PietProgram interpreter, PietStack stack, int value)
		{ super(interpreter, stack, value); this.signature = 0x0100; }
	
	@Override
	public boolean execute()
	{
		stack.push(value);
		
		return true;
	}
}
