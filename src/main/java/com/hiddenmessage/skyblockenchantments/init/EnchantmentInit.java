package com.hiddenmessage.skyblockenchantments.init;

import com.hiddenmessage.skyblockenchantments.ModEnchantmentType;
import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.enchantments.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentInit
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(
            ForgeRegistries.ENCHANTMENTS, SkyblockEnchantments.MOD_ID);

    public static final RegistryObject<Enchantment> LIFE_STEAL = ENCHANTMENTS.register("life_steal",
            () -> new LifeSteal(Enchantment.Rarity.UNCOMMON, EnchantmentType.WEAPON,
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> GROWTH = ENCHANTMENTS.register("growth",
            () -> new Growth(Enchantment.Rarity.UNCOMMON, EnchantmentType.ARMOR,
                    new EquipmentSlotType[] {EquipmentSlotType.HEAD, EquipmentSlotType.FEET,EquipmentSlotType.CHEST, EquipmentSlotType.LEGS }));

    public static final RegistryObject<Enchantment> EXPERIENCE = ENCHANTMENTS.register("experience",
            () -> new Experience(Enchantment.Rarity.RARE, EnchantmentType.WEAPON,
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> FIREWORKS = ENCHANTMENTS.register("fireworks",
            () -> new Fireworks(Enchantment.Rarity.RARE, (ModEnchantmentType.BOWS),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> TELEKINESIS = ENCHANTMENTS.register("telekinesis",
            () -> new Telekinesis(Enchantment.Rarity.COMMON, (ModEnchantmentType.NOT_ARMOR),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> AUTO_SMELT = ENCHANTMENTS.register("smelting_touch",
            () -> new AutoSmelt(Enchantment.Rarity.RARE, (ModEnchantmentType.NOAXE),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> VEINMINER = ENCHANTMENTS.register("ore_shatter",
            () -> new Veinminer(Enchantment.Rarity.RARE, (ModEnchantmentType.PICKAXE),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> TIMBER = ENCHANTMENTS.register("wood_shatter",
            () -> new Timber(Enchantment.Rarity.RARE, (ModEnchantmentType.AXE),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> MAGMA_WALKER = ENCHANTMENTS.register("magma_walker",
            () -> new MagmaWalker(Enchantment.Rarity.VERY_RARE, (EnchantmentType.ARMOR_FEET),
                    new EquipmentSlotType[]{EquipmentSlotType.FEET}));

    public static final RegistryObject<Enchantment> SEEDING = ENCHANTMENTS.register("replenish",
            () -> new Seeding(Enchantment.Rarity.UNCOMMON, (ModEnchantmentType.HOE),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> SPEED = ENCHANTMENTS.register("speed",
            () -> new Speed(Enchantment.Rarity.COMMON, (EnchantmentType.ARMOR_FEET),
                    new EquipmentSlotType[]{EquipmentSlotType.FEET}));

    public static final RegistryObject<Enchantment> RAINBOW = ENCHANTMENTS.register("rainbow",
            () -> new Speed(Enchantment.Rarity.UNCOMMON, (ModEnchantmentType.SHEARS),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> ENDER_SLAYER = ENCHANTMENTS.register("ender_slayer",
            () -> new EnderSlayer(Enchantment.Rarity.UNCOMMON, EnchantmentType.WEAPON,
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> AIMING = ENCHANTMENTS.register("aiming",
            () -> new Aiming(Enchantment.Rarity.VERY_RARE, (ModEnchantmentType.BOWS),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> HARVESTING = ENCHANTMENTS.register("harvesting",
            () -> new Harvesting(Enchantment.Rarity.RARE, (ModEnchantmentType.HOE),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> LEAFMINER = ENCHANTMENTS.register("leaf_shatter",
            () -> new Leafminer(Enchantment.Rarity.RARE, (ModEnchantmentType.SHEARS),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> MIGHT = ENCHANTMENTS.register("might",
            () -> new Might(Enchantment.Rarity.UNCOMMON, EnchantmentType.WEAPON,
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> DESTROYER = ENCHANTMENTS.register("destroyer",
            () -> new Destroyer(Enchantment.Rarity.RARE, (ModEnchantmentType.PICKAXE),
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> SNIPER = ENCHANTMENTS.register("sniper",
            () -> new Destroyer(Enchantment.Rarity.RARE, EnchantmentType.TRIDENT,
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

    public static final RegistryObject<Enchantment> TRACKER = ENCHANTMENTS.register("tracker",
            () -> new Destroyer(Enchantment.Rarity.RARE, EnchantmentType.TRIDENT,
                    new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
}


