package com.pirnogion.items;

import com.pirnogion.tom.ToMMod;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemDarkCrystal extends Item
{
	private int frequency;
	private int amplifier;
	private int degradationSpeed;
	
	public ItemDarkCrystal(String unlocalizedName, String registryName)
	{
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName( new ResourceLocation(ToMMod.MODID, registryName) );
		this.setMaxStackSize(16);
	}
}
