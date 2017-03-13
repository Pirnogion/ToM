package com.pirnogion.piet.core;

import java.util.LinkedList;

import com.pirnogion.piet.commands.IPietCommand;
import com.pirnogion.piet.commands.PietCommandManager;
import com.pirnogion.utils.Point;

public final class PietProgram
{
	private static PietCommandManager commandManager;
	
	private static final int nextStepMaxTries = 8;
	
	private final PietRawProgram rawProgram;
	
	// TODO: replace 'public' modifier to 'private' after debugging
	public PietStack stack = new PietStack(255);
	
	private int nextStepTries = 0;
	public Point<Integer> pointer = new Point<Integer>(0, 0);
	
	private int pointerDirection = PietDirections.RIGHT;
	private int codelChooserDirection = PietDirections.CC_LEFT;
	
	private boolean running = true;
	
	public PietProgram(PietRawProgram rawProgram)
		{ this.rawProgram = rawProgram; }
	
	public boolean isRunning() { return running; }
	
	public int getOrientation()
	{
		return pointerDirection + codelChooserDirection;
	}
	
	public void rotatePointerClockwise()
	{
		if ( pointerDirection == 1 ) pointerDirection = 2;
		if ( pointerDirection == 2 ) pointerDirection = 3;
		if ( pointerDirection == 3 ) pointerDirection = 4;
		if ( pointerDirection == 4 ) pointerDirection = 1;
	}
	
	public void rotatePointerAnticlockwise()
	{
		if ( pointerDirection == 1 ) pointerDirection = 4;
		if ( pointerDirection == 2 ) pointerDirection = 3;
		if ( pointerDirection == 3 ) pointerDirection = 2;
		if ( pointerDirection == 4 ) pointerDirection = 1;
	}
	
	public void mirrorCodelChooser()
	{
		codelChooserDirection = (codelChooserDirection == PietDirections.CC_LEFT) ? PietDirections.CC_RIGHT : PietDirections.CC_LEFT;
	}
	
	public void nextStep()
	{		
		if ( !running ) return;			
		
		PietColoredBlock curBlock = new PietColoredBlock(rawProgram, pointer);
		PietColoredBlock nextBlock = null;
		
		Point<Integer> curBlockCodel = null;
		Point<Integer> nextBlockCodel = null;
		
		// If my code is pie of shit then execute this code fragment!
		if (curBlock.blockColor == PietColorset.getBlackColor() || curBlock.blockColor == PietColorset.getEdge())
		{
			running = false;
			return;
		}

		switch ( getOrientation() )
		{
			// LEFT and RIGHT //
			case PietDirections.RIGHT_LEFT:
				curBlockCodel = curBlock.rightTopCodel;
				nextBlockCodel = new Point<Integer>(curBlock.rightTopCodel.getX()+1, curBlock.rightTopCodel.getY());
				
				break;
				
			case PietDirections.RIGHT_RIGHT:
				curBlockCodel = curBlock.rightBottomCodel;
				nextBlockCodel = new Point<Integer>(curBlock.rightBottomCodel.getX()+1, curBlock.rightBottomCodel.getY());
				break;
				
			case PietDirections.LEFT_LEFT:
				curBlockCodel = curBlock.leftBottomCodel;
				nextBlockCodel = new Point<Integer>(curBlock.leftBottomCodel.getX()-1, curBlock.leftBottomCodel.getY());
				break;
				
			case PietDirections.LEFT_RIGHT:
				curBlockCodel = curBlock.leftTopCodel;
				nextBlockCodel = new Point<Integer>(curBlock.leftTopCodel.getX()-1, curBlock.leftTopCodel.getY());
				break;
				
			// UP and DOWN //
			case PietDirections.UP_LEFT:
				curBlockCodel = curBlock.topLeftCodel;
				nextBlockCodel = new Point<Integer>(curBlock.topLeftCodel.getX(), curBlock.topLeftCodel.getY()-1);
				break;
				
			case PietDirections.UP_RIGHT:
				curBlockCodel = curBlock.topRightCodel;
				nextBlockCodel = new Point<Integer>(curBlock.topRightCodel.getX(), curBlock.topRightCodel.getY()-1);
				break;
				
			case PietDirections.DOWN_LEFT:
				curBlockCodel = curBlock.bottomRightCodel;
				nextBlockCodel = new Point<Integer>(curBlock.bottomRightCodel.getX(), curBlock.bottomRightCodel.getY()+1);
				break;
				
			case PietDirections.DOWN_RIGHT:
				curBlockCodel = curBlock.bottomLeftCodel;
				nextBlockCodel = new Point<Integer>(curBlock.bottomLeftCodel.getX(), curBlock.bottomLeftCodel.getY()+1);
				break;
		}
		
		nextBlock = new PietColoredBlock(rawProgram, nextBlockCodel);
		
		// If color of next block is black or next block is edge then execute this block  
		if ( nextBlock.blockColor == PietColorset.getBlackColor() || nextBlock.blockColor == PietColorset.getEdge() )
		{
			if (nextStepTries == nextStepMaxTries) { running = false; return; }
            
            if (nextStepTries % 2 == 0) mirrorCodelChooser();
            else rotatePointerClockwise();
            
            nextStepTries += 1;
            
            System.out.println( nextStepTries + " of " + nextStepMaxTries + " tries." );
            
            //nextStep();
		}
		else
        {
            pointer.setXY(nextBlockCodel.getX(), nextBlockCodel.getY());
            
            IPietCommand cmd = commandManager.getCommand
            (
                    PietColorset.getCommandSignature(curBlock.blockColor, nextBlock.blockColor),
                    this,
                    stack,
                    curBlock.blockSize
            );
            
            cmd.execute();
        }
	}
}
