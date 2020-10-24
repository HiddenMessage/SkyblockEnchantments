package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Experience extends Enchantment {

    public Experience(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 1 + 10 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class experienceLogic {

        @SubscribeEvent
        public static void entityExpDrop(LivingExperienceDropEvent event) {
            if (Config.Common.experience.get()) {

                PlayerEntity player = event.getAttackingPlayer();

                if(!(player instanceof PlayerEntity)) { return; }

                ItemStack stack = event.getAttackingPlayer().getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.EXPERIENCE.get(), stack);


                int Exp = event.getOriginalExperience();
                int ModExp1 = (int) (Exp + Exp * 1.2);
                int ModExp2 = (int) (Exp + Exp * 1.5);
                int ModExp3 = (int) (Exp + Exp * 2.0);
                int ModExp4 = (int) (Exp + Exp * 2.5);
                int ModExp5 = (int) (Exp + Exp * 3.25);

                if (lvl == 1) {
                    player.giveExperiencePoints(ModExp1);
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.0f, 0.0f);
                }

                if (lvl == 2) {
                    player.giveExperiencePoints(ModExp2);
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.0f, 0.0f);
                }

                if (lvl == 3) {
                    player.giveExperiencePoints(ModExp3);
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.0f, 0.0f);
                }

                if (lvl == 4) {
                    player.giveExperiencePoints(ModExp4);
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.0f, 0.0f);
                }

                if (lvl == 5) {
                    player.giveExperiencePoints(ModExp5);
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.0f, 0.0f);
                }

            }
        }
    }
}