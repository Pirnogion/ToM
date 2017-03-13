package com.pirnogion.piet.core;

import java.util.ArrayList;

public final class PietStack
{
	private final int maxStackSize;
	
	private ArrayList<Integer> stack;
	
	public PietStack(int maxStackSize)
	{
		if (maxStackSize < 1) throw new IndexOutOfBoundsException();

		this.maxStackSize = maxStackSize;
		this.stack = new ArrayList<Integer>();
	}
	
	public void replace(int pos, Integer newValue)
	{
		if (pos >= 0 && pos < stack.size()) stack.set(pos, newValue);
	}
	
	public void push(Integer value)
	{
		if (stack.size() < maxStackSize) stack.add(value);
	}
	
	public Integer pop()
	{
		if (stack.size() < 1) return 0;
		
		return stack.remove( stack.size()-1 );
	}
	
	public int getMaxSize()
	{
		return maxStackSize;
	}
	
	public int getSize()
	{
		return stack.size();
	}
}
