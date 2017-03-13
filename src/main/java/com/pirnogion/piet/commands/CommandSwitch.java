package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandSwitch extends IPietCommand
{
	public CommandSwitch(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0203; }
	
	@Override
	public boolean execute()
	{
		int countOfSwitches = stack.pop();
		
		for (int i = 0; i < countOfSwitches; ++i)
			interpreter.mirrorCodelChooser();
		
		return true;
	}
}
