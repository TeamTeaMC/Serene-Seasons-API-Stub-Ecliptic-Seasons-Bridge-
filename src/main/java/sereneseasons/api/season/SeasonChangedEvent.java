/*
 * ---------------------------------------------------------------------------
 * CREDIT: This class is a re-implementation of SeasonChangedEvent API.
 * Purpose: Allows mods to listen for "Serene Seasons" events, which are
 * actually triggered by Ecliptic Seasons' solar term changes.
 * ---------------------------------------------------------------------------
 */

package sereneseasons.api.season;

import net.minecraft.world.level.Level;
import glitchcore.event.Event;

public class SeasonChangedEvent<T> extends Event {
    private final Level level;
    private final T prevSeason;
    private final T newSeason;

    protected SeasonChangedEvent(Level level, T prevSeason, T newSeason) {
        this.level = level;
        this.prevSeason = prevSeason;
        this.newSeason = newSeason;
    }

    public Level getLevel() { return this.level; }
    public T getPrevSeason() { return this.prevSeason; }
    public T getNewSeason() { return this.newSeason; }

    public static class Standard extends SeasonChangedEvent<Season.SubSeason> {
        public Standard(Level level, Season.SubSeason prevSeason, Season.SubSeason newSeason) {
            super(level, prevSeason, newSeason);
        }
    }

    public static class Tropical extends SeasonChangedEvent<Season.TropicalSeason> {
        public Tropical(Level level, Season.TropicalSeason prevSeason, Season.TropicalSeason newSeason) {
            super(level, prevSeason, newSeason);
        }
    }
}