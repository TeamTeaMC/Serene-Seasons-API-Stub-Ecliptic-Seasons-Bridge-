package com.teamtea.eclipticseasons_serene_compatibility.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sereneseasons.init.ModConfig;
import sereneseasons.season.SeasonHooks;

@Mixin(value = {Biome.class}, priority = 1500)
public abstract class MixinBiome {

    // @WrapOperation(
    //         method = {"shouldSnow"},
    //         at = {@At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;")}
    // )
    // public Biome.Precipitation eclipticseasons_serene_compatibility$onShouldSnow(Biome instance, BlockPos pos, int seaLevel, Operation<Biome.Precipitation> original,
    //                                                                              @Local(argsOnly = true) LevelReader levelReader) {
    //     if (ModConfig.seasons.generateSnowAndIce)
    //         return (SeasonHooks.shouldSnowHook(instance, levelReader, pos, seaLevel) ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN);
    //     return original.call(instance, pos, seaLevel);
    // }
    //
    // @WrapOperation(
    //         method = {"shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z"},
    //         at = @At(
    //                 value = "INVOKE",
    //                 target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;I)Z"
    //         )
    // )
    // public boolean eclipticseasons_serene_compatibility$onShouldFreeze_warmEnoughToRain(Biome instance, BlockPos p_pos, int seaLevel, Operation<Boolean> original,
    //                                                                                     @Local(argsOnly = true) LevelReader levelReader) {
    //     if (ModConfig.seasons.generateSnowAndIce)
    //         return !SeasonHooks.warmEnoughToRainSeasonal(levelReader, p_pos, seaLevel);
    //     else
    //         return original.call(instance, p_pos, seaLevel);
    // }
}
