package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rainbow extends Enchantment {

    public Rainbow(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 20;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class rainbowLogic {

        @SubscribeEvent
        public static void playerInteract(PlayerInteractEvent.EntityInteract event) {
            if (Config.Common.rainbow.get()) {

                Entity sheeptest = event.getTarget();
                PlayerEntity player = event.getPlayer();
                World world = player.world;

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAINBOW.get(), stack);
                int flvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
                int tlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), stack);

                if (lvl == 1) {
                    if (sheeptest.getType() == EntityType.SHEEP) {

                        SheepEntity sheep = (SheepEntity) sheeptest;

                        if (!sheep.isChild()) {
                            if (!sheep.getSheared()) {

                                List<ItemStack> drops = sheep.onSheared(player, event.getItemStack(), world, sheeptest.func_233580_cy_(), flvl);

                                List<ItemStack> wool = new ArrayList<>();
                                wool.add(new ItemStack(Items.WHITE_WOOL));
                                wool.add(new ItemStack(Items.ORANGE_WOOL));
                                wool.add(new ItemStack(Items.MAGENTA_WOOL));
                                wool.add(new ItemStack(Items.LIGHT_BLUE_WOOL));
                                wool.add(new ItemStack(Items.YELLOW_WOOL));
                                wool.add(new ItemStack(Items.LIME_WOOL));
                                wool.add(new ItemStack(Items.PINK_WOOL));
                                wool.add(new ItemStack(Items.GRAY_WOOL));
                                wool.add(new ItemStack(Items.LIGHT_GRAY_WOOL));
                                wool.add(new ItemStack(Items.CYAN_WOOL));
                                wool.add(new ItemStack(Items.PURPLE_WOOL));
                                wool.add(new ItemStack(Items.BROWN_WOOL));
                                wool.add(new ItemStack(Items.GREEN_WOOL));
                                wool.add(new ItemStack(Items.RED_WOOL));
                                wool.add(new ItemStack(Items.BLACK_WOOL));

                                int color0 = new Random().nextInt(15);
                                int color1 = new Random().nextInt(15);
                                int color2 = new Random().nextInt(15);
                                int color3 = new Random().nextInt(15);

                                ItemStack rng0 = wool.get(color0);
                                ItemStack rng1 = wool.get(color1);
                                ItemStack rng2 = wool.get(color2);
                                ItemStack rng3 = wool.get(color3);

                                if (tlvl == 1) {
                                    drops.forEach(itemStack -> player.addItemStackToInventory(rng0));
                                    if (drops.size() == 2) {
                                        drops.forEach(itemStack -> player.addItemStackToInventory(rng1));
                                    }
                                    if (drops.size() == 3) {
                                        drops.forEach(itemStack -> player.addItemStackToInventory(rng2));
                                        drops.forEach(itemStack -> player.addItemStackToInventory(rng3));
                                    }
                                }
                                if (tlvl == 0) {
                                    ItemEntity blockdrop0 = new ItemEntity(world, sheep.getPosX(), sheep.getPosY()+1, sheep.getPosZ(), rng0);
                                    world.addEntity(blockdrop0);
                                    if(drops.size() == 2) {
                                        ItemEntity blockdrop1 = new ItemEntity(world, sheep.getPosX(), sheep.getPosY()+1, sheep.getPosZ(), rng1);
                                        world.addEntity(blockdrop1);
                                    }
                                    if(drops.size() == 3) {
                                        ItemEntity blockdrop2 = new ItemEntity(world, sheep.getPosX(), sheep.getPosY()+1, sheep.getPosZ(), rng2);
                                        world.addEntity(blockdrop2);
                                        ItemEntity blockdrop3 = new ItemEntity(world, sheep.getPosX(), sheep.getPosY()+1, sheep.getPosZ(), rng3);
                                        world.addEntity(blockdrop3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



