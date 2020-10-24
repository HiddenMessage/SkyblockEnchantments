package com.hiddenmessage.skyblockenchantments.init;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import com.hiddenmessage.skyblockenchantments.entity.HomingArrow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(
            ForgeRegistries.ENTITIES, SkyblockEnchantments.MOD_ID);

    public static final RegistryObject<EntityType<HomingArrow>> HOMING_ARROW = ENTITY.register("homing_arrow", () -> EntityType.Builder.create(HomingArrow::new, EntityClassification.MISC)
            .size(0.5f, 0.5f)
            .build("homing_arrow"));
}
