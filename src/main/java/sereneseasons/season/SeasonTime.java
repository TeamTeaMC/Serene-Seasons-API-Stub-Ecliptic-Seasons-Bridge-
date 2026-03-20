package sereneseasons.season;

public final class SeasonTime extends EclipticSeasonTime {
    public static final SeasonTime ZERO = new SeasonTime(null);

    public SeasonTime(Level level) {
        super(level);
    }
}