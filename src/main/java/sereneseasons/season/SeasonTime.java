package sereneseasons.season;

import com.teamtea.eclipticseasons_serene_compatibility.api.EclipticSeasonTime;
import net.minecraft.world.level.Level;

public final class SeasonTime extends EclipticSeasonTime {
    public static final SeasonTime ZERO = new SeasonTime(null);

    public SeasonTime(Level level) {
        super(level);
    }
}