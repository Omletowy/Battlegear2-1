package mods.battlegear2.common;



import mods.battlegear2.common.gui.BattlegearGUIHandeler;
import mods.battlegear2.common.heraldry.HeraldricWeaponRecipie;
import mods.battlegear2.common.items.ItemHeradryIcon;
import mods.battlegear2.common.utils.BattlegearConfig;
import mods.battlegear2.common.utils.BattlegearConnectionHandeler;
import mods.battlegear2.common.utils.BattlegearUtils;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="MB-Battlegear2", name="Mine & Blade: Battlegear 2", version="HeraldPrev - 0.3")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, 
	channels={
		BattlegearPacketHandeler.guiPackets,
		BattlegearPacketHandeler.syncBattlePackets,
		BattlegearPacketHandeler.mbAnimation,
		BattlegearPacketHandeler.guiHeraldryIconChange}, 
	packetHandler =BattlegearPacketHandeler.class)
public class BattleGear {
	
	 @Instance("MB-Battlegear2")
     public static BattleGear instance;
	 
	 public static final boolean debug = false;
	 
	 public static EnumArmorMaterial knightArmourMaterial;
	 
	
	@SidedProxy(clientSide="mods.battlegear2.client.ClientProxy",
			serverSide="mods.battlegear2.common.CommonProxy")
	public static CommonProxy proxy;
	
	public static String imageFolder = "/mods/battlegear2/textures/";
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		instance = this;
		//Knights armour is not as durable as diamond, provides 2 armour points less protection and is more enchantable
		knightArmourMaterial = EnumHelper.addArmorMaterial("knights.armour", 25, new int[]{3, 7, 5, 3}, 15);
		BattlegearConfig.getConfig(event);
        BattlegearConfig.registerRecipes();       
	}
	
	@cpw.mods.fml.common.Mod.Init
	public void Init(FMLInitializationEvent event){
		
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event){
		BattlegearUtils.scanAndProcessItems();
		
		
		proxy.registerKeyHandelers();
		NetworkRegistry.instance().registerGuiHandler(this, new BattlegearGUIHandeler());
		
		for (Item item : Item.itemsList) {
			if(item != null){
				
				if(item.itemID == Item.swordWood.itemID ||
						item.itemID == Item.swordStone.itemID ||
						item.itemID == Item.swordIron.itemID ||
						item.itemID == Item.swordDiamond.itemID ||
						item.itemID == Item.swordGold.itemID){
					
					GameRegistry.addRecipe(new HeraldricWeaponRecipie(item));
				}
				
			}
		}
		
		if(debug){
			proxy.registerTickHandelers();
			MinecraftForge.EVENT_BUS.register(new BattlemodeHookContainerClass());
			NetworkRegistry.instance().registerConnectionHandler(new BattlegearConnectionHandeler());
		}
	}
	
	
}
