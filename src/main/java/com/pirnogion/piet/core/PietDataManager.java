package com.pirnogion.piet.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class PietDataManager
{
	public static PietRawProgram readData(String filePath)
	{
		PietRawProgram rawProgram = null;
		
		try
		{
			/* Open file */
			File file = new File(filePath);
			FileInputStream inputFileStream = new FileInputStream( file );
			BufferedInputStream inputBuffer = new BufferedInputStream( inputFileStream );
			DataInputStream inputStream = new DataInputStream( inputBuffer );
			
			/* Read data */
			int width = inputStream.readUnsignedByte();
			int height = inputStream.readUnsignedByte();
			
			int[][] programData = new int[height][width];
			
			for (int y = 0; y < height; ++y)
			{
				for (int x = 0; x < width; ++x)
				{
					programData[y][x] = PietColorset.getColorFrom( inputStream.readUnsignedByte() );
				}
			}
			
			/* Close the file and return readed data */
			inputStream.close();
			
			rawProgram = new PietRawProgram(programData, width, height);
		}
		catch (NumberFormatException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		
		return rawProgram;
	}
	
	public static boolean saveData(String filePath, PietRawProgram rawProgram)
	{
		try
		{
			/* Open file */
			File file = new File(filePath);
			FileOutputStream outputFileStream = new FileOutputStream( file );
			BufferedOutputStream outputBuffer = new BufferedOutputStream( outputFileStream );
			DataOutputStream outputStream = new DataOutputStream( outputBuffer );
			
			int width = rawProgram.getWidth();
			int height = rawProgram.getHeight();
			
			/* Write data */
			outputStream.writeByte( width );
			outputStream.writeByte( height );
			
			for (int y = 0; y < height; ++y)
			{
				for (int x = 0; x < width; ++x)
				{
					int colorIndex = PietColorset.getIndexFrom( rawProgram.getColor(x, y) );
					
					outputStream.writeByte( colorIndex );
				}
			}
			
			/* Close the file */
			outputStream.close();
			
			return true;
		}
		catch (NumberFormatException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		
		return false;
	}
}
