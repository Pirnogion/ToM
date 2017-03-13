package com.pirnogion.GUI;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import com.pirnogion.chat.ChatColoredString;
import com.pirnogion.chat.ChatString;
import com.pirnogion.piet.core.PietDataManager;
import com.pirnogion.piet.core.PietProgram;
import com.pirnogion.piet.core.PietRawProgram;
import com.pirnogion.tom.ToMMod;

public class PietGUI extends GuiScreen
{
	ChatString s; 
	//PietRawProgram prp = PietDataManager.readData(ToMMod.savePath + "/pietPrograms/Piet1.piet");
	//PietProgram piet = new PietProgram(prp);
	
	long delay = 0;
	
    private void drawSquare(float x, float y, float width, float height, Color color)
    {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);

        GL11.glTranslatef(x, y, 0.0F);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex3f(0.0F, 0.0F, 0.0F);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex3f(0.0F, height, 0.0F);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex3f(width, height, 0.0F);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex3f(width, 0.0F, 0.0F);
        GL11.glEnd();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void drawScreen(int x, int y, float ticks)
    {
    	s = new ChatString("#ffffffffWhiteText#ff0000ffRedText#ff0000ffasd");
    	
    	System.out.println( s.getSize() );
    	
    	for (int i = 0; i < s.getSize(); ++i)
    	{
    		ChatColoredString str = s.getString(i);
    		this.drawString(mc.fontRendererObj, str.getString(), 10, 10*i, str.getColor().inthexcolor);
    	}
    	
    	/*delay += 1;
    	
    	mc.renderEngine.bindTexture( new ResourceLocation( "minecraft", "textures/blocks/wool_colored_white.png" ) );
    	
    	drawSquare(10, 10, 300, 300, new Color(255, 255, 255));
    	
    	if (delay % 100 == 0) { piet.nextStep(); }
    	
    	for (int j = 0; j < prp.getHeight(); ++j)
    	{
    		for (int i = 0; i < prp.getWidth(); ++i)
        	{
    			int color = prp.getColor(i, j);
    			
    			int r = (color & 0xFF0000) >> 16;
	    		int g = (color & 0x00FF00) >>  8;
	    		int b = (color & 0x0000FF) >>  0;
    			
    			drawSquare(15+(20*i), 15+(20*j), 20, 20, new Color( r, g, b ));
    			
    			if ( i == piet.pointer.getX() && j == piet.pointer.getY() )
    				drawSquare(15+(20*i+2.5f), 15+(20*j+2.5f), 15, 15, new Color( 255, 255, 255 ));
        	}
    	}*/
    }
}

