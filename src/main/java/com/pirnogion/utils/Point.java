package com.pirnogion.utils;

import javax.crypto.spec.PSource;

public final class Point <T extends Number>
{
	private T positionX, positionY;
	
	public Point(T positionX, T positionY)
		{ this.positionX = positionX; this.positionY = positionY; }
	
	public void setXY(T positionX, T positionY) { this.positionX = positionX; this.positionY = positionY; }
	public void setX(T positionX) { this.positionX = positionX; }
	public void setY(T positionY) { this.positionY = positionY; }
	
	public T getX() { return positionX; }
	public T getY() { return positionY; }
}
