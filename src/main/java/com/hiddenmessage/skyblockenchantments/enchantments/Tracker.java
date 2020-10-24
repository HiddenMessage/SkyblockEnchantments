package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Tracker extends Enchantment {

    public Tracker(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
    public static class sniperLogic {

        @SubscribeEvent
        public static void ProjectileFind (EntityJoinWorldEvent event) {

            if (!(event.getEntity() instanceof TridentEntity)) {
                return;
            }
            TridentEntity trident = (TridentEntity) event.getEntity();

            Entity thrower = trident.func_234616_v_();
            if (!(thrower instanceof PlayerEntity)) {
                return;
            }
            PlayerEntity player = (PlayerEntity) thrower;
            World world = player.world;

            ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
            int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SNIPER.get(), stack);

            //Team Red = world.getScoreboard().createTeam("Red"); //If you want it to glow colors make a team

            if(trident.isGlowing() == false && lvl == 1) {
                trident.setGlowing(true);
            }
        }
    }
}