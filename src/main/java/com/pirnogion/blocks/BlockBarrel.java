package com.pirnogion.blocks;

import java.util.Random;

import com.pirnogion.tileEntity.TileEntityBarrel;
import com.pirnogion.tom.ItemRegistration;
import com.pirnogion.utils.InventoryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class BlockBarrel extends Block implements ITileEntityProvider
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool CLOSED = PropertyBool.create("closed");

	public BlockBarrel(String unlocalizedName, String registryName)
	{
		super(Material.WOOD);

		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		
		this.setHardness(1.0f);
		this.setResistance(1.0f);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(CLOSED, Boolean.valueOf(false)));
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{		
		if ( worldIn.isRemote ) return true;
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityBarrel)
		{
			TileEntityBarrel tileBarrel = (TileEntityBarrel)tileEntity;
			ItemStack itemStackInHand = playerIn.inventory.getCurrentItem();
			
			if ( !tileBarrel.isClosed() && itemStackInHand != null )
			{
				if ( itemStackInHand.getItem() == ItemRegistration.woodenCap )
				{
					worldIn.setBlockState( tileBarrel.getPos(), state.withProperty(CLOSED, Boolean.valueOf(true)) );
					InventoryHelper.takeItemCount(itemStackInHand, 1);
				}
				else if ( itemStackInHand.getItem() == Items.WATER_BUCKET )
				{
					if ( tileBarrel.getWaterAmount() < tileBarrel.maxAmountOfWater )
					{
						tileBarrel.addWater();
						InventoryHelper.takeItemCount(itemStackInHand, 1);
						InventoryHelper.addItemCount(playerIn, Items.BUCKET, 1);
					}
				}
				else if ( itemStackInHand.getItem() == Items.BUCKET )
				{
					if ( tileBarrel.getWaterAmount() > 0 )
					{
						tileBarrel.takeWater();
						InventoryHelper.takeItemCount(itemStackInHand, 1);
						InventoryHelper.addItemCount(playerIn, Items.WATER_BUCKET, 1);
					}
				}
			}
			else if (tileBarrel.isClosed() && playerIn.isSneaking())
			{
				worldIn.setBlockState( tileBarrel.getPos(), state.withProperty(CLOSED, Boolean.valueOf(false)) );
				InventoryHelper.addItemCount(playerIn, ItemRegistration.woodenCap, 1);
			}
		}
		
		return true;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING, CLOSED});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBarrel();
	}

}
