package com.hiddenmessage.skyblockenchantments;

import net.minecraft.item.*;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEnchantmentType {

    public static final EnchantmentType HOE = EnchantmentType.create(SkyblockEnchantments.MOD_ID + ":hoe", item -> item.getItem() instanceof HoeItem);
    public static final EnchantmentType PICKAXE = EnchantmentType.create(SkyblockEnchantments.MOD_ID + ":pickaxe", item -> item.getItem() instanceof PickaxeItem);
    public static final EnchantmentType SHEARS = EnchantmentType.create(SkyblockEnchantments.MOD_ID + ":shears", item -> item.getItem() instanceof ShearsItem);
    public static final EnchantmentType BOWS = EnchantmentType.create(SkyblockEnchantments.MOD_ID + ":bows", item -> item.getItem() instanceof BowItem || item.getItem() instanceof CrossbowItem );
    public static final EnchantmentType AXE = EnchantmentType.create(SkyblockEnchantments.MOD_ID + ":axe", item -> item.getItem() instanceof AxeItem);
    public static final EnchantmentType NOAXE = EnchantmentType.create(SkyblockEnchantments.MOD_ID + ":noaxe", item -> item.getItem() instanceof PickaxeItem || item.getItem() instanceof ShovelItem);
    public static final EnchantmentType NOT_ARMOR = EnchantmentType.create(SkyblockEnchantments.MOD_ID + ":not_armor", item -> item.getItem() instanceof HoeItem || item.getItem() instanceof ShearsItem || item.getItem() instanceof SwordItem || item.getItem() instanceof PickaxeItem || item.getItem() instanceof AxeItem || item.getItem() instanceof ShovelItem || item.getItem() instanceof BowItem || item.getItem() instanceof CrossbowItem);


    public void ModEnchantmentType() {
        MinecraftForge.EVENT_BUS.register(this);
        this.addEnchantmentTypesToGroup(ItemGroup.TOOLS, HOE, PICKAXE, SHEARS, BOWS, AXE, NOAXE, NOT_ARMOR);
    }

    private void addEnchantmentTypesToGroup(ItemGroup group, EnchantmentType... types) {
        EnchantmentType[] oldTypes = group.getRelevantEnchantmentTypes();
        EnchantmentType[] newTypes = new EnchantmentType[oldTypes.length + types.length];
        System.arraycopy(oldTypes, 0, newTypes, 0, oldTypes.length);
        System.arraycopy(types, 0, newTypes, oldTypes.length, types.length);
        group.setRelevantEnchantmentTypes(newTypes);
    }
}