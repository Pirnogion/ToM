package com.pirnogion.blocks.specialRendereres;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import com.pirnogion.tileEntity.TileEntityBarrel;
import com.pirnogion.tom.ToMMod;
import com.pirnogion.utils.MathHelper;
import com.pirnogion.utils.RenderHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class SpecialRendererBarrel extends TileEntitySpecialRenderer<TileEntityBarrel>
{
	private final ResourceLocation waterStillResourceLoaction = new ResourceLocation( "minecraft", "textures/blocks/water_still.png" );
	private final double barrelHeight = 0.9d;
	private final int textureFrames = 32; // 512px : 16px = 32
	private final int delayTick = 10;
	
	private Minecraft mc = Minecraft.getMinecraft();
	private int tickCounter = 0;
	private int curTexBits = 0;
	
	/* For optimization */
	private int savedWaterAmount = 0;
	private double savedWaterLevel = 0;
	
	@Override
	public void renderTileEntityAt(TileEntityBarrel te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		/* Get tile entity barrel */
		if ( !(te instanceof TileEntityBarrel)) return;
		TileEntityBarrel tileBarrel = te;
		
		/* Exit if no water into the barrel or the barrel is closed */
		if ( tileBarrel.getWaterAmount() == 0 || tileBarrel.isClosed() ) return;
		
		/* Liquid animation */
		if (tickCounter > delayTick)
		{
			tickCounter = 0;
			
			if (curTexBits-textureFrames == 0) curTexBits = 0;
			else curTexBits += 1;
		}
		else tickCounter += 1;
		
		/* Draw Liquid */
		double waterLevel = calcWaterLevel(tileBarrel.getWaterAmount(), tileBarrel.maxAmountOfWater);
		
		RenderHelper.fixTextureBrightness(te);
		RenderHelper.drawTexturedRect(waterStillResourceLoaction, x, y+waterLevel, z, 0, 16*curTexBits, 16, 16, 16, 512);
		
		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
	}
	
	private double calcWaterLevel(final int waterAmount, final int maxWaterAmount)
	{
		double waterLevel;
		
		if (waterAmount == savedWaterAmount)
		{
			waterLevel = savedWaterLevel;
		}
		else
		{
			waterLevel = MathHelper.recalcPercentsToValue(MathHelper.recalcValueToPercents( waterAmount, maxWaterAmount ), barrelHeight);
			
			// Save new values
			savedWaterAmount = waterAmount;
			savedWaterLevel = waterLevel;
		}
		
		return waterLevel;
	}
}
