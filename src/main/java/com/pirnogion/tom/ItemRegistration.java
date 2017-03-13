package com.pirnogion.tom;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.pirnogion.items.ItemDarkCrystal;
import com.pirnogion.items.ItemShardOfCrystal;
import com.pirnogion.items.ItemWoodenCap;

public class ItemRegistration
{
	public static ItemWoodenCap woodenCap;
	public static ItemDarkCrystal darkCrystal;
	public static ItemShardOfCrystal shardOfCrystal;
	
	public static void constructItems()
	{
		woodenCap = new ItemWoodenCap("wooden_cap", "wooden_cap");
		darkCrystal = new ItemDarkCrystal("dark_crystal", "dark_crystal");
		shardOfCrystal = new ItemShardOfCrystal("shard_of_crystal", "shard_of_crystal");
	}
	
	/* Registry */
	public static void registerItems()
	{
		registerItem(woodenCap);
		registerItem(darkCrystal);
		registerItem(shardOfCrystal);
	}
	
	public static void registerRenderes()
	{
		registerRenderItem(woodenCap);
		registerRenderItem(darkCrystal);
		registerRenderItem(shardOfCrystal);
	}
	
	/* Adapter */
	public static void registerItem(Item item)
	{
		item.setCreativeTab(ToMMod.tomStuffTab);
		
		GameRegistry.register(item);
	}
	
	public static void registerRenderItem(Item item)
	{
		ResourceLocation _rl = new ResourceLocation( ToMMod.MODID, 
				item.getUnlocalizedName().substring(5));
		ModelResourceLocation _mrl = new ModelResourceLocation(_rl, "inventory");
		
		ModelLoader.setCustomModelResourceLocation(item, 0, _mrl);
	}
}
