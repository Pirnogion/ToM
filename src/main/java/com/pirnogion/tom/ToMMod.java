package com.pirnogion.tom;

import java.util.ArrayList;

import com.pirnogion.network.NetworkPacket;
import com.pirnogion.network.NetworkPacketHandler;
import com.pirnogion.proxy.CommonProxy;
import com.pirnogion.tileEntity.TileEntityBarrel;
import com.pirnogion.tileEntity.TileEntityMagicFurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ToMMod.MODID, version = ToMMod.VERSION)
public class ToMMod
{
	public static final CreativeTabs tomStuffTab = new ToMCreativeTab();
	public static final String savePath = "";
	
    public static final String MODID = "tom";
    public static final String VERSION = "1.0 indev";
    public static final String CLIENT_PROXY = "com.pirnogion.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.pirnogion.proxy.CommonProxy";
    
    @Mod.Instance(value = "tom")
    public static ToMMod INSTANCE;

    @SidedProxy(clientSide = ToMMod.CLIENT_PROXY, serverSide = ToMMod.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance
    public static ToMMod instance;

    public static ToMMod getInstance() {
        return instance;
    }
    
    public static ToMMod instance() {
        return INSTANCE;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.newSimpleChannel("MPMainCh").registerMessage(NetworkPacketHandler.class, NetworkPacket.class, 0, Side.CLIENT);
        
        ItemRegistration.constructItems();
        ItemRegistration.registerItems();
        
        BlockRegistration.constructBlocks();
        BlockRegistration.registerBlocks();
        
        proxy.registerEventHandlers();
        proxy.registerRenderes();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {    	
        proxy.init(); 
        GameRegistry.registerTileEntity(TileEntityBarrel.class, this.MODID + "TileEntityBarrel");
        GameRegistry.registerTileEntity(TileEntityMagicFurnace.class, this.MODID + "TileEntityMagicFurnace");
    }
}
