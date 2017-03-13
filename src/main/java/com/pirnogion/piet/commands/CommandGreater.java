package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandGreater extends IPietCommand
{
	public CommandGreater(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0003; }
	
	@Override
	public boolean execute()
	{		
		stack.push( ( stack.pop() < stack.pop() ) ? 1 : 0 );
		
		return true;
	}
}
