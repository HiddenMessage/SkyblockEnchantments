package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class LifeSteal extends Enchantment {

    public LifeSteal(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
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


    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class lifeStealLogic {

        @SubscribeEvent
        public static void playerAttack(LivingHurtEvent event) {
            if (Config.Common.life_steal.get()) {

                Entity source = event.getSource().getTrueSource();
                LivingEntity entity = event.getEntityLiving();
                World world = entity.getEntityWorld();
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);

                if (serverworld.getPlayers().contains(source)) {
                    PlayerEntity player = (PlayerEntity) source;

                    ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                    int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.LIFE_STEAL.get(), stack);

                    float damage = event.getAmount();
                    entity.attackEntityFrom(DamageSource.GENERIC, 1.0f);

                    if (lvl == 1) {
                        player.heal(damage * 0.05f);
                    }

                    if (lvl == 2) {
                        player.heal(damage * 0.075f);
                    }

                    if (lvl == 3) {
                        player.heal(damage * 0.10f);
                    }
                }
            }
        }
    }
}




