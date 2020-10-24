package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class Destroyer extends Enchantment {

    public Destroyer(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 19 + (enchantmentLevel*2);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class destroyerLogic {

        @SubscribeEvent
        public static void playerMine(PlayerEvent.BreakSpeed event) {
            if (Config.Common.destroyer.get()) {
                PlayerEntity player = event.getPlayer();
                BlockState state = event.getState();

                List<Block> blocks = new ArrayList<>();
                blocks.add(Blocks.ENDER_CHEST);
                blocks.add(Blocks.field_235398_nh_);
                blocks.add(Blocks.field_235399_ni_);
                blocks.add(Blocks.field_235397_ng_);
                blocks.add(Blocks.field_235400_nj_);
                blocks.add(Blocks.OBSIDIAN);

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.DESTROYER.get(), stack);

                if(lvl != 0) {
                    if(blocks.contains(state.getBlock())) {
                        event.setNewSpeed(event.getOriginalSpeed() + event.getOriginalSpeed()*lvl*2);
                    }
                }
            }
        }
    }
}