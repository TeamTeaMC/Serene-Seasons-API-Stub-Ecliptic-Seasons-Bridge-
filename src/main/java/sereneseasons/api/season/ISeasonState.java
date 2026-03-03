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

public interface ISeasonState {
    int getDayDuration();

    int getSubSeasonDuration();

    int getSeasonDuration();

    int getCycleDuration();

    int getSeasonCycleTicks();

    int getDay();

    Season.SubSeason getSubSeason();

    Season getSeason();

    Season.TropicalSeason getTropicalSeason();
}
