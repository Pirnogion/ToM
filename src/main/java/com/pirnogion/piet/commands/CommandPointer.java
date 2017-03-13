package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class CommandPointer extends IPietCommand
{	
	public CommandPointer(PietProgram interpreter, PietStack stack)
		{ super(interpreter, stack); this.signature = 0x0103; }
	
	@Override
	public boolean execute()
	{
		int countOfRotations = stack.pop();
		
		for (int i = 0; i < countOfRotations; ++i)
			if (countOfRotations > 0) interpreter.rotatePointerClockwise();
			else interpreter.rotatePointerAnticlockwise();
		
		return true;
	}
}
