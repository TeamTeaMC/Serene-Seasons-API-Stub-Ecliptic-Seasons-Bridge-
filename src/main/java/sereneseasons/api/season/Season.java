/*
 * ============================================================================
 * CREDIT NOTICE
 * ============================================================================
 * This class is a RE-IMPLEMENTATION of the Serene Seasons API for the
 * purpose of INTEROPERABILITY and COMPATIBILITY.
 *
 * 1. OWNERSHIP:
 * The original API design, structure, and naming conventions are
 * property of the Serene Seasons team (Adubbz, GameOnMedia, etc.).
 *
 * 2. INDEPENDENT WORK:
 * All functional logic, data processing, and internal method
 * implementations have been independently developed by TeamTea.
 * No proprietary binary or source code from the original mod
 * has been used in this file.
 *
 * 3. INTENT:
 * This file exists solely to allow mods depending on Serene Seasons
 * to function correctly when Ecliptic Seasons is installed.
 * ============================================================================
 */

package sereneseasons.api.season;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Functional stub for compatibility purposes.
 * Redirects API calls to the Ecliptic Seasons calculation engine.
 */
public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER;

    public enum SubSeason implements StringRepresentable {
        EARLY_SPRING(Season.SPRING), MID_SPRING(Season.SPRING), LATE_SPRING(Season.SPRING),
        EARLY_SUMMER(Season.SUMMER), MID_SUMMER(Season.SUMMER), LATE_SUMMER(Season.SUMMER),
        EARLY_AUTUMN(Season.AUTUMN), MID_AUTUMN(Season.AUTUMN), LATE_AUTUMN(Season.AUTUMN),
        EARLY_WINTER(Season.WINTER), MID_WINTER(Season.WINTER), LATE_WINTER(Season.WINTER);

        public static final SubSeason[] VALUES = values();
        private final Season season;

        SubSeason(Season season) {
            this.season = season;
        }

        public Season getSeason() {
            return this.season;
        }

        //public int getGrassOverlay() {
        //    return TemperateSolarTermColors.collectValues()[ordinal() * 2].getGrassColor();
        //}
        //
        //public float getGrassSaturationMultiplier() {
        //    return TemperateSolarTermColors.collectValues()[ordinal() * 2].getMix();
        //}
        //
        //public int getFoliageOverlay() {
        //    return TemperateSolarTermColors.collectValues()[ordinal() * 2].getLeaveColor();
        //}
        //
        //public float getFoliageSaturationMultiplier() {
        //    return TemperateSolarTermColors.collectValues()[ordinal() * 2].getMix();
        //}
        //
        //public int getBirchColor() {
        //    return BirchLeavesColor.collectValues()[ordinal() * 2].getColor();
        //}

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum TropicalSeason {
        EARLY_DRY, MID_DRY, LATE_DRY, EARLY_WET, MID_WET, LATE_WET;

        public static final TropicalSeason[] VALUES = values();
        //
        //public int getGrassOverlay() {
        //    return HotSolarTermColors.collectValues()[ordinal() * 4].getGrassColor();
        //}
        //
        //public int getFoliageOverlay() {
        //    return HotSolarTermColors.collectValues()[ordinal() * 4].getGrassColor();
        //}
        //
        //public int getBirchColor() {
        //    return BirchLeavesColor.collectValues()[ordinal() * 4].getColor();
        //}
    }
}