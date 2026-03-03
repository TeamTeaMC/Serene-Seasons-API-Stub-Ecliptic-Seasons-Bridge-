package sereneseasons.init;

import sereneseasons.config.SeasonsConfig;

public class ModConfig {
    public static SeasonsConfig seasons;

    public static void init() {
        seasons = new SeasonsConfig();
    }
}
