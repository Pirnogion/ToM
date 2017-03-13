package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandRoll extends IPietCommand
{
	public CommandRoll(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0104; }
	
	@Override
	public boolean execute()
	{
		int countOfRolls = stack.pop();
		int toDepth = stack.pop();
		
		int direction = (countOfRolls > 0) ? 1 : -1;
		
		while ( stack.getSize() < toDepth-1 ) stack.push(0);
		
		for (int i = 0; i < countOfRolls; ++i)
			stack.replace(toDepth + (i * direction), countOfRolls);
		
		return true;
	}
}
