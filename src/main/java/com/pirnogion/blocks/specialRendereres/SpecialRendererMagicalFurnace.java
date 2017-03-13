package com.pirnogion.blocks.specialRendereres;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import com.pirnogion.tileEntity.TileEntityBarrel;
import com.pirnogion.tileEntity.TileEntityMagicFurnace;
import com.pirnogion.utils.MathHelper;
import com.pirnogion.utils.RenderHelper;

public final class SpecialRendererMagicalFurnace extends TileEntitySpecialRenderer<TileEntityMagicFurnace>
{
	private Minecraft mc = Minecraft.getMinecraft();
	
	private final ResourceLocation rune001 = new ResourceLocation( "tom", "textures/blocks/rune001.png" );
	private final ResourceLocation rune002 = new ResourceLocation( "tom", "textures/blocks/rune002.png" );
	private final ResourceLocation rune003 = new ResourceLocation( "tom", "textures/blocks/rune003.png" );
	
	private final int delayTick = 10;
	private int tickCounter = 0;
	
	private final double flyingDeviationMultiplier = 0.007D;
	private final double flyingFluctuationSpeed = 0.03D;
	private final double minFlyingHeight = 0.25D;
	private double flyingLevel = 0.0D;
	
	private final double radiusOfRotation = 0.2D; 
	private final double rotationSpeed = 0.06D;
	private final double overclockedRSModifier = 0.45D;
	private double rotationAngle = 0.0D;
	
	@Override
	public void renderTileEntityAt(TileEntityMagicFurnace te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (flyingLevel > 360.0d) flyingLevel = 0.0d;
		else flyingLevel += flyingFluctuationSpeed;
		
		double rotationModifier = (te.getStoredEnergy() > te.getCapacity()) ? overclockedRSModifier : 0D;
		
		if (rotationAngle > 360.0d) rotationAngle = 0.0d;
		else rotationAngle += rotationSpeed + rotationModifier;
		
		double doubleX = mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * partialTicks+0.005;
        double doubleY = mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * partialTicks+0.005;
        double doubleZ = mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * partialTicks+0.005;
		
        double centerX = -doubleX+te.getPos().getX()+0.5;
        double centerY = -doubleY+te.getPos().getY();
        double centerZ = -doubleZ+te.getPos().getZ()+0.5;
        
        double flyingLevelRad = Math.toRadians( this.flyingLevel );
        double flyingDeviation = minFlyingHeight+Math.sin(flyingLevelRad)*flyingDeviationMultiplier;
        
        // Draw runes //
		GL11.glPushMatrix();
		GL11.glTranslated(centerX, centerY+flyingDeviation, centerZ);
		GL11.glRotated(rotationAngle, 0.0d, 1.0d, 0.0d);
		
		/* Draw rune001 */
		GL11.glPushMatrix();
		GL11.glTranslated(0, Math.sin(flyingLevelRad)*flyingDeviationMultiplier, 0);
		GL11.glScaled(-0.4, 0, -0.4);
		RenderHelper.drawTexturedRect(rune001, radiusOfRotation, 0.0d, radiusOfRotation, 0, 0, 16, 16, 16, 16);
		GL11.glPopMatrix();
		
		/* Draw rune002 */
		GL11.glPushMatrix();
		GL11.glTranslated(0, Math.sin(flyingLevelRad+Math.toRadians( 120 ))*flyingDeviationMultiplier, 0);
		GL11.glRotated(120, 0, 1, 0);
		GL11.glScaled(-0.4, 0, -0.4);
		RenderHelper.drawTexturedRect(rune002, radiusOfRotation, 0.0d, radiusOfRotation, 0, 0, 16, 16, 16, 16);
		GL11.glPopMatrix();
		
		/* Draw rune003 */
		GL11.glPushMatrix();
		GL11.glTranslated(0, Math.sin(flyingLevelRad+Math.toRadians( 240 ))*flyingDeviationMultiplier, 0);
		GL11.glRotated(240, 0, 1, 0);
		GL11.glScaled(-0.4, 0, -0.4);
		RenderHelper.drawTexturedRect(rune003, radiusOfRotation, 0.0d, radiusOfRotation, 0, 0, 16, 16, 16, 16);
		GL11.glPopMatrix();
		
		GL11.glTranslated(-centerX, -(centerY+flyingDeviation), -centerZ);
		GL11.glPopMatrix();
		
		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
	}
}
