package com.teamtea.eclipticseasons_serene_compatibility;

import com.teamtea.eclipticseasons.api.event.SolarTermChangeEvent;
import com.teamtea.eclipticseasons_serene_compatibility.api.EclipticSeasonTime;
import com.teamtea.eclipticseasons_serene_compatibility.config.LayerCommonConfig;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


@Mod.EventBusSubscriber(modid = SereneCompatibility.MODID)

public class AllListener {
    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof Level level) {
            SeasonHelper.SEASON_STATE_HASH_MAP.put(level, new EclipticSeasonTime(level));
        }
    }

    @SubscribeEvent
    public static void onLevelUnLoad(LevelEvent.Unload event) {
        if (event.getLevel() instanceof Level level) {
            SeasonHelper.SEASON_STATE_HASH_MAP.remove(level);
        }
    }

    private static Method fireMethod = null;
    private static Constructor<?> eventConstructor = null;
    @SubscribeEvent
    public static void onSolarTermChangeEvent(SolarTermChangeEvent event) {
        Season.SubSeason oldSub = Season.SubSeason.VALUES[event.getOldSolarTerm().ordinal() / 2];
        Season.SubSeason newSub = Season.SubSeason.VALUES[event.getNewSolarTerm().ordinal() / 2];

        if (oldSub != newSub) {
            try {
                if (fireMethod == null) {
                    Class<?> eventManagerClass = Class.forName("glitchcore.event.EventManager");
                    Class<?> eventBaseClass = Class.forName("glitchcore.event.Event");
                    fireMethod = eventManagerClass.getMethod("fire", eventBaseClass);

                    Class<?> eventClass = Class.forName("sereneseasons.api.season.SeasonChangedEvent$Standard");
                    eventConstructor = eventClass.getConstructor(Level.class, Season.SubSeason.class, Season.SubSeason.class);
                }
                Object seasonEvent = eventConstructor.newInstance(event.getLevel(), oldSub, newSub);
                fireMethod.invoke(null, seasonEvent);
            } catch (Exception e) {
                SereneCompatibility.logger("Try fire glitchcore event" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onTagsUpdatedEvent(TagsUpdatedEvent event) {
        LayerCommonConfig.init();
    }
}
