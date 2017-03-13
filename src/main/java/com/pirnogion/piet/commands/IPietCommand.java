package com.pirnogion.piet.commands;

import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietStack;

public abstract class IPietCommand
{
	protected short signature;
	
	protected PietProgram interpreter;
	
	protected PietStack stack;
	protected int value;
	protected Runnable callback;
	
	protected IPietCommand(PietProgram interpreter, PietStack stack)
		{ this.interpreter = interpreter; this.stack = stack; };
		
	protected IPietCommand(PietProgram interpreter, PietStack stack, int value)
		{ this.interpreter = interpreter; this.stack = stack; this.value = value; };
		
	protected IPietCommand(PietProgram interpreter, PietStack stack, Runnable callback)
		{ this.interpreter = interpreter; this.stack = stack; this.callback = callback; };
		
	protected IPietCommand(PietProgram interpreter, PietStack stack, int value, Runnable callback)
		{ this.interpreter = interpreter; this.stack = stack; this.value = value; this.callback = callback; };
	
	public final short getSignature() { return signature; }
	
	public abstract boolean execute();
}
