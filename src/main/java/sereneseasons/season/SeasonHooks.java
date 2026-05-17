/*
 * ---------------------------------------------------------------------------
 * CREDIT: This class is a re-implementation of SeasonHooks.
 * It acts as a bridge to redirect Minecraft environment queries
 * (like snowing/freezing) to the Ecliptic Seasons engine.
 * ---------------------------------------------------------------------------
 */

package sereneseasons.season;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.api.constant.climate.ISnowTerm;
import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.api.util.SolarUtil;
import com.teamtea.eclipticseasons.common.core.biome.BiomeClimateManager;
import com.teamtea.eclipticseasons.common.core.biome.WeatherManager;
import com.teamtea.eclipticseasons.common.core.map.MapChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.state.BlockState;
import sereneseasons.api.season.Season;
import sereneseasons.init.ModConfig;

public class SeasonHooks {

    private static LevelReader getLevel(LevelReader levelReader) {
        if (levelReader instanceof Level level) return level;
        if (levelReader instanceof WorldGenLevel worldGenLevel) return worldGenLevel.getLevel();
        return null;
    }


    public static boolean shouldSnowHook(Biome biome, LevelReader levelReader, BlockPos pos, int seaLevel) {
        if (getLevel(levelReader) instanceof Level level) return shouldSnow(pos, level);
        return biome.shouldSnow(levelReader, pos);
    }

    public static boolean shouldFreezeWarmEnoughToRainHook(Biome biome, BlockPos pos, int seaLevel, LevelReader levelReader) {
        return ModConfig.seasons.generateSnowAndIce && !warmEnoughToRainSeasonal(levelReader, pos, seaLevel);
    }

    public static boolean isRainingAtHook(Level level, BlockPos position) {
        return EclipticSeasonsApi.getInstance().isRainingOrSnowing(level, position);
    }

    public static Biome.Precipitation getPrecipitationAtTickIceAndSnowHook(LevelReader level, Biome biome, BlockPos pos, int seaLevel) {
        if (getLevel(level) instanceof Level level1)
            return EclipticSeasonsApi.getInstance().getPrecipitationAt(level1, pos);
        if (!biome.hasPrecipitation()) return Precipitation.NONE;
        return ModConfig.seasons.generateSnowAndIce && coldEnoughToSnowSeasonal(level, pos, seaLevel) ? Precipitation.SNOW : Precipitation.RAIN;
    }


    public static boolean coldEnoughToSnowSeasonal(LevelReader level, BlockPos pos, int seaLevel) {
        return !warmEnoughToRainSeasonal(level, pos, seaLevel);
    }


    public static boolean coldEnoughToSnowSeasonal(LevelReader level, Holder<Biome> biome, BlockPos pos) {
        return !warmEnoughToRainSeasonal(level, biome, pos, level.getSeaLevel());
    }

    public static boolean warmEnoughToRainSeasonal(LevelReader level, BlockPos pos, int seaLevel) {
        return warmEnoughToRainSeasonal(level, null, pos, seaLevel);
    }


    public static boolean warmEnoughToRainSeasonal(LevelReader level, Holder<Biome> biome, BlockPos pos, int seaLevel) {
        if (getLevel(level) instanceof Level level1) {
            return shouldSnow(pos, level1);
        }
        return getBiomeTemperature(level, biome, pos, seaLevel) >= 0.15F;
    }

    private static boolean shouldSnow(BlockPos pos, Level level1) {
        Holder<Biome> biome;
        biome = MapChecker.getSurfaceBiome(level1, pos);
        if (WeatherManager.hasNonePrecipitation(biome.value())) return false;
        boolean server = level1 instanceof ServerLevel;
        ISnowTerm snowTerm = SolarUtil.getSnowTerm(biome.value(), server, EclipticUtil.getSnowTempChange(level1));
        SolarTerm solarTerm = EclipticSeasonsApi.getInstance().getSolarTerm(level1);
        return snowTerm.maySnow(solarTerm, biome.value(), pos, server);
    }

    public static float getBiomeTemperature(LevelReader level, Holder<Biome> biome, BlockPos pos, int seaLevel) {
        if (getLevel(level) instanceof Level level1)
            return getBiomeTemperature(level1, biome, pos, seaLevel);
        return biome.value().getTemperature(pos, level.getSeaLevel());
    }

    public static float getBiomeTemperature(Level level, Holder<Biome> biome, BlockPos pos, int seaLevel) {
        float temperatureFloat = EclipticUtil.getTemperatureFloat(level, biome.value(), pos);
        if (shouldSnow(pos, level)) {
            return Math.min(temperatureFloat, 0.15f - 0.00001f);
        }
        return temperatureFloat;
    }


    public static float getBiomeTemperatureInSeason(Season.SubSeason subSeason, Holder<Biome> biome, BlockPos pos, int seaLevel) {
        return BiomeClimateManager.getBiomeClimateSettings(biome.value(), true).getTemperature(
                SolarTerm.collectValidValues()[subSeason.ordinal() * 2]
        );
    }

    public static boolean hasPrecipitationSeasonal(Level level, Holder<Biome> biome) {
        return WeatherManager.getPrecipitationAt(level, biome.value(), BlockPos.ZERO) != Precipitation.NONE;
    }

    public static Biome.Precipitation getPrecipitationAtSeasonal(Level level, Holder<Biome> biome, BlockPos pos, int seaLevel) {
        return EclipticSeasonsApi.getInstance().getPrecipitationAt(level, pos);
    }
}