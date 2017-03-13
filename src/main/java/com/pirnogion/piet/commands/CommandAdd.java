package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandAdd extends IPietCommand
{	
	public CommandAdd(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0001; }
	
	@Override
	public boolean execute()
	{
		stack.push( stack.pop() + stack.pop() );
		
		return true;
	}
}
