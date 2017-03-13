package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandDivide extends IPietCommand
{
	public CommandDivide(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0002; }
	
	@Override
	public boolean execute()
	{
		int op1 = stack.pop();
		int op2 = stack.pop();
		
		stack.push( op2 / op1 );
		
		return true;
	}
}
