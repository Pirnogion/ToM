package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandMultiply extends IPietCommand
{
	public CommandMultiply(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0201; }
	
	@Override
	public boolean execute()
	{
		stack.push( stack.pop() * stack.pop() );
		
		return true;
	}
}
