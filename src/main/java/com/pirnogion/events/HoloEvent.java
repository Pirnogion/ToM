package com.pirnogion.events;

import com.pirnogion.inWorldRenderer.InventoryHolo;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL44;

import java.util.ArrayList;

public class HoloEvent
{
    private final Minecraft mc = Minecraft.getMinecraft();
    private ArrayList<IInventory> inventories = new ArrayList<IInventory>();
    private ArrayList<InventoryHolo> holograms = new ArrayList<InventoryHolo>();
    private ArrayList<Integer> deletedHolograms = new ArrayList<Integer>();

    public HoloEvent() {}

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event)
    {
        //Если игрок смотрит на инвентарь, то сохранить этот инвентарь и создать голограмму
        MinecraftServer srv = FMLCommonHandler.instance().getMinecraftServerInstance();
        World world = srv.getEntityWorld();

        RayTraceResult hit = mc.objectMouseOver;

        IInventory inventory = null;

        boolean isServerSide = world.isRemote == false;
        if ( isServerSide )
        {
            Entity entity = hit.entityHit;
            TileEntity tileEntity = world.getTileEntity(hit.getBlockPos());

            boolean isEntity = entity != null;
            if ( isEntity )
                entity = world.getEntityByID( entity.getEntityId() );

            boolean isNotNull = hit != null && tileEntity != null || entity != null;
            boolean isInventory = tileEntity instanceof IInventory || entity instanceof IInventory;
            if( isNotNull && isInventory )
            {
                inventory = isEntity ? (IInventory) entity : (IInventory) tileEntity;
                if ( !inventories.contains(inventory) )
                {
                    InventoryHolo hologram;

                    if ( isEntity )
                        hologram = new InventoryHolo(inventory, entity.posX, entity.posY, entity.posZ);
                    else
                        hologram = new InventoryHolo(inventory, hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ());

                    inventories.add( inventory );
                    holograms.add( hologram );
                }
            }
        }

        //Сортировка голограмм по дистанции(Пузырьковая сортировка)
        for (int i = holograms.size()-1; i > 0; --i)
        {
            for (int j = 0; j < i; ++j)
            {
                if ( holograms.get(j).getDistToPlayer() < holograms.get(j+1).getDistToPlayer() )
                {
                    InventoryHolo _hologram = holograms.get(j);
                    IInventory _inventory = inventories.get(j);

                    holograms.set(j, holograms.get(j+1));
                    inventories.set(j, inventories.get(j+1));

                    holograms.set(j+1, _hologram);
                    inventories.set(j+1, _inventory);
                }
            }
        }

        //Обработка и отрисовка голограмм
        for (int i = 0; i < holograms.size(); ++i)
        {
            InventoryHolo _hologram = holograms.get(i);

            if ( !_hologram.getDeadState() )
            {
                boolean _hologramWasActivated = inventory == inventories.get(i) ? true : false;

                _hologram.setHoverState( _hologramWasActivated );
                _hologram.renderHolo(event.getPartialTicks());
            }
            else deletedHolograms.add(i);
        }

        //Уничтожить "мертвые" голограммы
        for (int i = 0; i < deletedHolograms.size(); ++i)
        {
            inventories.remove(deletedHolograms.get(i)-i);
            holograms.remove(deletedHolograms.get(i)-i);
        }

        deletedHolograms.clear();
    }
}
