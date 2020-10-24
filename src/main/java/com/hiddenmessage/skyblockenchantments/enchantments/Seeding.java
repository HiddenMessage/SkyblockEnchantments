package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Seeding extends Enchantment {

    public Seeding(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class seedingLogic {

        @SubscribeEvent
        public static void playerMine(BlockEvent.BreakEvent event) {
            if (Config.Common.replenish.get()) {

                PlayerEntity player = event.getPlayer();
                BlockState state = event.getState();
                BlockPos pos = event.getPos();
                World world = player.world;
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);
                List<ItemStack> drops = Block.getDrops(state, serverworld, pos, null);

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SEEDING.get(), stack);

                if (lvl == 1) {

                    if (state.getBlock().isIn(BlockTags.CROPS)) {
                        CropsBlock plant = (CropsBlock) state.getBlock();
                        ItemStack plantdrop = plant.getItem(world, pos, state);
                        BlockItem blockItem = (BlockItem) plantdrop.getItem();
                        BlockState seeds = blockItem.getBlock().getDefaultState();

                        if (state.get(plant.getAgeProperty()) != plant.getMaxAge()) {
                            UUID id = player.getUniqueID();
                            player.sendMessage(new StringTextComponent("The plant was not fully grown."), id);
                            server.enqueue(new TickDelayedTask(0, () -> world.setBlockState(pos, seeds, 3)));
                            drops.forEach(itemStack -> player.addItemStackToInventory(itemStack.getStack()));

                            for (int inv = 0; inv < player.inventory.getSizeInventory(); inv++) {
                                ItemStack seedItem = player.inventory.getStackInSlot(inv);
                                if (seedItem == drops.get(1)) {
                                    drops.get(1).shrink(1);
                                }
                                if (state.getBlock() == Blocks.CARROTS) {
                                    drops.get(0).shrink(1);
                                }
                                if (state.getBlock() == Blocks.POTATOES) {
                                    drops.get(0).shrink(1);
                                }

                            }
                        }
                        if (state.get(plant.getAgeProperty()) == plant.getMaxAge()) {
                            server.enqueue(new TickDelayedTask(0, () -> world.setBlockState(pos, seeds, 3)));
                            //drops.forEach(itemStack -> player.addItemStackToInventory(itemStack.getStack()));


                            for (int inv = 0; inv < player.inventory.getSizeInventory(); inv++) {
                                ItemStack seedItem = player.inventory.getStackInSlot(inv);
                                if (seedItem == drops.get(1)) {
                                    drops.get(1).shrink(1);
                                }
                                if (state.getBlock() == Blocks.CARROTS) {
                                    drops.get(0).shrink(1);
                                }
                                if (state.getBlock() == Blocks.POTATOES) {
                                    drops.get(0).shrink(1);
                                }

                            }

                        }
                    }
                }
            }
        }
    }
}