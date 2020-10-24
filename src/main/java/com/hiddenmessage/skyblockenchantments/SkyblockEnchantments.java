package com.hiddenmessage.skyblockenchantments;

import com.hiddenmessage.skyblockenchantments.config.Config;
import com.hiddenmessage.skyblockenchantments.init.EnchantmentInit;
import com.hiddenmessage.skyblockenchantments.init.BlockInit;

import com.hiddenmessage.skyblockenchantments.init.EntityInit;
import net.minecraft.item.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("deprecation")
@Mod("skyblockenchantments")
@Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Bus.MOD)
public class SkyblockEnchantments {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "skyblockenchantments";
    public static SkyblockEnchantments instance;

    public SkyblockEnchantments()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        BlockInit.BLOCKS.register(modEventBus);
        EnchantmentInit.ENCHANTMENTS.register(modEventBus);
        EntityInit.ENTITY.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, "SkyblockEnchantments.toml");

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();

        BlockInit.BLOCKS.getEntries().stream()
                .map(RegistryObject::get).forEach(block -> {
            final Item.Properties properties = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });

        LOGGER.debug("Registered BlockItems!");
    }
}
