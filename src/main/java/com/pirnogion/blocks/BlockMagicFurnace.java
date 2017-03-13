package com.pirnogion.blocks;

import java.util.Random;

import com.pirnogion.tileEntity.TileEntityMagicFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlockMagicFurnace extends Block implements ITileEntityProvider
{
	public static final PropertyBool IS_ACTIVE = PropertyBool.create("isactive");
	
	public BlockMagicFurnace(String unlocalizedName, String registryName)
	{
		super(Material.ROCK);

		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		
		this.setHardness(1.0f);
		this.setResistance(1.0f);
		
		this.setTickRandomly(true);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(IS_ACTIVE, Boolean.valueOf(false)));
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getValue(IS_ACTIVE) && worldIn instanceof WorldServer)
        {
            ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.25D, (double)pos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D, new int[0]);
        }
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
        return state.getCollisionBoundingBox(worldIn, pos);
    }
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {IS_ACTIVE});
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		boolean isActive = ((Boolean)state.getValue(IS_ACTIVE)).booleanValue();
		
		return isActive ? 1 : 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
        return this.getDefaultState().withProperty(IS_ACTIVE, Boolean.valueOf(false));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMagicFurnace();
	}
}
