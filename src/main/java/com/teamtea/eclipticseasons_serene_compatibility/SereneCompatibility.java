package com.teamtea.eclipticseasons_serene_compatibility;


import com.teamtea.eclipticseasons.common.registry.ItemRegistry;
import com.teamtea.eclipticseasons_serene_compatibility.config.LayerClientConfig;
import com.teamtea.eclipticseasons_serene_compatibility.config.LayerCommonConfig;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sereneseasons.api.SSItems;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SereneCompatibility.MODID)
public class SereneCompatibility {
    public static final String MODID = "sereneseasons";
    public static final Logger LOGGER = LogManager.getLogger(SereneCompatibility.MODID);

    public static void logger(Object... x) {
        extraLogger(false, x);
    }

    public static void extraLogger(boolean debug, Object... x) {

        // if (!FMLEnvironment.production||General.bool.get())
        {
            StringBuilder output = new StringBuilder();

            for (Object i : x) {
                if (i == null) output.append(", ").append("null");
                else if (i.getClass().isArray()) {
                    output.append(", [");
                    if (i instanceof Object[] objects) {
                        for (Object c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof float[] objects) {
                        for (float c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof int[] objects) {
                        for (int c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof double[] objects) {
                        for (double c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof long[] objects) {
                        for (long c : objects) {
                            output.append(c).append(",");
                        }
                    } else if (i instanceof boolean[] objects) {
                        for (boolean c : objects) {
                            output.append(c).append(",");
                        }
                    }
                    output.append("]");
                } else if (i instanceof List list) {
                    output.append(", [");
                    for (Object c : list) {
                        output.append(c);
                    }
                    output.append("]");
                } else
                    output.append(", ").append(i);
            }
            if (debug) {
                LOGGER.debug(output.substring(1));
            } else {
                LOGGER.info(output.substring(1));
            }
        }

    }


    public SereneCompatibility(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::FMLCommonSetup);

        modEventBus.addListener(LayerCommonConfig::UpdateConfig);
        modEventBus.addListener(LayerClientConfig::UpdateConfig);

        modContainer.registerConfig(ModConfig.Type.COMMON, LayerCommonConfig.COMMON_CONFIG);
        modContainer.registerConfig(ModConfig.Type.CLIENT, LayerClientConfig.CLIENT_CONFIG);

        if (FMLLoader.getCurrentOrNull().getDist() == Dist.CLIENT)
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);


    }

    public static Identifier rl(String id) {
        return Identifier.fromNamespaceAndPath(MODID, id);
    }

    public void FMLCommonSetup(final FMLCommonSetupEvent event) {
        sereneseasons.init.ModConfig.init();
        LayerCommonConfig.init();
        SSItems.CALENDAR= ItemRegistry.calendar_item.get();
    }

}
