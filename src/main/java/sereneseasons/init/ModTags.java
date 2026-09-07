package sereneseasons.init;

import com.teamtea.eclipticseasons_serene_compatibility.SereneCompatibility;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("removal")
public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> GREENHOUSE_GLASS = BlockTags.GRASS_BLOCKS;
    }

    public static class Biomes {
        private static TagKey<Biome> tag(Identifier name) {
            return TagKey.create(Registries.BIOME, name);
        }

        public static final TagKey<Biome> BLACKLISTED_BIOMES = tag(SereneCompatibility.rl("blacklisted_biomes"));
        public static final TagKey<Biome> INFERTILE_BIOMES = tag(SereneCompatibility.rl("infertile_biomes"));
        public static final TagKey<Biome> LESSER_COLOR_CHANGE_BIOMES = tag(SereneCompatibility.rl("lesser_color_change_biomes"));
        public static final TagKey<Biome> TROPICAL_BIOMES = tag(SereneCompatibility.rl("tropical_biomes"));
    }
}
