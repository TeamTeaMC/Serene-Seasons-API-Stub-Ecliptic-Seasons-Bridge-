package com.teamtea.eclipticseasons_serene_compatibility.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sereneseasons.init.ModConfig;
import sereneseasons.season.SeasonHooks;

@Mixin({Biome.class})
public abstract class MixinBiome {

    @Inject(
            method = {"shouldSnow"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void eclipticseasons_serene_compatibility$onShouldSnow(LevelReader p_level, BlockPos p_pos, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.seasons.generateSnowAndIce)
            cir.setReturnValue(SeasonHooks.shouldSnowHook((Biome) (Object) this, p_level, p_pos, p_level.getSeaLevel()));
    }

    @WrapOperation(
            method = {"shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;I)Z"
            )
    )
    public boolean eclipticseasons_serene_compatibility$onShouldFreeze_warmEnoughToRain(Biome instance, BlockPos p_pos, int seaLevel, Operation<Boolean> original,
                                                                                        @Local(argsOnly = true) LevelReader levelReader) {
        if (ModConfig.seasons.generateSnowAndIce)
            return SeasonHooks.shouldFreezeWarmEnoughToRainHook(instance, p_pos, seaLevel, levelReader);
        else
            return original.call(instance, p_pos, seaLevel);
    }
}
