package com.pirnogion.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.pirnogion.tileEntity.TileEntityBarrel;

public final class RenderHelper
{
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static <T extends TileEntity> void fixTextureBrightness(T tile)
	{
		int li = tile.getWorld().getCombinedLight(tile.getPos(), 15728640);
		int i1 = li % 65536;
		int j1 = li / 65536;
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i1, (float) j1);
	}
	
	public static void drawTexturedRect(ResourceLocation texture_resource, double pos_x, double pos_y, double pos_z, int start_u, int start_v, int end_u, int end_v, int texture_size_x, int texture_size_y)
	{
		GlStateManager.pushMatrix();
		
		/* OGL settings */
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		/* Model settings */
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.translate((float)pos_x, (float)pos_y, (float)pos_z);
		
		/* Draw model */
		Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        
        mc.renderEngine.bindTexture( texture_resource );
        
        // UV coords
        double pixelU = 1.0d / texture_size_x;
        double pixelV = 1.0d / texture_size_y;
        
        double top_left_corner_u = (0.0d + 0) * pixelU;
        double top_left_corner_v = (start_v + end_v) * pixelV;
        
        double top_right_corner_u = (start_u + end_u) * pixelU;
        double top_right_corner_v = (start_v + end_v) * pixelV;
        
        double bottom_right_corner_u = (start_u + end_u) * pixelU;
        double bottom_right_corner_v = (start_v + 0) * pixelV;
        
        double bottom_left_corner_u = (start_u + 0) * pixelU;
        double bottom_left_corner_v = (start_v + 0) * pixelV;
        
        // Set vertex
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0.0D, 0.0D, 0.0D).tex(top_left_corner_u,     top_left_corner_v    ).endVertex();
        vertexbuffer.pos(1.0D, 0.0D, 0.0D).tex(top_right_corner_u,    top_right_corner_v   ).endVertex();
        vertexbuffer.pos(1.0D, 0.0D, 1.0D).tex(bottom_right_corner_u, bottom_right_corner_v).endVertex();
        vertexbuffer.pos(0.0D, 0.0D, 1.0D).tex(bottom_left_corner_u,  bottom_left_corner_v ).endVertex();
        tessellator.draw();
        
        GlStateManager.translate(-(float)pos_x, -(float)pos_y, -(float)pos_z);
        
        /* OGL settings */
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
}
