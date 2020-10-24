package com.hiddenmessage.skyblockenchantments.enchantments;

import com.google.gson.JsonObject;
import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import com.sun.org.apache.xpath.internal.operations.String;
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
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sun.net.ResourceManager;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AutoSmelt extends Enchantment {

    public AutoSmelt(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 25;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return ench.equals(Enchantments.SILK_TOUCH) ? false : true;
    }


    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class autoSmeltLogic {

        @SubscribeEvent
        public static void playerMine(BlockEvent.BreakEvent event)
        {
            if (Config.Common.smelting_touch.get()) {

                PlayerEntity player = event.getPlayer();
                BlockState state = event.getState();
                BlockPos pos = event.getPos();
                World world = player.world;
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);
                List<ItemStack> drops = Block.getDrops(state, serverworld, pos, null);


                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AUTO_SMELT.get(), stack);
                int tlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), stack);

                if (lvl == 1 && drops.size() > 0) {
                    Inventory inventory = new Inventory(drops.get(0));
                    Optional<FurnaceRecipe> smelting = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, inventory, world);

                    if (smelting.isPresent()) {
                        if (tlvl == 0) {
                            ItemStack drop = new ItemStack(smelting.get().getRecipeOutput().getItem());
                            ItemEntity blockdrop = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop);
                            world.addEntity(blockdrop);
                            world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        }
                        if (tlvl == 1) {
                            ItemStack drop = new ItemStack(smelting.get().getRecipeOutput().getItem());
                            player.addItemStackToInventory(drop);
                            world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        }
                    }
                    if (!smelting.isPresent()) {
                        if (tlvl == 1) {
                            drops.forEach(itemStack -> player.addItemStackToInventory(itemStack.getStack()));
                            world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}