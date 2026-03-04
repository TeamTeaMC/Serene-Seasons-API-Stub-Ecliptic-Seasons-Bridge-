package sereneseasons.config;

import com.teamtea.eclipticseasons.config.ClientConfig;
import com.teamtea.eclipticseasons.config.CommonConfig;

public class FertilityConfig {
    public boolean seasonalCrops = true;
    public boolean cropTooltips = true;
    public int outOfSeasonCropBehavior = 0;
    public int undergroundFertilityLevel = -64;

    public FertilityConfig() {

    }

    public void load() {
    }
}