package com.pirnogion.tileEntity;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.pirnogion.utils.InventoryHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class TileEntityMagicFurnace extends TileEntity implements ITickable
{
	private static final FurnaceRecipes recipes = FurnaceRecipes.instance();
	
	private final int NORMAL      = 0;
	private final int OVERCLOCKED = 1;
	private final int OVERLOADED  = 2;
	private       int status      = NORMAL;
	
	private final int defaultUpdateRate     = 20*4;
	private final int overclockedUpdateRate = 20*1;
	private       int updateRate            = defaultUpdateRate;
	
	private final int overloadedLimit  = 153600;
	private final int overclockedLimit = 102400;
	private       int energyStored     = 0;
	
	private final int defaultOperationCost  = 200;
	private final int overclockedEnergyLoss = 800;
	
	private int tickCounter = 0;
	
	private boolean tookFuel = false;
	private boolean tookItem = false;
	
	private EntityItem capturedFuel;
	private EntityItem capturedItem;
	
	@Override
	public void update()
	{
		if ( this.getWorld().isRemote )
		{
			Random rand = new Random();
			
			if ( tookItem )
			{
				for (int i = 0; i < 5; ++i)
				{
					getWorld().spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+rand.nextDouble(), (double)pos.getY()+0.1875D+rand.nextDouble(), (double)pos.getZ()+rand.nextDouble(), 0.0D, 0.05D, 0.0D, new int[0]);
					getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)pos.getX()+rand.nextDouble(), (double)pos.getY()+0.1875D+rand.nextDouble(), (double)pos.getZ()+rand.nextDouble(), 0.0D, 0.05D, 0.0D, new int[0]);
				}
				
				tookItem = false;
			}
			
			if ( tookFuel )
			{
				getWorld().spawnParticle(EnumParticleTypes.CLOUD, (double)pos.getX()+rand.nextDouble(), (double)pos.getY()+0.1875D+rand.nextDouble(), (double)pos.getZ()+rand.nextDouble(), 0.0D, 0.05D, 0.0D, new int[0]);
				
				tookFuel = false;
			}
		}
		
		if ( !this.getWorld().isRemote )
		{
			++tickCounter;
			
			if ( tickCounter == updateRate )
			{
				tickCounter = 0;
				
				/* Store energy */
				if ( capturedFuel != null && !capturedFuel.isDead )
				{
					ItemStack fuel = capturedFuel.getEntityItem();
					
					if (fuel.getCount() > 0)
					{
						int energyAsItem = TileEntityFurnace.getItemBurnTime(fuel);
						storeEnergy(energyAsItem, 1);
						
						if ( fuel.getItem() == Items.LAVA_BUCKET )
							InventoryHelper.spawnEntityItem(getWorld(), (double)pos.getX(), (double)pos.getY()+0.5D, (double)pos.getZ(), new ItemStack(Items.BUCKET, 1));
							
						tookFuel = true;
						fuel.setCount( fuel.getCount() - 1 );

						getWorld().playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.8F);
						
						forceSync();
					}
				}
				else
				{
					capturedFuel = filtrateEntityItem(findEntityItemIntoBB(), TileEntityMagicFurnace::isFuel);
					if ( capturedFuel != null ) capturedFuel.lifespan = Integer.MAX_VALUE;
				}
				
				/* Smelt item */
				if ( capturedItem != null && !capturedItem.isDead )
				{
					ItemStack item = capturedItem.getEntityItem();
					
					if ( item.getCount() > 0 && energyStored >= defaultOperationCost )
					{
						ItemStack smeltedResult = recipes.getSmeltingResult(item);
						ItemStack smeltedItemStack = new ItemStack(smeltedResult.getItem(), 1, smeltedResult.getMetadata());
						
						consumeEnergy(defaultOperationCost, 1);
						item.setCount( item.getCount() - 1 );
						InventoryHelper.spawnEntityItem(getWorld(), (double)pos.getX(), (double)pos.getY()+0.5D, (double)pos.getZ(), smeltedItemStack);

						getWorld().playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (getWorld().rand.nextFloat() - getWorld().rand.nextFloat()) * 0.8F);
						
						tookItem = true;
						
						forceSync();
					}
				}
				else
				{
					capturedItem = filtrateEntityItem(findEntityItemIntoBB(), TileEntityMagicFurnace::canSmelt);
					if ( capturedItem != null ) capturedItem.lifespan = Integer.MAX_VALUE;
				}
			}
		}
	}
	
	/* Find and filtrate EntityItems */
	private static Boolean isFuel(ItemStack itemStack)
	{
		return TileEntityFurnace.isItemFuel(itemStack);
	}
	
	private static Boolean canSmelt(ItemStack itemStack)
	{
		return recipes.getSmeltingResult(itemStack) != null;
	}
	
	private List<EntityItem> findEntityItemIntoBB()
	{		
		return this.getWorld().getEntitiesWithinAABB(
				EntityItem.class,
				new AxisAlignedBB(this.pos).expand(0d, 0.25d, 0d),
				EntitySelectors.IS_ALIVE
		);
	}
	
	private EntityItem filtrateEntityItem(List<EntityItem> foundItems, Predicate<ItemStack> filter)
	{
		for (EntityItem entityItem : foundItems)
		{
			if ( entityItem != null )
			{
				ItemStack itemStack = entityItem.getEntityItem();
				if ( itemStack != null && itemStack.getCount() > 0 )
					if ( filter.test(itemStack) ) return entityItem;
			}
		}
		
		return null;
	}

	/* ??? */
	public void updateStatus()
	{
		if ( energyStored > overclockedLimit  && status != OVERCLOCKED )
		{
			status = OVERCLOCKED;
			
			updateRate = overclockedUpdateRate;
			
			getWorld().playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);

			return;
		}

		if ( energyStored > 0 && status != NORMAL )
		{
			status = NORMAL;
			
			getWorld().playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			
			updateRate = defaultUpdateRate;

			return;
		}

		if ( energyStored > overloadedLimit )
		{
			status = OVERLOADED;

			getWorld().createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10.0F, true);
			getWorld().playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);

			return;
		}
	}
	
	public void storeEnergy(int count, int multiplier)
	{
		switch ( status )
		{	        
		case OVERCLOCKED:
			energyStored += (count * multiplier) - (multiplier * overclockedEnergyLoss);
			break;
			
		case NORMAL:
			energyStored += count * multiplier;
			break;
		}
		
		updateStatus();
	}
	
	public void consumeEnergy(int count, int multiplier)
	{
		energyStored -= count * multiplier;
		
		updateStatus();
	}
	
	/* ??? */
	public int getOverloadedLimit()
	{
		return overloadedLimit;
	}
	
	public int getCapacity()
	{
		return overclockedLimit;
	}
	
	public int getStoredEnergy()
	{
		return energyStored;
	}
	
	/* ??? */
	private void forceSync()
	{
		IBlockState state = this.getWorld().getBlockState(pos);
        this.getWorld().notifyBlockUpdate(pos, state, state, 3);
		this.markDirty();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("STATUS", status);
		compound.setInteger("UPDATE_RATE", updateRate);
		compound.setInteger("ENERGY_STORED", energyStored);
		
		compound.setBoolean("TOOK_FUEL", tookFuel);
		compound.setBoolean("TOOK_ITEM", tookItem);
		
		tookFuel = false; tookItem = false;
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		status = compound.getInteger("STATUS");
		updateRate = compound.getInteger("UPDATE_RATE");
		energyStored = compound.getInteger("ENERGY_STORED");
		
		tookFuel = compound.getBoolean("TOOK_FUEL");
		tookItem = compound.getBoolean("TOOK_ITEM");
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		int metadata = this.getBlockMetadata();
		
		return new SPacketUpdateTileEntity(this.pos, metadata, compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT( pkt.getNbtCompound() );
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return ( newSate.getBlock() != oldState.getBlock() );
	}
}
