package com.pirnogion.inWorldRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.Color;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 1 on 24.01.2016.
 */
public class InventoryHolo
{
    private final Minecraft mc = Minecraft.getMinecraft();
    
    private IInventory inventory;

    private Timer lifeTimer = new Timer();
    private TimerTask task;

    private Timer fadeTimer = new Timer();
    private TimerTask fade;

    private boolean isDead = false;
    private boolean isHover = false;

    private double distToPlayer = 0;

    private double startDrawingPosX;
    private double startDrawingPosY;
    private double startDrawingPosZ;

    public int lifeTime = 500;

    /* ������� */
    public double bgSlotSideLen = 0.5d;
    public double slotMargin = 0.05d;
    public int slotInOneRowDefault = 9;

    //�����������
    public double triangleWidth = 0.20d;
    public double triangleHeight = 0.15d;

    /* ������� ��� */
    //����� ������������
    public final float globalAlpha = 0.25f;
    public float currentGlobalAlpha = 0.25f;

    //������� ��������
    public Color bgColor = new Color(25, 25, 25, 51);
    public Color lineColor = new Color(178, 178, 178, 127);
    public float lineThickness = 2.0f;

    //�������� �����
    public Color slotBgColor = new Color(25, 25, 25, 51);
    public Color slotLineColor = new Color(25, 25, 25, 51);
    public float slotLineThickness = 5.0f;

    public InventoryHolo(IInventory inventory, double startDrawingPosX, double startDrawingPosY, double startDrawingPosZ)
    {
        this.inventory = inventory;

        this.startDrawingPosX = startDrawingPosX;
        this.startDrawingPosY = startDrawingPosY+1;
        this.startDrawingPosZ = startDrawingPosZ;

        distToPlayer = calculateDistanceToPlayer();
    }

    public boolean getHoverState()
    {
        return isHover;
    }

    public void setHoverState(boolean hoverState)
    {
        isHover = hoverState;
    }

    public boolean getDeadState()
    {
        return isDead;
    }

    public double getDistToPlayer()
    {
        return distToPlayer;
    }

    public void renderHolo(float partialTicks)
    {
        if ( !isHover )
        {
            lifeTimer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    isDead = true;
                }
            };
            lifeTimer.schedule(task, lifeTime);

            fadeTimer = new Timer();
            fade = new TimerTask() {
                @Override
                public void run() {
                    currentGlobalAlpha -= globalAlpha / (lifeTime/10);
                }
            };
            fadeTimer.schedule(fade, lifeTime/(lifeTime/10), (lifeTime/10));
        }
        else
        {
            lifeTimer.cancel();
            fadeTimer.cancel();

            currentGlobalAlpha = globalAlpha;
        }

        //������ ���������� �� ������
        distToPlayer = calculateDistanceToPlayer();

        //����� ������� ��������� �� "������" � "����"
        double doubleX = mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * partialTicks+0.005;
        double doubleY = mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * partialTicks+0.005;
        double doubleZ = mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * partialTicks+0.005;

        double bgWidth, bgHeight;
        int slotInOneRow, slotCount, endRowSlotCount, rows;

        //�������� ���������� �� ���������
        slotInOneRow = (inventory.getSizeInventory() < slotInOneRowDefault) ? inventory.getSizeInventory() : slotInOneRowDefault;
        bgWidth = slotMargin+slotMargin*slotInOneRow+bgSlotSideLen*slotInOneRow;

        slotCount = inventory.getSizeInventory();
        endRowSlotCount = slotCount % slotInOneRow;
        rows = (endRowSlotCount > 0) ? slotCount / slotInOneRow + 1 : slotCount / slotInOneRow;
        bgHeight = slotMargin+slotMargin*rows+bgSlotSideLen*rows;

        //�������� ���������� � �������� ����������
        String drawedText = inventory.getName();
        double scaledTextWidth = mc.fontRendererObj.getStringWidth(drawedText);

        if ( mc.fontRendererObj.getStringWidth(drawedText) > bgWidth*100 )
        {
            drawedText = drawedText.substring(0, 15) + "�";
            scaledTextWidth = mc.fontRendererObj.getStringWidth(drawedText);
        }

        //��������� �����
        GL11.glPushMatrix();

        GL11.glTranslated(-doubleX+startDrawingPosX+0.5d, -doubleY+startDrawingPosY+bgHeight/2, -doubleZ+startDrawingPosZ+0.5d);
        GL11.glRotated(-mc.player.rotationYawHead+180.0d, 0.0d, 1.0d, 0.0d);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL14.glBlendColor(0, 0, 0, currentGlobalAlpha);

        GL11.glLineWidth(lineThickness);

        //��������� ��������
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4ub(bgColor.getRedByte(), bgColor.getGreenByte(), bgColor.getBlueByte(), bgColor.getAlphaByte());
        GL11.glVertex3d(-bgWidth/2, bgHeight/2, 0);

        //����� ��� ���������
        GL11.glVertex3d(-bgWidth/2+0.05d, bgHeight/2+0.1d, 0);
        GL11.glVertex3d(-bgWidth/2+scaledTextWidth/100+0.15d, bgHeight/2+0.1d, 0);
        GL11.glVertex3d(-bgWidth/2+scaledTextWidth/100+0.15d+0.05d, bgHeight/2, 0);

        GL11.glVertex3d(bgWidth/2, bgHeight/2, 0);
        GL11.glVertex3d(bgWidth/2, -bgHeight/2, 0);
        GL11.glVertex3d(-bgWidth/2, -bgHeight/2, 0);
        GL11.glEnd();

        //����������� ��������
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3d(triangleWidth/2, -bgHeight/2, 0.0d);
        GL11.glVertex3d(0.0d, -bgHeight/2-triangleHeight, 0.0d);
        GL11.glVertex3d(-triangleWidth/2, -bgHeight/2, 0.0d);
        GL11.glEnd();

        //������� ��������
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor4ub(lineColor.getRedByte(), lineColor.getGreenByte(), lineColor.getBlueByte(), lineColor.getAlphaByte());
        GL11.glVertex3d(-bgWidth/2, bgHeight/2, 0);

        //����� ��� ���������
        GL11.glVertex3d(-bgWidth/2+0.05d, bgHeight/2+0.1d, 0);
        GL11.glVertex3d(-bgWidth/2+scaledTextWidth/100+0.15d, bgHeight/2+0.1d, 0);
        GL11.glVertex3d(-bgWidth/2+scaledTextWidth/100+0.15d+0.05d, bgHeight/2, 0);

        GL11.glVertex3d(bgWidth/2, bgHeight/2, 0);
        GL11.glVertex3d(bgWidth/2, -bgHeight/2, 0);

        //�����������
        GL11.glVertex3d(triangleWidth/2, -bgHeight/2, 0);
        GL11.glVertex3d(0, -bgHeight/2-triangleHeight, 0);
        GL11.glVertex3d(-triangleWidth/2, -bgHeight/2, 0);
        GL11.glVertex3d(-triangleWidth/2, -bgHeight/2, 0);

        GL11.glVertex3d(-bgWidth/2, -bgHeight/2, 0);
        GL11.glEnd();

        //���������� �����
        for (int j = 0; j < rows; ++j)
        {
            if ( endRowSlotCount > 0 && j == rows-1 )
            {
                for (int k = 0; k < endRowSlotCount; ++k)
                {
                    drawSlot(
                            bgWidth/2-bgSlotSideLen/2-slotMargin-(slotMargin*k)-(bgSlotSideLen*k),
                            bgHeight/2-bgSlotSideLen/2-slotMargin-(slotMargin*j)-(bgSlotSideLen*j),
                            0.05d,
                            inventory.getStackInSlot(j * slotInOneRow + (endRowSlotCount-k-1))
                    );
                }
            }
            else
            {
                for (int i = 0; i < slotInOneRow; ++i)
                {
                    drawSlot(
                            bgWidth/2-bgSlotSideLen/2-slotMargin-(slotMargin*i)-(bgSlotSideLen*i),
                            bgHeight/2-bgSlotSideLen/2-slotMargin-(slotMargin*j)-(bgSlotSideLen*j),
                            0.05d,
                            inventory.getStackInSlot(j * slotInOneRow + (slotInOneRow-i-1))
                    );
                }
            }

        }

        //�������� ��������� ����� ����������!
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(0.01d, -0.01d, 0.01d);
        GL11.glTranslated(-bgWidth/2*100 + 7, -bgHeight/2*100 - 7, 0.05d*100);
        mc.fontRendererObj.drawString(drawedText, 0, 0, 0xFFFFFF);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glPopMatrix();
    }

    private void drawSlot(double offsetX, double offsetY, double offsetZ, ItemStack itemStack)
    {
        //��������� �������� �����
        GL11.glPushMatrix();
        GL11.glTranslated(offsetX, offsetY, offsetZ);

        GL11.glLineWidth(slotLineThickness);

        GL11.glBegin(GL11.GL_POLYGON);

        GL11.glColor4ub(slotBgColor.getRedByte(), slotBgColor.getGreenByte(), slotBgColor.getBlueByte(), slotBgColor.getAlphaByte());
        GL11.glVertex3d(-0.25, -0.25, 0);
        GL11.glVertex3d(0.25, -0.25, 0);
        GL11.glVertex3d(0.25, 0.25, 0);
        GL11.glVertex3d(-0.25, 0.25, 0);

        GL11.glEnd();

        //������� �������� �����
        GL11.glBegin(GL11.GL_LINE_LOOP);

        GL11.glColor4ub(slotLineColor.getRedByte(), slotLineColor.getGreenByte(), slotLineColor.getBlueByte(), slotLineColor.getAlphaByte());
        GL11.glVertex3d(-0.25, -0.25, 0);
        GL11.glVertex3d(0.25, -0.25, 0);
        GL11.glVertex3d(0.25, 0.25, 0);
        GL11.glVertex3d(-0.25, 0.25, 0);

        GL11.glEnd();

        //��������� ���������
        if ( itemStack != null )
        {
	    	RenderItem renderItem = mc.getRenderItem();
	    	EntityItem entityItem = new EntityItem(mc.world, 0D, 0D, 0D, itemStack);
	    	
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			GL11.glPushMatrix();
	    	
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
	    	GlStateManager.scale(0.5F, 0.5F, 0.5F);
	
	        if (!renderItem.shouldRenderItemIn3D(entityItem.getEntityItem()) || itemStack.getItem() instanceof ItemSkull)
	        {
	            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
	        }
	
	        GlStateManager.pushAttrib();
	        RenderHelper.enableStandardItemLighting();
	        renderItem.renderItem(entityItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
	        RenderHelper.disableStandardItemLighting();
	        GlStateManager.popAttrib();
	        
	        GL11.glPopMatrix();

	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);

	        GL11.glPushMatrix();

	        GL11.glScaled(0.02d, -0.02d, 0.02d);
	        GL11.glTranslated(-5, 2.5d, 10);
	        mc.fontRendererObj.drawString(Integer.toString( itemStack.getCount() ), 0, 0, 0xFFFFFF);

	        GL11.glPopMatrix();

	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA) ;
        }

        GL11.glPopMatrix();
    }

    private double calculateDistanceToPlayer()
    {
        return Math.pow( (mc.player.posX-startDrawingPosX), 2 ) +
                Math.pow( (mc.player.posY-startDrawingPosY), 2 ) +
                Math.pow( (mc.player.posZ-startDrawingPosZ), 2 );

    }
}
