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
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sun.security.pkcs11.wrapper.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Veinminer extends Enchantment {

    public Veinminer(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 27;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class veinminerLogic {

        private static int hue;

        @SubscribeEvent
        public static void playerMine(BlockEvent.BreakEvent event) {
            if (Config.Common.ore_shatter.get()) {

                PlayerEntity player = event.getPlayer();
                BlockState state = event.getState();
                BlockPos pos = event.getPos();
                World world = player.world;
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);


                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.VEINMINER.get(), stack);

                for (int offsetX = -4; offsetX <= 4; offsetX++) {
                    for (int offsetZ = -4; offsetZ <= 4; offsetZ++) {
                        for (int offsetY = -4; offsetY <= 4; offsetY++) {


                            BlockPos Offset = new BlockPos(pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ);
                            BlockState OffsetState = world.getBlockState(Offset);
                            List<ItemStack> drops = Block.getDrops(OffsetState, serverworld, pos, null);

                            int aslvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AUTO_SMELT.get(), stack);
                            int tlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), stack);
                            int ulvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
                            int flvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
                            int exp = state.getExpDrop(world, pos, flvl, 0);

                            if (lvl != 0) {
                                if (OffsetState.getBlock().isIn(Tags.Blocks.ORES) && OffsetState.getBlock() == state.getBlock()) {
                                    int rng1 = new Random().nextInt(4);
                                    int rng2 = new Random().nextInt(4);

                                    if (drops.size() >= 2 && ulvl >= rng1) {
                                        stack.setDamage(stack.getDamage() - 1);
                                        if (drops.size() >= 4 && ulvl >= rng2) {
                                            stack.setDamage(stack.getDamage() - 1);
                                        }
                                    }
                                    if (aslvl == 0) {
                                        if (tlvl == 0) {
                                            world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                            ItemEntity blockdrop = new ItemEntity(world, Offset.getX(), Offset.getY(), Offset.getZ(), drops.get(0));
                                            drops.forEach(itemStack -> world.addEntity(blockdrop));
                                            drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                            spawnParticles(serverworld, Offset, OffsetState);
                                            player.giveExperiencePoints(exp);
                                        }
                                        if (tlvl == 1) {
                                            world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                            drops.forEach(itemStack -> player.addItemStackToInventory(itemStack.getStack()));
                                            drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                            spawnParticles(serverworld, Offset, OffsetState);
                                            player.giveExperiencePoints(exp);
                                        }
                                    }
                                    if (aslvl == 1) {
                                        Inventory inventory = new Inventory(drops.get(0));
                                        Optional<FurnaceRecipe> smelting = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, inventory, world);
                                        if (smelting.isPresent()) {
                                            if (tlvl == 0) {
                                                ItemStack drop = new ItemStack(smelting.get().getRecipeOutput().getItem());
                                                ItemEntity blockdrop = new ItemEntity(world, Offset.getX(), Offset.getY(), Offset.getZ(), drop);
                                                world.addEntity(blockdrop);
                                                spawnParticles(serverworld, Offset, OffsetState);
                                                drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                                world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                            }
                                            if (tlvl == 1) {
                                                ItemStack drop = new ItemStack(smelting.get().getRecipeOutput().getItem());
                                                player.addItemStackToInventory(drop);
                                                spawnParticles(serverworld, Offset, OffsetState);
                                                drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                                world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                            }
                                        }
                                        if (!smelting.isPresent()) {
                                            if (tlvl == 0) {
                                                world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                                ItemEntity blockdrop = new ItemEntity(world, Offset.getX(), Offset.getY(), Offset.getZ(), drops.get(0));
                                                drops.forEach(itemStack -> world.addEntity(blockdrop));
                                                drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                                spawnParticles(serverworld, Offset, OffsetState);
                                                player.giveExperiencePoints(exp);
                                            }
                                            if (tlvl == 1) {
                                                world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                                drops.forEach(itemStack -> player.addItemStackToInventory(itemStack.getStack()));
                                                drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                                spawnParticles(serverworld, Offset, OffsetState);
                                                player.giveExperiencePoints(exp);
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
        @OnlyIn(Dist.CLIENT)
        private static void spawnParticles(ServerWorld serverworld, BlockPos Offset, BlockState OffsetState) {
            if(hue >= 255) {hue = 0;} else {hue++;}
            final Color color = Color.getHSBColor(hue/255F, 1.0F, 1.0F);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), (double) ((float) Offset.getX() + new Random().nextFloat()), (double) Offset.getY() + (double) new Random().nextFloat() * OffsetState.getShape(serverworld, Offset).getEnd(Direction.Axis.Y), (double) ((float) Offset.getZ() + new Random().nextFloat()), 1, 0, 0, 0, 100);
        }
    }
}