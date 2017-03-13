package com.pirnogion.items;

import com.pirnogion.tom.ToMMod;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemShardOfCrystal extends Item
{
	private int color;
	
	public ItemShardOfCrystal(String unlocalizedName, String registryName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName( new ResourceLocation(ToMMod.MODID, registryName) );
		this.setMaxStackSize(64);
	}
}
