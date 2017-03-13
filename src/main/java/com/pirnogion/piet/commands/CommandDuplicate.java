package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandDuplicate extends IPietCommand
{
	public CommandDuplicate(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0004; }
	
	@Override
	public boolean execute()
	{
		stack.push( stack.pop() << 2 );
		
		return true;
	}
}
