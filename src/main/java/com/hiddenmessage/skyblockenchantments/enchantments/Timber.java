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
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Timber extends Enchantment {

    public Timber(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
    public static class timberLogic {

        private static int hue;

        @SubscribeEvent
        public static void playerMine(BlockEvent.BreakEvent event) {
            if (Config.Common.wood_shatter.get()) {

                PlayerEntity player = event.getPlayer();
                BlockState state = event.getState();
                BlockPos pos = event.getPos();
                World world = player.world;
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);


                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TIMBER.get(), stack);
                int ulvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
                int tlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), stack);

                int treeY = 6;
                int treeX = 1;
                int treeZ = 1;

                if (state.getBlock() == Blocks.DARK_OAK_LOG) {
                    treeY = 15;
                    treeX = 2;
                    treeZ = 2;
                }
                if (state.getBlock() == Blocks.JUNGLE_LOG) {
                    treeY = 15;
                }

                for (int offsetX = -treeX; offsetX <= treeX; offsetX++) {
                    for (int offsetZ = -treeZ; offsetZ <= treeZ; offsetZ++) {
                        for (int offsetY = -treeY; offsetY <= treeY; offsetY++) {


                            BlockPos Offset = new BlockPos(pos.getX() + offsetX, pos.getY() + offsetY + treeY, pos.getZ() + offsetZ);
                            BlockState OffsetState = world.getBlockState(Offset);
                            List<ItemStack> drops = Block.getDrops(OffsetState, serverworld, pos, null);

                            if (lvl == 1) {
                                {
                                    if (OffsetState.getBlock().isIn(BlockTags.LOGS)) {
                                        if (OffsetState.getBlock() == state.getBlock()) {
                                            int rng1 = new Random().nextInt(4);
                                            int rng2 = new Random().nextInt(4);

                                            if (drops.size() >= 2 && ulvl >= rng1) {
                                                stack.setDamage(stack.getDamage() - 1);
                                                if (drops.size() >= 4 && ulvl >= rng2) {
                                                    stack.setDamage(stack.getDamage() - 1);
                                                }
                                            }
                                            if (tlvl == 0) {
                                                spawnParticles(serverworld, Offset, OffsetState);
                                                world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                                drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                                ItemEntity blockdrop = new ItemEntity(world, Offset.getX(), Offset.getY(), Offset.getZ(), drops.get(0));
                                                world.addEntity(blockdrop);
                                            }
                                            if (tlvl == 1) {
                                                spawnParticles(serverworld, Offset, OffsetState);
                                                world.setBlockState(Offset, Blocks.AIR.getDefaultState());
                                                drops.forEach(itemStack -> stack.setDamage(stack.getDamage() + 1));
                                                drops.forEach(itemStack -> player.addItemStackToInventory(itemStack.getStack()));
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