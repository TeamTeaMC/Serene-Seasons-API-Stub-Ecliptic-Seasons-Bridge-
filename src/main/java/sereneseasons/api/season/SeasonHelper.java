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

import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import com.teamtea.eclipticseasons_serene_compatibility.api.EclipticSeasonTime;

import java.util.IdentityHashMap;
import java.util.Map;

public class SeasonHelper {
   public static final Map<Level,ISeasonState> SEASON_STATE_HASH_MAP =new IdentityHashMap<>();

    public static ISeasonState getSeasonState(Level level) {
        return SEASON_STATE_HASH_MAP.getOrDefault(
              level,EclipticSeasonTime.ZERO
        );
    }

    public static boolean usesTropicalSeasons(Holder<Biome> biome) {
        return false;
    }

    public interface ISeasonDataProvider {
        ISeasonState getServerSeasonState(Level var1);

        ISeasonState getClientSeasonState(Level var1);

        boolean usesTropicalSeasons(Holder<Biome> var1);
    }
}
