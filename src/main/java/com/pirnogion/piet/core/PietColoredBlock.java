package com.pirnogion.piet.core;

import com.pirnogion.utils.Pair;
import com.pirnogion.utils.Point;

public final class PietColoredBlock
{
	private static class ColoredBlockCalculator
	{
		private PietRawProgram rawProgram;
		private boolean[][] isChecked;
		
		private final int blockColor;
		private int blockSize;
		
		public final Point<Integer> leftCodel, rightCodel, topCodel, bottomCodel;
		
		public ColoredBlockCalculator(PietRawProgram rawProgram, Point<Integer> entryPoint)
		{
			if ( rawProgram.getColor( entryPoint ) != PietColorset.getEdge() )
			{
				this.rawProgram = rawProgram;
				
				this.isChecked = new boolean[rawProgram.getHeight()][rawProgram.getWidth()];
				this.blockSize = 0;
				
				this.blockColor = rawProgram.getColor(entryPoint);
				
				this.leftCodel = new Point<Integer>(rawProgram.getWidth(), rawProgram.getHeight());
				this.rightCodel = new Point<Integer>(0, 0);
				this.topCodel = new Point<Integer>(rawProgram.getWidth(), rawProgram.getHeight());
				this.bottomCodel = new Point<Integer>(0, 0);
				
				recursiveFunction( entryPoint.getX(), entryPoint.getY() );
			}
			else
			{
				this.blockColor = PietColorset.getEdge();
				
				this.leftCodel = new Point<Integer>(0, 0);
				this.rightCodel = new Point<Integer>(0, 0);
				this.topCodel = new Point<Integer>(0, 0);
				this.bottomCodel = new Point<Integer>(0, 0);
			}
		}
		
		public int getBlockSize()
		{
			return blockSize;
		}
		
		public int getBlockColor()
		{
			return blockColor;
		}
		
		private void recursiveFunction(int x, int y)
		{
			isChecked[y][x] = true;
			blockSize += 1;
			
			if ( x-1 >= 0 && isChecked[y][x-1] == false && rawProgram.getColor(x-1, y) == blockColor ) recursiveFunction(x-1, y);
			else if (x < leftCodel.getX()) leftCodel.setXY(x, y);
				
			if ( x+1 < rawProgram.getWidth() && isChecked[y][x+1] == false && rawProgram.getColor(x+1, y) == blockColor ) recursiveFunction(x+1, y);
			else if (x > rightCodel.getX()) rightCodel.setXY(x, y);
			
			if ( y-1 >= 0 && isChecked[y-1][x] == false && rawProgram.getColor(x, y-1) == blockColor ) recursiveFunction(x, y-1);
			else if (y < topCodel.getY()) topCodel.setXY(x, y);
			
			if ( y+1 < rawProgram.getHeight() && isChecked[y+1][x] == false && rawProgram.getColor(x, y+1) == blockColor ) recursiveFunction(x, y+1);
			else if (y > bottomCodel.getY()) bottomCodel.setXY(x, y);
		}
	}
	
	public final int blockSize;
	public final int blockColor;
	public final Point<Integer> leftTopCodel, leftBottomCodel, rightTopCodel, rightBottomCodel;
	public final Point<Integer> topLeftCodel, topRightCodel, bottomLeftCodel, bottomRightCodel;
	
	public PietColoredBlock(PietRawProgram rawProgram, Point<Integer> entryPoint)
	{		
		ColoredBlockCalculator cbcalc = new ColoredBlockCalculator(rawProgram, entryPoint);
		
		Pair<Point<Integer>> _tmp;
		
		_tmp = getEndpoints(rawProgram, cbcalc, cbcalc.leftCodel, PietDirections.VERTICAL);
		this.leftTopCodel = _tmp.getValue1(); this.leftBottomCodel = _tmp.getValue2();
		
		_tmp = getEndpoints(rawProgram, cbcalc, cbcalc.rightCodel, PietDirections.VERTICAL);
		this.rightTopCodel = _tmp.getValue1(); this.rightBottomCodel = _tmp.getValue2();
		
		_tmp = getEndpoints(rawProgram, cbcalc, cbcalc.topCodel, PietDirections.HORIZONTAL);
		this.topLeftCodel = _tmp.getValue1(); this.topRightCodel = _tmp.getValue2();
		
		_tmp = getEndpoints(rawProgram, cbcalc, cbcalc.bottomCodel, PietDirections.HORIZONTAL);
		this.bottomLeftCodel = _tmp.getValue1(); this.bottomRightCodel = _tmp.getValue2();
		
		this.blockColor = cbcalc.getBlockColor();
		this.blockSize = cbcalc.getBlockSize();
	}
	
	private Pair<Point<Integer>> getEndpoints(PietRawProgram rawProgram, ColoredBlockCalculator cbcalc, Point<Integer> pos, boolean dir)
	{
		Point<Integer> left = new Point<Integer>(0, 0);
		Point<Integer> right = new Point<Integer>(0, 0);
		
		if ( dir == PietDirections.HORIZONTAL )
		{
			int posy = pos.getY();
			left.setXY(pos.getX(), posy);
			right.setXY(pos.getX(), posy);
			
			for (int i = 0; i < rawProgram.getWidth(); ++i)
			{
				if ( rawProgram.getColor(i, posy) == cbcalc.blockColor && cbcalc.isChecked[posy][i] )
					left.setX(i);
				
				int inv = (rawProgram.getWidth()-1)-i;
				if ( rawProgram.getColor(inv, posy) == cbcalc.blockColor && cbcalc.isChecked[posy][inv] )
					right.setX(inv);
			}
		}
		else
		{
			int posx = pos.getX();
			left.setXY(posx, pos.getY());
			right.setXY(posx, pos.getY());
			
			for (int i = 0; i < rawProgram.getHeight(); ++i)
			{
				if ( rawProgram.getColor(posx, i) == cbcalc.blockColor && cbcalc.isChecked[i][posx] )
					left.setY(i);
				
				int inv = (rawProgram.getHeight()-1)-i;
				if ( rawProgram.getColor(posx, inv) == cbcalc.blockColor && cbcalc.isChecked[inv][posx] )
					right.setY(inv);
			}
		}
		
		return new Pair<Point<Integer>>(left, right);
	}
}
