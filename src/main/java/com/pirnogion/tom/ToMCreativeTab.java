package com.pirnogion.tom;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ToMCreativeTab extends CreativeTabs
{
	public ToMCreativeTab() {
		super("tomstuff");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack( Item.getItemFromBlock(BlockRegistration.barrel) );
	}

}
