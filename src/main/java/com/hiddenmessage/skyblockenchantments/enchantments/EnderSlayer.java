package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EnderSlayer extends Enchantment {

    public EnderSlayer(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 1 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return !(ench instanceof DamageEnchantment);
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class enderSlayerLogic {

        @SubscribeEvent
        public static void playerAttack(LivingHurtEvent event) {
            if (Config.Common.ender_slayer.get()) {

                Entity source = event.getSource().getTrueSource();
                LivingEntity entity = event.getEntityLiving();
                World world = entity.getEntityWorld();
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);

                if (serverworld.getPlayers().contains(source)) {
                    PlayerEntity player = (PlayerEntity) source;


                    ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                    int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ENDER_SLAYER.get(), stack);


                    float damage = event.getAmount();
                    entity.attackEntityFrom(DamageSource.GENERIC, 1.0f);

                    if (entity.getType().equals(EntityType.ENDERMAN) || entity.getType().equals(EntityType.ENDERMITE) || entity.getType().equals(EntityType.SHULKER) || entity.getType().equals(EntityType.ENDER_DRAGON)) {
                        if (lvl == 1) {
                            event.setAmount(event.getAmount() + event.getAmount() * 0.5f);
                        }
                        if (lvl == 2) {
                            event.setAmount(event.getAmount() + event.getAmount() * 1.0f);
                        }
                        if (lvl == 3) {
                            event.setAmount(event.getAmount() + event.getAmount() * 1.75f);
                        }
                        if (lvl == 4) {
                            event.setAmount(event.getAmount() + event.getAmount() * 2.5f);
                        }
                        if (lvl == 5) {
                            event.setAmount(event.getAmount() + event.getAmount() * 3.5f);
                        }
                    }
                }
            }
        }
    }
}
