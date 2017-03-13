package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public final class PietCommandManager
{
	private static IPietCommand getCmd(short signature, PietProgram interpreter, PietStack stack, int value, Runnable callback)
	{
		switch (signature)
		{
			case 0x0001: return new CommandAdd(interpreter, stack);
			case 0x0002: return new CommandDivide(interpreter, stack);
			case 0x0003: return new CommandGreater(interpreter, stack);
			case 0x0004: return new CommandDuplicate(interpreter, stack);
			case 0x0005: return new CommandInChar(interpreter, stack, value);
			
			case 0x0100: return new CommandPush(interpreter, stack, value);
			case 0x0101: return new CommandSubstract(interpreter, stack);
			case 0x0102: return new CommandMod(interpreter, stack);
			case 0x0103: return new CommandPointer(interpreter, stack);
			case 0x0104: return new CommandRoll(interpreter, stack);
			case 0x0105: return new CommandOutNum(interpreter, stack);
			
			case 0x0200: return new CommandPop(interpreter, stack);
			case 0x0201: return new CommandMultiply(interpreter, stack);
			case 0x0202: return new CommandNot(interpreter, stack);
			case 0x0203: return new CommandSwitch(interpreter, stack);
			case 0x0204: return new CommandInNum(interpreter, stack);
			case 0x0205: return new CommandOutChar(interpreter, stack);
		}
		
		return null;
	}
	
	public static IPietCommand getCommand(short signature, PietProgram interpreter, PietStack stack)
	{
		return getCmd(signature, interpreter, stack, 0, null);
	}
	
	public static IPietCommand getCommand(short signature, PietProgram interpreter, PietStack stack, int value)
	{
		return getCmd(signature, interpreter, stack, value, null);
	}
	
	public static IPietCommand getCommand(short signature, PietProgram interpreter, PietStack stack, Runnable callback)
	{
		return getCmd(signature, interpreter, stack, 0, callback);
	}
	
	public static IPietCommand getCommand(short signature, PietProgram interpreter, PietStack stack, int value, Runnable callback)
	{
		return getCmd(signature, interpreter, stack, value, callback);
	}
}
