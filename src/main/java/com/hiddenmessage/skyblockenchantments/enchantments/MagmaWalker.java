package com.hiddenmessage.skyblockenchantments.enchantments;

import com.google.common.base.Objects;
import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.BlockInit;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Random;


public class MagmaWalker extends Enchantment {

    public MagmaWalker(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 29;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class magmaWalkerLogic {

        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event) {
            if (Config.Common.magma_walker.get()) {

                PlayerEntity player = event.player;

                ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MAGMA_WALKER.get(), stack);

                BlockPos prevBlockpos = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, player, "field_184620_bC");
                BlockPos blockpos = new BlockPos(player.func_233580_cy_());


                if (!Objects.equal(prevBlockpos, blockpos)) {
                    frostWalkerFloor(player, player.world, blockpos, lvl);
                }
            }
        }
        private static void frostWalkerFloor(LivingEntity living, World worldIn, BlockPos pos, int level) {
            if (living.func_233570_aj_()) {
                if (living.func_233570_aj_()) {
                    BlockState blockstate = BlockInit.BURNT_MAGMA_BLOCK.get().getDefaultState();
                    float f = (float) Math.min(16, 2 + level);
                    BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                        if (blockpos.withinDistance(living.getPositionVec(), (double) f)) {
                            blockpos$mutable.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                            BlockState blockstate1 = worldIn.getBlockState(blockpos$mutable);
                            if (blockstate1.isAir(worldIn, blockpos$mutable)) {
                                BlockState blockstate2 = worldIn.getBlockState(blockpos);
                                boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.get(FlowingFluidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
                                if (blockstate2.getMaterial() == Material.LAVA && isFull && blockstate.isValidPosition(worldIn, blockpos) && worldIn.func_226663_a_(blockstate, blockpos, ISelectionContext.dummy()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(living, net.minecraftforge.common.util.BlockSnapshot.create(worldIn.func_234923_W_(), worldIn, blockpos), net.minecraft.util.Direction.UP) && level != 0) {
                                    worldIn.setBlockState(blockpos, blockstate);
                                    worldIn.getPendingBlockTicks().scheduleTick(blockpos, BlockInit.BURNT_MAGMA_BLOCK.get(), MathHelper.nextInt(living.getRNG(), 60, 120));
                                }
                            }
                        }
                    }

                }
            }
        }
        @OnlyIn(Dist.CLIENT)
        public static void spawnParticles(BlockPos blockpos, World worldIn)
        {
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, blockpos.getX()+new Random().nextFloat(), blockpos.getY()+new Random().nextFloat(), blockpos.getZ()+new Random().nextFloat(),0, 0,0);
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, blockpos.getX()+new Random().nextFloat(), blockpos.getY()+new Random().nextFloat(), blockpos.getZ()+new Random().nextFloat(),0, 0,0);
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, blockpos.getX()+new Random().nextFloat(), blockpos.getY()+new Random().nextFloat(), blockpos.getZ()+new Random().nextFloat(),0, 0,0);
        }
    }
}