package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class Growth extends Enchantment {

    public Growth(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 2;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }


    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class growthLogic {

        @SubscribeEvent
        public static void playerEquip(TickEvent.PlayerTickEvent event) {
            if (Config.Common.growth.get()) {

                PlayerEntity player = event.player;

                ItemStack HeadStack = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
                int Headlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.GROWTH.get(), HeadStack);
                ItemStack ChestStack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                int Chestlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.GROWTH.get(), ChestStack);
                ItemStack LegsStack = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
                int Legslvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.GROWTH.get(), LegsStack);
                ItemStack FeetStack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
                int Feetlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.GROWTH.get(), FeetStack);


                AttributeModifier Head = new AttributeModifier(UUID.fromString("9f411daa-32dc-4835-8207-133abefc191e"), "MaxHealth1", (float) Headlvl, AttributeModifier.Operation.ADDITION);
                AttributeModifier Chest = new AttributeModifier(UUID.fromString("876af069-5559-42f4-8e0e-fef8ad505e55"), "MaxHealth2", (float) Chestlvl, AttributeModifier.Operation.ADDITION);
                AttributeModifier Legs = new AttributeModifier(UUID.fromString("71a48a0d-9583-4f43-92f3-10bed3377ace"), "MaxHealth3", (float) Legslvl, AttributeModifier.Operation.ADDITION);
                AttributeModifier Feet = new AttributeModifier(UUID.fromString("3b2daebc-3732-4b27-800c-ac7e047ebbdd"), "MaxHealth4", (float) Feetlvl, AttributeModifier.Operation.ADDITION);

                if (Headlvl != 0) {
                    if (!player.getAttribute(Attributes.field_233818_a_).hasModifier(Head)) {
                        player.getAttribute(Attributes.field_233818_a_).func_233767_b_(Head);
                        player.setHealth(player.getHealth()+Headlvl);

                    }
                }
                if (Headlvl == 0) {
                    if (player.getAttribute(Attributes.field_233818_a_).hasModifier(Head)) {
                        player.getAttribute(Attributes.field_233818_a_).removeModifier(Head);
                    }
                }
                if (Chestlvl != 0) {
                    if (!player.getAttribute(Attributes.field_233818_a_).hasModifier(Chest)) {
                        player.getAttribute(Attributes.field_233818_a_).func_233767_b_(Chest);
                        player.setHealth(player.getHealth()+Chestlvl);

                    }
                }
                if (Chestlvl == 0) {
                    if (player.getAttribute(Attributes.field_233818_a_).hasModifier(Chest)) {
                        player.getAttribute(Attributes.field_233818_a_).removeModifier(Chest);
                    }
                }
                if (Legslvl != 0) {
                    if (!player.getAttribute(Attributes.field_233818_a_).hasModifier(Legs)) {
                        player.getAttribute(Attributes.field_233818_a_).func_233767_b_(Legs);
                        player.setHealth(player.getHealth()+Legslvl);

                    }
                }
                if (Legslvl == 0) {
                    if (player.getAttribute(Attributes.field_233818_a_).hasModifier(Legs)) {
                        player.getAttribute(Attributes.field_233818_a_).removeModifier(Legs);
                    }
                }
                if (Feetlvl != 0) {
                    if (!player.getAttribute(Attributes.field_233818_a_).hasModifier(Feet)) {
                        player.getAttribute(Attributes.field_233818_a_).func_233767_b_(Feet);
                        player.setHealth(player.getHealth()+Feetlvl);

                    }
                }
                if (Feetlvl == 0) {
                    if (player.getAttribute(Attributes.field_233818_a_).hasModifier(Feet)) {
                        player.getAttribute(Attributes.field_233818_a_).removeModifier(Feet);
                    }
                }
            }
        }
    }
}