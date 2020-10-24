package com.hiddenmessage.skyblockenchantments.enchantments;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.sun.org.apache.xpath.internal.operations.String;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkyblockEnchantments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Notice {

    @SubscribeEvent
    public static void Notice (EntityJoinWorldEvent event) {

        if(!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) event.getEntity();
        player.sendMessage(new StringTextComponent("Skyblock Enchantments v1.5 (1.16.3), Please be aware that Aiming occassionally bugs out in this version!"), player.getUniqueID());
    }
}
