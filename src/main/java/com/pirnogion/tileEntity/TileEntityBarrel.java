package com.pirnogion.tileEntity;

import java.awt.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.pirnogion.blocks.BlockBarrel;
import com.pirnogion.tom.ToMMod;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityBarrel extends TileEntity implements ITickable
{
	/* Hint: The amount of water is measured in bucket */
	public final int maxAmountOfWater = 10;
	public final int maxIngridients = 3;
	
	private int amountOfWater = 0;
	private ArrayList<ItemStack> ingridients = new ArrayList<ItemStack>();
	
	public boolean addItemStack(ItemStack itemStack)
	{
		if ( ingridients.size()+1 <= maxIngridients )
		{
			ingridients.add(itemStack);
			return true;
		}
		
		return false;
	}
	
	public ItemStack removeItemStack()
	{
		if ( ingridients.size()-1 >= 0 )
		{
			return ingridients.remove(ingridients.size());
		}
		
		return null;
	}
	
	public boolean addWater()
	{
		if ( amountOfWater+1 <= maxAmountOfWater )
		{
			amountOfWater = amountOfWater + 1;
			
			forceSync();
			return true;
		}
		
		return false;
	}
	
	public boolean takeWater()
	{
		if ( amountOfWater-1 >= 0 )
		{
			amountOfWater = amountOfWater - 1;
			
			forceSync();
			return true;
		}
		
		return false;
	}
	
	public int getWaterAmount()
	{
		return amountOfWater;
	}
	
	public boolean isClosed()
	{
		return this.getWorld().getBlockState(this.pos).getValue( BlockBarrel.CLOSED );
	}
	
	private void forceSync()
	{
		IBlockState state = this.getWorld().getBlockState(pos);
        this.getWorld().notifyBlockUpdate(pos, state, state, 3);
		this.markDirty();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("AmountOfWater", amountOfWater);
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		amountOfWater = compound.getInteger("AmountOfWater");
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
	public void update()
	{
		// Nothing
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return ( newSate.getBlock() != oldState.getBlock() );
	}
}
