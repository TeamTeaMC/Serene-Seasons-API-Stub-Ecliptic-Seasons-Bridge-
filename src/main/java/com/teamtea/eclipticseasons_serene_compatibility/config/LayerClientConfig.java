package com.teamtea.eclipticseasons_serene_compatibility.config;


import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class LayerClientConfig {

    public static final ModConfigSpec CLIENT_CONFIG = new ModConfigSpec.Builder().configure(LayerClientConfig::new).getRight();

    protected LayerClientConfig(ModConfigSpec.Builder builder) {

    }

    public static void UpdateConfig(ModConfigEvent modConfigEvent) {
        if (!(modConfigEvent instanceof ModConfigEvent.Unloading)
                && modConfigEvent.getConfig().getSpec() == CLIENT_CONFIG) {
        }
    }
}
