package com.hiddenmessage.skyblockenchantments.init;

import com.hiddenmessage.skyblockenchantments.SkyblockEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SkyblockEnchantments.MOD_ID);

    public static final RegistryObject<Block> BURNT_MAGMA_BLOCK = BLOCKS.register("burnt_magma_block", () -> new Block(
            Block.Properties.create(Material.IRON)
                    .hardnessAndResistance(0.5f, 0.5f)
                    .sound(SoundType.STONE)));

}
