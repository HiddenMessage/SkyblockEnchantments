package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

public class Harvesting extends Enchantment {

    public Harvesting(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
    public static class seedingLogic {

        public static boolean executedBreak = false;

        @SubscribeEvent
        public static void playerMine(BlockEvent.BreakEvent event) {
            if (Config.Common.harvesting.get()) {

                PlayerEntity player = event.getPlayer();
                BlockState state = event.getState();
                BlockPos pos = event.getPos();
                World world = player.world;
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);
                List<ItemStack> drops = Block.getDrops(state, serverworld, pos, null);

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.HARVESTING.get(), stack);

                if (lvl != 0) {
                    if (state.getBlock().isIn(BlockTags.CROPS)) {

                        int rng = new Random().nextInt(8);
                        ItemStack extra = new ItemStack(drops.get(0).getItem());

                        if (lvl >= rng) {
                            spawnParticles(serverworld, pos, state);
                            drops.add(extra);
                        }
                    }
                }
            }
        }
        @OnlyIn(Dist.CLIENT)
        private static void spawnParticles(ServerWorld serverworld, BlockPos pos, BlockState state)
        {
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
            serverworld.spawnParticle(ParticleTypes.HAPPY_VILLAGER, (double)((float)pos.getX() + new Random().nextFloat()), (double)pos.getY() + (double)new Random().nextFloat() * state.getShape(serverworld, pos).getEnd(Direction.Axis.Y)-0.15, (double)((float)pos.getZ() + new Random().nextFloat()), 1, 0,0,0, 100);
        }
    }
}