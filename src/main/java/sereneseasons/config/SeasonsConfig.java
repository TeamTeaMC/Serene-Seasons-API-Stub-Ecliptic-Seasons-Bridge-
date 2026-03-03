/*
 * ---------------------------------------------------------------------------
 * CREDIT: This is a compatibility stub for SeasonsConfig.
 * Designed by TeamTea to reroute config queries to Ecliptic Seasons.
 * ---------------------------------------------------------------------------
 */

package sereneseasons.config;

import com.teamtea.eclipticseasons.config.CommonConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;

import java.util.ArrayList;
import java.util.List;

public class SeasonsConfig {

    public boolean generateSnowAndIce = true;
    public boolean changeWeatherFrequency = true;
    public int dayDuration = 24000;
    public int subSeasonDuration = 8;
    public boolean changeGrassColor = true;
    public boolean changeFoliageColor = true;
    public boolean changeBirchColor = true;
    public List<String> whitelistedDimensions=new ArrayList<>();

    public SeasonsConfig() {
    }

    public void load() {
    }

    public boolean isDimensionWhitelisted(ResourceKey<Level> dimension) {
        return (CommonConfig.Season.validDimensions.get().contains(dimension.location().toString()));
    }

    public SeasonProperties getSeasonProperties(Season.SubSeason season) {
        return new SeasonProperties(
                season,
                100.0F,
                1,
                0.0F,
                12000, 36000,
                12000, 36000,
                -1,
                -1,
                -1,
                -1,
                -1
        );
    }

    public static record SeasonProperties(
            Season.SubSeason subSeason, float meltChance, int meltRolls,
            float biomeTempAdjustment, int minRainTime, int maxRainTime,
            int minThunderTime, int maxThunderTime, int grassColour,
            float grassSaturation, int foliageColour, float foliageSaturation,
            int birchColor
    ) {
        public boolean canRain() {
            return minRainTime != -1;
        }

        public boolean canThunder() {
            return minThunderTime != -1;
        }
    }
}