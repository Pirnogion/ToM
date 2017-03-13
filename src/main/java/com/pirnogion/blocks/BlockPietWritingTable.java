package com.pirnogion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class BlockPietWritingTable extends Block
{
	public BlockPietWritingTable(String unlocalizedName, String registryName)
	{
		super(Material.WOOD);
		
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		
		this.setHardness(1.0f);
		this.setResistance(1.0f);
	}
}
