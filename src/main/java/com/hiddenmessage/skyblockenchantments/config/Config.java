package com.hiddenmessage.skyblockenchantments.config;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static class Common {

        public static ForgeConfigSpec.BooleanValue telekinesis;
        public static ForgeConfigSpec.BooleanValue aiming;
        public static ForgeConfigSpec.BooleanValue smelting_touch;
        public static ForgeConfigSpec.BooleanValue ender_slayer;
        public static ForgeConfigSpec.BooleanValue experience;
        public static ForgeConfigSpec.BooleanValue fireworks;
        public static ForgeConfigSpec.BooleanValue growth;
        public static ForgeConfigSpec.BooleanValue harvesting;
        public static ForgeConfigSpec.BooleanValue leaf_shatter;
        public static ForgeConfigSpec.BooleanValue life_steal;
        public static ForgeConfigSpec.BooleanValue magma_walker;
        public static ForgeConfigSpec.BooleanValue might;
        public static ForgeConfigSpec.BooleanValue rainbow;
        public static ForgeConfigSpec.BooleanValue replenish;
        public static ForgeConfigSpec.BooleanValue speed;
        public static ForgeConfigSpec.BooleanValue wood_shatter;
        public static ForgeConfigSpec.BooleanValue ore_shatter;
        public static ForgeConfigSpec.BooleanValue destroyer;
        public static ForgeConfigSpec.BooleanValue sniper;
        public static ForgeConfigSpec.BooleanValue tracker;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Enable or Disable Enchantments");

            builder.push("Telekinesis");
            telekinesis = builder.define("Enable", true);
            builder.pop();

            builder.push("Aiming");
            aiming = builder.define("Enable", true);
            builder.pop();

            builder.push("Smelting-Touch");
            smelting_touch = builder.define("Enable", true);
            builder.pop();

            builder.push("Ender-Slayer");
            ender_slayer = builder.define("Enable", true);
            builder.pop();

            builder.push("Experience");
            experience = builder.define("Enable", true);
            builder.pop();

            builder.push("Fireworks");
            fireworks = builder.define("Enable", true);
            builder.pop();

            builder.push("Growth");
            growth = builder.define("Enable", true);
            builder.pop();

            builder.push("Harvesting");
            harvesting = builder.define("Enable", true);
            builder.pop();

            builder.push("Leaf-Shatter");
            leaf_shatter = builder.define("Enable", true);
            builder.pop();

            builder.push("Life-Steal");
            life_steal = builder.define("Enable", true);
            builder.pop();

            builder.push("Magma-Walker");
            magma_walker = builder.define("Enable", true);
            builder.pop();

            builder.push("Might");
            might = builder.define("Enable", true);
            builder.pop();

            builder.push("Rainbow");
            rainbow = builder.define("Enable", true);
            builder.pop();

            builder.push("Replenish");
            replenish = builder.define("Enable", true);
            builder.pop();

            builder.push("Speed");
            speed = builder.define("Enable", true);
            builder.pop();

            builder.push("Ore-Shatter");
            ore_shatter = builder.define("Enable", true);
            builder.pop();

            builder.push("Wood-Shatter");
            wood_shatter = builder.define("Enable", true);
            builder.pop();

            builder.push("Destroyer");
            destroyer = builder.define("Enable", true);
            builder.pop();

            builder.push("Sniper");
            sniper = builder.define("Enable", true);
            builder.pop();

            builder.push("Tracker");
            sniper = builder.define("Enable", true);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading event) {

    }
}

