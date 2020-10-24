package com.hiddenmessage.skyblockenchantments.enchantments;

import com.google.common.collect.Lists;
import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

public class Fireworks extends Enchantment {

    public Fireworks(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
        return 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class fireworksLogic {


        @SubscribeEvent
        public static void entityDamaged(LivingHurtEvent event) {

            if (Config.Common.fireworks.get()) {

                Entity source = event.getSource().getTrueSource();
                LivingEntity target = (LivingEntity) event.getEntity();

                if (!(source instanceof PlayerEntity)) {
                    return;
                }

                PlayerEntity player = (PlayerEntity) source;
                World world = player.world;

                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.FIREWORKS.get(), stack);



                int[] color = new int[11];

                color[0] = 11743532; //Red
                color[1] = 15435844; //Orange
                color[2] = 14602026; //Yellow
                color[3] = 4312372; //Lime
                color[4] = 3887386; //Green
                color[5] = 6719955; //Light Blue
                color[6] = 2651799; //Cyan
                color[7] = 2437522; //Blue
                color[8] = 8073150; //Purple
                color[9] = 12801229; //Magenta
                color[10] = 14188952; //Pink


                int a = new Random().nextInt(color.length);
                int color1 = color[a];
                int b = new Random().nextInt(color.length);
                int color2 = color[b];
                int c = new Random().nextInt(color.length);
                int color3 = color[c];
                int d = new Random().nextInt(color.length);
                int color4 = color[d];


                Item star = Items.FIREWORK_ROCKET;
                ItemStack poof = new ItemStack((star));
                CompoundNBT nbt = new CompoundNBT();
                //CompoundNBT type = new CompoundNBT();
                CompoundNBT colors = new CompoundNBT();
                //CompoundNBT trail = new CompoundNBT();
                //CompoundNBT flight = new CompoundNBT();
                //CompoundNBT flicker = new CompoundNBT();
                CompoundNBT explosion = new CompoundNBT();

                ListNBT list = new ListNBT();
                List<Integer> clist = Lists.newArrayList();

                clist.add(color1);
                clist.add(color2);
                clist.add(color3);
                clist.add(color4);
                colors.putIntArray("Colors", clist);


                //flight.putInt("Flight", 1);
                //type.putInt("Type", 1);
                //flicker.putInt("Flicker", 1);
                //trail.putInt("Trail", 0);

                //list.add(type);
                //list.add(trail);
                //list.add(flicker);
                list.add(colors);

                explosion.put("Explosions", list);
                //explosion.merge(flight);
                nbt.put("Fireworks", explosion);
                poof.setTag(nbt);


                FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(world, target.getPosX(), target.getPosY() + 1, target.getPosZ(), poof);


                if (lvl == 1) {
                    target.world.addEntity(fireworkrocketentity);
                }
            }
        }
    }
}
