package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandNot extends IPietCommand
{
	public CommandNot(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0202; }
	
	@Override
	public boolean execute()
	{
		stack.push( (stack.pop() != 0) ? 0 : 1 );
		
		return true;
	}
}
