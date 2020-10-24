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
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class Speed extends Enchantment {

    public Speed(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() { return 3; }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class speedLogic {


        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event) {
            if (Config.Common.speed.get()) {

                PlayerEntity player = event.player;

                ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPEED.get(), stack);

                AttributeModifier Speed = new AttributeModifier(UUID.fromString("c91f8076-5456-43d8-b460-9533d0088815"), "MoveSpeed", 0.1 + lvl * 0.04, AttributeModifier.Operation.ADDITION);

                if (lvl != 0) {
                        if (!player.getAttribute(Attributes.field_233821_d_).hasModifier(Speed)) {
                            player.getAttribute(Attributes.field_233821_d_).func_233767_b_(Speed);
                        }
                    }

                if (lvl == 0) {
                    if (player.getAttribute(Attributes.field_233821_d_).hasModifier(Speed)) {
                        player.getAttribute(Attributes.field_233821_d_).removeModifier(Speed);
                    }
                }
            }
        }
    }
}