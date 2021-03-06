package mods.battlegear2.common.items;

import java.util.Random;

import mods.battlegear2.api.IHeraldyItem;
import mods.battlegear2.api.OffhandAttackEvent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


//Should we make this also use the hearaldry? It actually doesn't look as good as the sword (and makes the sword a little more special)
public class ItemWaraxe extends OneHandedWeapon{ // implements IHeraldyItem{ Don't know if we want to do this or not
	
	private int ignoreDamageAmount;
	
	/*
	private Icon baseIcon;
	private Icon trimIcon;
	private Icon postRenderIcon;
	 */
	public ItemWaraxe(int par1, EnumToolMaterial material, String name, int ignoreDamageAmount) {
		super(par1,material,name);
		this.ignoreDamageAmount = ignoreDamageAmount;
		//set the base damage to that of lower than usual (balance)
		this.baseDamage = baseDamage-1;
	}
	
	@Override
	public boolean canHarvestBlock(Block par1Block)//Waraxe can harvest logs
    {
        return par1Block.blockID == Block.wood.blockID;
    }
	
	@Override
	public int getDamageVsEntity(Entity par1Entity)
    {
		
		if(par1Entity instanceof EntityLiving){
			
			//Record the hurt times
			int hurtTimeTemp = ((EntityLiving) par1Entity).hurtTime;
			int hurtResistanceTimeTemp = par1Entity.hurtResistantTime;
			
			//Attack using the "generic" damage type (ignores armour)
			par1Entity.attackEntityFrom(DamageSource.generic, ignoreDamageAmount);
			
			//Re-apply the saved values
			((EntityLiving) par1Entity).hurtTime = hurtTimeTemp;
			par1Entity.hurtResistantTime = hurtResistanceTimeTemp;
			
			//return the normal damage minus the amount usually ignored
			return super.getDamageVsEntity(par1Entity)-ignoreDamageAmount;
		}else{
			return super.getDamageVsEntity(par1Entity);
		}
		
    }
	
	

	/*
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		baseIcon = par1IconRegister.registerIcon("battlegear2:waraxe-heraldry/"+name+".hilt");
		trimIcon = par1IconRegister.registerIcon("battlegear2:waraxe-heraldry/"+name+".gem");
		postRenderIcon = par1IconRegister.registerIcon("battlegear2:waraxe-heraldry/"+name+".blade");
	}

	@Override
	public Icon getBaseIcon() {
		return baseIcon;
	}

	@Override
	public Icon getTrimIcon() {
		return trimIcon;
	}

	@Override
	public Icon getPostRenderIcon() {
		return postRenderIcon;
	}

	@Override
	public boolean hasHeraldry(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey("heraldry");
	}

	@Override
	public int getHeraldryCode(ItemStack stack) {
		return stack.getTagCompound().getInteger("heraldry");
	}

	@Override
	public void setHeraldryCode(ItemStack stack, int code) {
		NBTTagCompound compound = stack.getTagCompound();
		if(compound == null)
			compound = new NBTTagCompound();
		compound.setInteger("heraldry", code);
		stack.setTagCompound(compound);		
	}

	@Override
	public void removeHeraldry(ItemStack stack) {
		if(hasHeraldry(stack)){
			stack.getTagCompound().removeTag("heraldry");
		}
	}

	@Override
	public boolean shouldDoPass(HeraldyRenderPassess pass) {
		return pass == HeraldyRenderPassess.PostRenderIcon ||
				pass == HeraldyRenderPassess.SecondaryColourTrim ||
				pass == HeraldyRenderPassess.PrimaryColourBase;
	}

	@Override
	public boolean useDefaultRenderer() {
		return true;
	}
	*/
}
