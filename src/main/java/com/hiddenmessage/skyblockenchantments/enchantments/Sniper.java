package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.system.CallbackI;

public class Sniper extends Enchantment {

    public Sniper(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 4;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 22;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class sniperLogic {

        public static int level = 0;
        public static double distance = 0;

        @SubscribeEvent
        public static void ProjectileHit(ProjectileImpactEvent event) {
            if (Config.Common.sniper.get()) {

                if (!(event.getEntity() instanceof TridentEntity)) {
                    return;
                }

                TridentEntity trident = (TridentEntity) event.getEntity();
                Entity thrower = trident.func_234616_v_();

                if (!(thrower instanceof PlayerEntity)) {
                    return;
                }

                distance = trident.getDistanceSq(thrower.getPosX(), thrower.getPosY(), thrower.getPosZ());
            }
        }

        @SubscribeEvent
        public static void TridentDamage(LivingHurtEvent event) {
            if (Config.Common.sniper.get()) {

                Entity source = event.getSource().getTrueSource();
                LivingEntity entity = event.getEntityLiving();
                World world = entity.getEntityWorld();
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);

                if (serverworld.getPlayers().contains(source)) {
                    PlayerEntity player = (PlayerEntity) source;

                    entity.attackEntityFrom(DamageSource.GENERIC, 1.0f);

                    if (entity.isLiving() && level != 0) {
                        if (distance >= 250 && distance < 500) {
                            event.setAmount(8 + level);
                        }
                        if (distance >= 500 && distance < 750) {
                            event.setAmount(10 + level);
                        }
                        if (distance >= 750 && distance < 1000) {
                            event.setAmount(13 + level);
                        }
                        if (distance >= 1000) {
                            event.setAmount(16 + level);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void TridentCheck(EntityJoinWorldEvent event) {
            if (Config.Common.sniper.get()) {

                if (!(event.getEntity() instanceof TridentEntity)) {
                    return;
                }
                TridentEntity trident = (TridentEntity) event.getEntity();

                Entity thrower = trident.func_234616_v_();
                if (!(thrower instanceof PlayerEntity)) {
                    return;
                }
                PlayerEntity player = (PlayerEntity) thrower;
                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SNIPER.get(), stack);

                level = lvl;
            }
        }
    }
}