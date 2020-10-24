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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

public class Telekinesis extends Enchantment {

    public Telekinesis(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 1;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 30;
    }


    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class telekinesisLogic {

        @SubscribeEvent
        public static void playerMine(BlockEvent.BreakEvent event) {
            if (Config.Common.telekinesis.get()) {
                PlayerEntity player = event.getPlayer();
                BlockState state = event.getState();
                BlockPos pos = event.getPos();
                World world = player.world;
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);
                List<ItemStack> drops = Block.getDrops(state, serverworld, pos, null);


                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                Item shears = Items.SHEARS;
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), stack);
                int flvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
                int exp = state.getExpDrop(world, pos, flvl, 0);

                if (lvl == 1) {
                    if (stack.getItem().equals(shears)) {
                        if (state.getBlock().isIn(BlockTags.LEAVES)) {

                            ItemStack leaf = new ItemStack(state.getBlock());
                            player.addItemStackToInventory(leaf);
                        }
                    }
                    if (state.getBlock().equals(Blocks.SNOW)) {
                        player.addItemStackToInventory(new ItemStack(Items.SNOWBALL));
                    }
                    if (flvl != 0) {
                        if (state.getBlock().isIn(Tags.Blocks.ORES)) {

                            Inventory inventory = new Inventory(drops.get(0));
                            Optional<FurnaceRecipe> smelting = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, inventory, world);

                            if (!smelting.isPresent()) {
                                int rng = new Random().nextInt(100);

                                ItemStack ore0 = new ItemStack(drops.get(0).getItem());
                                ItemStack ore1 = new ItemStack(drops.get(0).getItem());
                                ItemStack ore2 = new ItemStack(drops.get(0).getItem());
                                ItemStack ore3 = new ItemStack(drops.get(0).getItem());

                                if (flvl == 1) {
                                    if (rng < 34) {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                        player.addItemStackToInventory(ore1);
                                    } else {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                    }
                                }
                                if (flvl == 2) {
                                    if (rng < 26) {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                        player.addItemStackToInventory(ore1);
                                        player.addItemStackToInventory(ore2);
                                    }
                                    if (rng >= 26 && rng < 51) {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                        player.addItemStackToInventory(ore1);
                                    } else {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                    }
                                }
                                if (flvl == 3) {
                                    if (rng < 21) {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                        player.addItemStackToInventory(ore1);
                                        player.addItemStackToInventory(ore2);
                                        player.addItemStackToInventory(ore3);
                                    }
                                    if (rng >= 21 && rng < 41) {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                        player.addItemStackToInventory(ore1);
                                        player.addItemStackToInventory(ore2);
                                    }
                                    if (rng < 61) {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                        player.addItemStackToInventory(ore1);
                                    } else {
                                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                                        player.addItemStackToInventory(ore0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void entityOnDeath(LivingDropsEvent event) {
            if (Config.Common.telekinesis.get()) {
                LivingEntity entity = event.getEntityLiving();
                Collection<ItemEntity> items = event.getDrops();
                World world = entity.getEntityWorld();
                Entity source = event.getSource().getTrueSource();
                List<ItemEntity> itemlist = new ArrayList<>();
                items.forEach(itemlist::add);
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);

                if (serverworld.getPlayers().contains(source)) {
                    PlayerEntity player = (PlayerEntity) source;


                    ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                    int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), stack);

                    if (lvl == 1) {
                        //player.sendMessage(new StringTextComponent(items.toString())); //Debug
                        items.forEach(itemEntity -> player.addItemStackToInventory(itemEntity.getItem()));
                    }
                }
            }
        }
        @SubscribeEvent
        public static void playerInteract(PlayerInteractEvent.EntityInteract event) {
            if (Config.Common.telekinesis.get()) {
                Entity sheeptest = event.getTarget();
                PlayerEntity player = event.getPlayer();
                World world = player.world;
                ItemStack shears = new ItemStack(Items.SHEARS);

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.TELEKINESIS.get(), stack);
                int rlvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAINBOW.get(), stack);
                int flvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

                if (lvl == 1) {
                    if (event.getItemStack() == shears) {
                        if (sheeptest.getType() == EntityType.SHEEP) {

                            SheepEntity sheep = (SheepEntity) sheeptest;

                            if (sheep.isChild() == false) {
                                if (sheep.getSheared() == false) {
                                    if (rlvl == 0) {
                                        List<ItemStack> drops = sheep.onSheared(player, event.getItemStack(), world, sheeptest.func_233580_cy_(), flvl);
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