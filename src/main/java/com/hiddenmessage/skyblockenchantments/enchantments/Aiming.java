package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.entity.HomingArrow;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aiming extends Enchantment {

    public Aiming(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 30;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class aimingLogic {

        private static int charge = 0;
        public static int level = 0;
        public static int plevel = 0;
        public static HomingArrow Arrow;
        public static PlayerEntity Player;
        public static boolean fired = false;
        public static boolean Fired = false;
        private static int hue;
        public static ServerWorld Serverworld;
        public static LivingEntity Target;

        @SubscribeEvent
        public static void arrowSpawn(EntityJoinWorldEvent event) {
            if (Config.Common.aiming.get()) {

                Entity arrow = event.getEntity();
                if (!(arrow instanceof AbstractArrowEntity)) {
                    return;
                }
                Entity shooter = ((AbstractArrowEntity) arrow).func_234616_v_();
                if (!(shooter instanceof PlayerEntity)) {
                    return;
                }

                PlayerEntity player = (PlayerEntity) shooter;
                Player = player;

                World world = player.world;
                MinecraftServer server = world.getServer();
                ServerWorld serverworld = server.getWorld(World.field_234918_g_);
                Serverworld = serverworld;

                HomingArrow harrow = new HomingArrow(EntityType.ARROW, world);
                Arrow = harrow;

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AIMING.get(), stack);
                int plvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                level = lvl;
                plevel = plvl;
                if (lvl != 0 && fired == true) {
                    fired = false;
                    Fired = true;
                    serverworld.addEntity(harrow);
                    harrow.setPosition(arrow.getPosX(),arrow.getPosY(),arrow.getPosZ());
                    harrow.setMotion(arrow.getMotion());
                    arrow.remove();
                }
            }
        }

        @SubscribeEvent
        public static void arrowHoming (TickEvent.ServerTickEvent event) {
            if (Config.Common.aiming.get()) {
                if (Player == null) {
                    return;
                }

                if (Arrow == null) {
                    return;
                }

                World world = Player.world;
                Entity arrow = world.getEntityByID(Arrow.getEntityId());

                if (arrow == null) {
                    return;
                }

                double speed = arrow.getMotion().length();
                arrow.setNoGravity(true);
                spawnParticles(Serverworld, arrow.getPositionVec());

                Vector3d min = new Vector3d(arrow.getPosX() - 5, arrow.getPosY() - 5, arrow.getPosZ() - 5);
                Vector3d max = new Vector3d(arrow.getPosX() + 5, arrow.getPosY() + 5, arrow.getPosZ() + 5);
                AxisAlignedBB AABB = new AxisAlignedBB(min, max);

                List<Entity> list = world.getEntitiesWithinAABB(Entity.class, AABB, Entity::isLiving);
                if (list.contains(Player)) {
                    list.remove(Player);
                }
                List<LivingEntity> elist = new ArrayList<>();
                list.forEach(entity -> elist.add((LivingEntity) entity));
                LivingEntity target = world.getClosestEntity(elist, EntityPredicate.DEFAULT, null, Arrow.getPosX(), Arrow.getPosY(), Arrow.getPosZ());

                if (target == null) {
                    return;
                }

                Target = target;

                Vector3d apos = new Vector3d(arrow.getPosX(), arrow.getPosY(), arrow.getPosZ());
                Vector3d tpos = new Vector3d(target.getPosX(), target.getPosY(), target.getPosZ());

                Vector3d v = tpos.subtract(apos).normalize();
                v.scale(speed);

                arrow.setMotion(v.add(new Vector3d(0, 0.3D, 0)));

                if (arrow.getDistanceSq(tpos) < 5) {
                    arrow.setPosition(tpos.getX(), tpos.getY(), tpos.getZ());
                    target.performHurtAnimation();
                    arrow.remove();

                    ItemStack stack = Player.getHeldItem(Hand.MAIN_HAND);

                    if (stack.getItem() == Items.CROSSBOW && Fired) {
                        Target.setHealth(Target.getHealth() - 9);
                        Fired = false;
                    }
                    if (stack.getItem() == Items.BOW && Fired) {
                        Fired = false;
                        if (plevel == 0) {
                            if (charge < 10) {
                                Target.setHealth(Target.getHealth() - 1);
                            }
                            if (charge >= 10 && charge < 20) {
                                Target.setHealth(Target.getHealth() - 6);
                            }
                            if (charge >= 20) {
                                Target.setHealth(Target.getHealth() - 10);
                            }
                        }
                        if (plevel == 1) {
                            if (charge < 10) {
                                Target.setHealth(Target.getHealth() - 2);
                            }
                            if (charge >= 10 && charge < 20) {
                                Target.setHealth(Target.getHealth() - 9);
                            }
                            if (charge >= 20) {
                                Target.setHealth(Target.getHealth() - 15);
                            }
                        }
                        if (plevel == 2) {
                            if (charge < 10) {
                                Target.setHealth(Target.getHealth() - 2);
                            }
                            if (charge >= 10 && charge < 20) {
                                Target.setHealth(Target.getHealth() - 11);
                            }
                            if (charge >= 20) {
                                Target.setHealth(Target.getHealth() - 18);
                            }
                        }
                        if (plevel == 3) {
                            if (charge < 10) {
                                Target.setHealth(Target.getHealth() - 2);
                            }
                            if (charge >= 10 && charge < 20) {
                                Target.setHealth(Target.getHealth() - 12);
                            }
                            if (charge >= 20) {
                                Target.setHealth(Target.getHealth() - 20);
                            }
                        }
                        if (plevel == 4) {
                            if (charge < 10) {
                                Target.setHealth(Target.getHealth() - 3);
                            }
                            if (charge >= 10 && charge < 20) {
                                Target.setHealth(Target.getHealth() - 14);
                            }
                            if (charge >= 20) {
                                Target.setHealth(Target.getHealth() - 23);
                            }
                        }
                        if (plevel == 5) {
                            if (charge < 10) {
                                Target.setHealth(Target.getHealth() - 4);
                            }
                            if (charge >= 10 && charge < 20) {
                                Target.setHealth(Target.getHealth() - 15);
                            }
                            if (charge >= 20) {
                                Target.setHealth(Target.getHealth() - 25);
                            }
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void arrowShot(ArrowLooseEvent event) {
            if (Config.Common.aiming.get()) {
                fired = true;
                charge = event.getCharge();
            }
        }

        @SubscribeEvent
        public static void entityDamaged(LivingHurtEvent event) {
            if (Config.Common.aiming.get()) {
                Entity source = event.getSource().getTrueSource();

                if (!(source instanceof PlayerEntity)) {
                    return;
                }

                PlayerEntity player = (PlayerEntity) source;
                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AIMING.get(), stack);

                if (lvl == 1) {
                    if (stack.getItem() == Items.CROSSBOW) {
                        event.setAmount(0);
                    }
                    if (stack.getItem() == Items.BOW) {
                        event.setAmount(0);
                    }
                }
            }
        }
        @OnlyIn(Dist.CLIENT)
        private static void spawnParticles(ServerWorld serverworld, Vector3d pos) {
            if(hue >= 255) {hue = 0;} else {hue++;}
            final java.awt.Color color = Color.getHSBColor(hue/255F, 1.0F, 1.0F);
            serverworld.spawnParticle(new RedstoneParticleData(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, 1.0f), pos.x, pos.y, pos.z, 1, 0, 0, 0, 100);
        }
    }
}

