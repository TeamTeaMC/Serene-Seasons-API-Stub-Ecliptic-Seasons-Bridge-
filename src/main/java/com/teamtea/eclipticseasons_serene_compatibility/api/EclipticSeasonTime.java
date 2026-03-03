//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.teamtea.eclipticseasons_serene_compatibility.api;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.common.core.SolarHolders;
import com.teamtea.eclipticseasons.config.CommonConfig;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.Season.SubSeason;
import sereneseasons.api.season.Season.TropicalSeason;

import java.lang.ref.WeakReference;

public final class EclipticSeasonTime implements ISeasonState {

    public static final ISeasonState ZERO = new EclipticSeasonTime(null);

    public final int time = 0;
    public final WeakReference<Level> level;

    public EclipticSeasonTime(Level level) {
        this.level = new WeakReference<>(level);
    }

    private <T> T or(Function<Level, T> function, Supplier<T> defaultValue) {
        if (level.get() != null) {
            return function.apply(level.get());
        }
        return defaultValue.get();
    }

    public int getDayDuration() {
        return or(EclipticUtil::getDayLengthInMinecraft,
                EclipticUtil::getDayLengthInMinecraftStatic);
    }

    public int getSubSeasonDuration() {
        return this.getDayDuration() * CommonConfig.Season.lastingDaysOfEachTerm.get() * 2;
    }

    public int getSeasonDuration() {
        return this.getSubSeasonDuration() * 3;
    }

    public int getCycleDuration() {
        return this.getSubSeasonDuration() * SubSeason.VALUES.length;
    }

    public int getSeasonCycleTicks() {
        return or((level) -> SolarHolders.getSaveDataLazy(level)
                        .map(sd -> {
                            int tick = sd.getSolarTermsTicks();
                            return tick + sd.getSolarTermsDay() * EclipticUtil.getDayLengthInMinecraft(level);
                        }).orElse(0),
                () -> 0);
    }

    public int getDay() {
        return or(EclipticUtil::getNowSolarDay, () -> 0);
    }

    public Season.SubSeason getSubSeason() {
        int index = getIndex();
        return SubSeason.VALUES[index];
    }

    private int getIndex() {
        return or(EclipticUtil::getNowSolarTerm, () -> SolarTerm.BEGINNING_OF_SPRING).ordinal() / 2
                % SubSeason.VALUES.length;
    }

    public Season getSeason() {
        return this.getSubSeason().getSeason();
    }

    public Season.TropicalSeason getTropicalSeason() {
        int index = ((getIndex() + 11) / 2 + 5) % TropicalSeason.VALUES.length;
        return TropicalSeason.VALUES[index];
    }
}
