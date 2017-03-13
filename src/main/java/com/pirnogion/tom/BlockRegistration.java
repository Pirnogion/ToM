package com.pirnogion.tom;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.pirnogion.blocks.BlockBarrel;
import com.pirnogion.blocks.BlockCrystaliser;
import com.pirnogion.blocks.BlockMagicFurnace;
import com.pirnogion.blocks.BlockPietWritingTable;
import com.pirnogion.blocks.specialRendereres.SpecialRendererBarrel;
import com.pirnogion.blocks.specialRendereres.SpecialRendererMagicalFurnace;
import com.pirnogion.tileEntity.TileEntityBarrel;
import com.pirnogion.tileEntity.TileEntityMagicFurnace;

public class BlockRegistration
{
	public static BlockBarrel barrel;
	public static BlockCrystaliser crystaliser;
	public static BlockMagicFurnace magicFurnace;
	public static BlockPietWritingTable writingTable;
	
	public static void constructBlocks()
	{
		barrel = new BlockBarrel("barrel", "barrel");
		crystaliser = new BlockCrystaliser("crystaliser", "crystaliser");
		writingTable = new BlockPietWritingTable("piet_writing_table", "piet_writing_table");
		magicFurnace = new BlockMagicFurnace("magic_furnace", "magic_furnace");
	}
	
	/* Registry */
	public static void registerBlocks()
	{
		registerBlock(barrel);
		registerBlock(crystaliser);
		registerBlock(writingTable);
		registerBlock(magicFurnace);
	}
	
	public static void registerRenderes()
	{
		registerRenderBlock(barrel);
		registerRenderBlock(crystaliser);
		registerRenderBlock(writingTable);
		registerRenderBlock(magicFurnace);
	}
	
	/* Adapter */
	public static void registerBlock(Block block)
	{
		block.setCreativeTab(ToMMod.tomStuffTab);
		
		GameRegistry.register(block);
		GameRegistry.register( new ItemBlock(block).setRegistryName(block.getRegistryName()) );
	}
	
	public static void registerRenderBlock(Block block)
	{
		ResourceLocation _rl = new ResourceLocation( ToMMod.MODID, 
				block.getUnlocalizedName().substring(5));
		ModelResourceLocation _mrl = new ModelResourceLocation(_rl, "inventory");
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, _mrl);
		
		//Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, _mrl);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new SpecialRendererBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagicFurnace.class, new SpecialRendererMagicalFurnace());
	}
}
