package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class Might extends Enchantment {

    public Might(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 40;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class speedLogic {

        private static boolean ExecutedMight = false;

        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event) {
            if (Config.Common.might.get()) {

                PlayerEntity player = event.player;

                ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT.get(), stack);

                if (lvl != 0) {
                    if (player.getAttribute(Attributes.field_233825_h_).getBaseValue() != 4.0 + lvl * 0.25) {
                        player.getAttribute(Attributes.field_233825_h_).setBaseValue(4.0 + lvl * 0.25);
                    }
                }
                if (lvl == 0) {
                    if (player.getAttribute(Attributes.field_233825_h_).getBaseValue() != 4.0) {
                        player.getAttribute(Attributes.field_233825_h_).setBaseValue(4.0);
                    }
                }
            }
        }
    }
}
