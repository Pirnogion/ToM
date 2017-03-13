package com.pirnogion.piet.core;

public final class PietDirections
{
	public static final byte RIGHT = 1;
	public static final byte DOWN  = 2;
	public static final byte LEFT  = 3;
	public static final byte UP    = 4;
	
	public static final byte CC_LEFT  = -4;
	public static final byte CC_RIGHT = 4;
	
	public static final byte UP_LEFT     = UP    + CC_LEFT;
	public static final byte UP_RIGHT    = UP    + CC_RIGHT;
	public static final byte DOWN_LEFT   = DOWN  + CC_LEFT;
	public static final byte DOWN_RIGHT  = DOWN  + CC_RIGHT;
	public static final byte LEFT_LEFT   = LEFT  + CC_LEFT;
	public static final byte LEFT_RIGHT  = LEFT  + CC_RIGHT;
	public static final byte RIGHT_LEFT  = RIGHT + CC_LEFT;
	public static final byte RIGHT_RIGHT = RIGHT + CC_RIGHT;
	
	public static final boolean HORIZONTAL = false;
	public static final boolean VERTICAL   = true;
}
