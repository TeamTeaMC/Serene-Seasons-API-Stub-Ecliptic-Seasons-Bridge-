package sereneseasons.season;

import net.minecraft.world.level.Level;

import java.util.HashMap;

// Required by AllTheLeaks
@Deprecated(forRemoval = true)
public class SeasonHandler {
    public static final HashMap<Level, Long> lastDayTimes = new HashMap();
    public static final HashMap<Level, Integer> updateTicks = new HashMap();
    //public static final HashMap<ResourceKey<Level>, Integer> prevServerSeasonCycleTicks = new HashMap();
}
