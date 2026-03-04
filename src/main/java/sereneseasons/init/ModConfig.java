package sereneseasons.init;

import sereneseasons.config.FertilityConfig;
import sereneseasons.config.SeasonsConfig;

public class ModConfig {
    public static SeasonsConfig seasons;
    public static FertilityConfig fertility;

    public static void init() {
        seasons = new SeasonsConfig();
        fertility = new FertilityConfig();
    }
}
