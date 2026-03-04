/*
 * ---------------------------------------------------------------------------
 * CREDIT: This is a compatibility stub for SeasonsConfig.
 * Designed by TeamTea to reroute config queries to Ecliptic Seasons.
 * ---------------------------------------------------------------------------
 */

package sereneseasons.config;

import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import com.teamtea.eclipticseasons.config.ClientConfig;
import com.teamtea.eclipticseasons.config.CommonConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeasonsConfig {

    public boolean generateSnowAndIce = true;
    public boolean changeWeatherFrequency = true;
    public int dayDuration = 24000;
    public int subSeasonDuration = 8;
    public boolean changeGrassColor = true;
    public boolean changeFoliageColor = true;
    public boolean changeBirchColor = true;
    public List<String> whitelistedDimensions = new ArrayList<>();

    public SeasonsConfig() {
    }

    public void load() {
    }

    public boolean isDimensionWhitelisted(ResourceKey<Level> dimension) {
        return (CommonConfig.Season.validDimensions.get().contains(dimension.location().toString()));
    }

    private static final Map<Season.SubSeason, SeasonProperties> DEFAULT_SEASON_PROPERTIES =
            Arrays.stream(new SeasonProperties[]{

                    // Winter
                    new SeasonProperties(Season.SubSeason.EARLY_WINTER, 0.0F, 0),
                    new SeasonProperties(Season.SubSeason.MID_WINTER, 0.0F, 0),
                    new SeasonProperties(Season.SubSeason.LATE_WINTER, 0.0F, 0),

                    // Spring
                    new SeasonProperties(Season.SubSeason.EARLY_SPRING, 6.25F, 1),
                    new SeasonProperties(Season.SubSeason.MID_SPRING, 8.33F, 1),
                    new SeasonProperties(Season.SubSeason.LATE_SPRING, 12.5F, 1),

                    // Summer
                    new SeasonProperties(Season.SubSeason.EARLY_SUMMER, 25.0F, 1),
                    new SeasonProperties(Season.SubSeason.MID_SUMMER, 25.0F, 1),
                    new SeasonProperties(Season.SubSeason.LATE_SUMMER, 25.0F, 1),

                    // Autumn
                    new SeasonProperties(Season.SubSeason.EARLY_AUTUMN, 12.5F, 1),
                    new SeasonProperties(Season.SubSeason.MID_AUTUMN, 8.33F, 1),
                    new SeasonProperties(Season.SubSeason.LATE_AUTUMN, 6.25F, 1),

            }).collect(Collectors.toMap(
                    SeasonProperties::subSeason,
                    p -> p
            ));

    public SeasonProperties getSeasonProperties(Season.SubSeason season) {
        //SolarTerm solarTerm = SolarTerm.collectValidValues()[season.ordinal() * 2];
        return DEFAULT_SEASON_PROPERTIES.get(season);
    }

    public static record SeasonProperties(Season.SubSeason subSeason, float meltChance, int meltRolls,
                                          float biomeTempAdjustment, int minRainTime, int maxRainTime,
                                          int minThunderTime, int maxThunderTime) {

        public SeasonProperties(Season.SubSeason subSeason, float meltChance, int meltRolls) {
            this(subSeason, meltChance, meltRolls,
                    0.0F,
                    12000, 36000,
                    12000, 36000);
        }

        public boolean canRain() {
            return minRainTime != -1;
        }

        public boolean canThunder() {
            return minThunderTime != -1;
        }
    }
}