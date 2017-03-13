package com.pirnogion.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryHelper
{
	public static void takeItemCount(ItemStack itemStack, final int count)
	{
		itemStack.setCount( itemStack.getCount() - count );
	}
	
	public static void addItemCount(final EntityPlayer playerIn, final Item item, final int count)
	{
		ItemStack newItemStack = new ItemStack(item, count);
		
		if (!playerIn.inventory.addItemStackToInventory( newItemStack ))
		{
			spawnEntityItem(playerIn.getEntityWorld(), playerIn.posX, playerIn.posY, playerIn.posZ, newItemStack);
		}
	}
	
	public static void spawnEntityItem(World world, double x, double y, double z, ItemStack itemStack)
	{
		EntityItem item = new EntityItem(world, x, y, z, itemStack);
		
		world.spawnEntity(item);
	}
}
