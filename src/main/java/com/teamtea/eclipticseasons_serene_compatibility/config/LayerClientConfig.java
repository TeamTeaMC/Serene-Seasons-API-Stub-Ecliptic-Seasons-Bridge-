package com.teamtea.eclipticseasons_serene_compatibility.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class LayerClientConfig {

    public static final ForgeConfigSpec CLIENT_CONFIG = new ForgeConfigSpec.Builder().configure(LayerClientConfig::new).getRight();

    protected LayerClientConfig(ForgeConfigSpec.Builder builder) {

    }

    public static void UpdateConfig(ModConfigEvent modConfigEvent) {
        if (!(modConfigEvent instanceof ModConfigEvent.Unloading)
                && modConfigEvent.getConfig().getSpec() == CLIENT_CONFIG) {
        }
    }
}
