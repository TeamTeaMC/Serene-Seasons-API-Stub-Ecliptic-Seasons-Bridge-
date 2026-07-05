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
import sereneseasons.api.season.Season;

public class SeasonHooks {

    private static LevelReader getLevel(LevelReader levelReader) {
        if (levelReader instanceof Level level) return level;
        if (levelReader instanceof WorldGenLevel worldGenLevel) return worldGenLevel.getLevel();
        return null;
    }


    public static boolean shouldSnowHook(Biome biome, LevelReader levelReader, BlockPos pos) {
        return getLevel(levelReader) instanceof Level level ? shouldSnow(pos, level) :
                biome.shouldSnow(levelReader, pos);
    }

    public static boolean shouldFreezeWarmEnoughToRainHook(Biome biome, BlockPos pos, LevelReader levelReader) {
        return !warmEnoughToRainSeasonal(levelReader, pos);
    }

    public static boolean isRainingAtHook(Level level, BlockPos position) {
        return EclipticSeasonsApi.getInstance().isRainingOrSnowing(level, position);
    }

    public static Biome.Precipitation getPrecipitationAtTickIceAndSnowHook(LevelReader level, Biome biome, BlockPos pos) {
        if (getLevel(level) instanceof Level level1)
            return EclipticSeasonsApi.getInstance().getPrecipitationAt(level1, pos);
        if (!biome.hasPrecipitation()) return Precipitation.NONE;
        return coldEnoughToSnowSeasonal(level, pos) ? Precipitation.SNOW : Precipitation.RAIN;
    }

    public static boolean coldEnoughToSnowSeasonal(LevelReader level, Holder<Biome> biome, BlockPos pos) {
        return !warmEnoughToRainSeasonal(level, biome, pos);
    }

    public static boolean coldEnoughToSnowSeasonal(LevelReader level, BlockPos pos) {
        return !warmEnoughToRainSeasonal(level, pos);
    }

    public static boolean warmEnoughToRainSeasonal(LevelReader level, BlockPos pos) {
        return warmEnoughToRainSeasonal(level, null, pos);
    }


    public static boolean warmEnoughToRainSeasonal(LevelReader level, Holder<Biome> biome, BlockPos pos) {
        if (getLevel(level) instanceof Level level1) {
            return shouldSnow(pos, level1);
        }
        return getBiomeTemperature(level, biome, pos) >= 0.15F;
    }

    private static boolean shouldSnow(BlockPos pos, Level level1) {
        Holder<Biome> biome;
        biome = MapChecker.getSurfaceBiome(level1, pos);
        if (WeatherManager.hasNonePrecipitation(biome.value())) return false;
        boolean server = level1 instanceof ServerLevel;
        ISnowTerm snowTerm = SolarTerm.getSnowTerm(biome.value(), server, EclipticUtil.getSnowTempChange(level1));
        SolarTerm solarTerm = EclipticSeasonsApi.getInstance().getSolarTerm(level1);
        return snowTerm.maySnow(solarTerm, biome.value(), pos, server);
    }

    public static float getBiomeTemperature(LevelReader level, Holder<Biome> biome, BlockPos pos) {
        if (getLevel(level) instanceof Level level1)
            return getBiomeTemperature(level1, biome, pos);
        return biome.value().getTemperature(pos);
    }

    // Append
    public static float getBiomeTemperature(Level level, Holder<Biome> biome, BlockPos pos) {
        float temperatureFloat = EclipticUtil.getTemperatureFloat(level, biome.value(), pos);
        if (shouldSnow(pos, level)) {
            return Math.min(temperatureFloat, 0.15f - 0.00001f);
        }
        return Math.max(temperatureFloat, 0.15f + 0.00001f);
    }


    public static float getBiomeTemperatureInSeason(Season.SubSeason subSeason, Holder<Biome> biome, BlockPos pos) {
        return BiomeClimateManager.getBiomeClimateSettings(biome.value(), true).getTemperature(
                SolarTerm.collectValidValues()[subSeason.ordinal() * 2]
        );
    }

    public static boolean hasPrecipitationSeasonal(Level level, Holder<Biome> biome) {
        return WeatherManager.getPrecipitationAt(level, biome.value(), BlockPos.ZERO) != Precipitation.NONE;
    }
}