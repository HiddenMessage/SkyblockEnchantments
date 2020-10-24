package com.hiddenmessage.skyblockenchantments.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.Random;

public class HomingArrow extends AbstractArrowEntity {

    public HomingArrow(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    private static int hue;

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }

    @Override
    public void tick() {
        if(this.ticksExisted > 60) {
            this.remove();
        }
        if(this.inGround) {
            this.remove();
        }
        this.setGlowing(true);
    }
}
