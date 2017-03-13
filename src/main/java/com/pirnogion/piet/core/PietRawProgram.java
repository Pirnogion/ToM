package com.pirnogion.piet.core;

import com.pirnogion.utils.Point;

public final class PietRawProgram
{
	private int edge = -1;
	
	private final int width;
	private final int height;
	private final int[][] program; 
//	{
//		{0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0xFF0000, 0xC000C0, 0x000000, 0x000000},
//		{0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0x000000, 0xC000C0, 0x000000, 0x000000},
//		{0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0x000000, 0xC000C0, 0x000000, 0x000000},
//		{0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0xFFC0C0, 0x000000, 0xC000C0, 0x000000, 0x000000},
//		{0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0xC000C0, 0x000000, 0x000000},
//		{0x000000, 0x000000, 0x000000, 0x000000, 0xC000C0, 0xC000C0, 0xC000C0, 0xFF0000},
//		{0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
//		{0x000000, 0x000000, 0xC000C0, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000}
//	};
	
	public PietRawProgram(int[][] program, int width, int height)
	{
		this.program = program;
		this.width = width;
		this.height = height;
	}
	
	public int getColor(int posX, int posY)
	{
		if ( posX < 0 || posX >= width || posY < 0 || posY >= height )
			return edge;
		
		return program[posY][posX];
	}
	public int getColor(Point<Integer> point)
	{
		if ( point.getX() < 0 || point.getX() >= width || point.getY() < 0 || point.getY() >= height )
			return edge;
		
		return program[point.getY()][point.getX()];
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
