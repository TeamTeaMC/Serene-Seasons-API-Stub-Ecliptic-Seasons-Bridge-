package sereneseasons.init;

import com.teamtea.eclipticseasons.common.core.crop.CropInfoManager;
import com.teamtea.eclipticseasons.common.item.GrowthDetectorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ModFertility {
    public static boolean isCrop(BlockState state) {
        List<Component> components = CropInfoManager.appendInfo(state.getBlock());
        return !components.isEmpty();
    }

    public static boolean isCropFertile(String cropName, Level level, BlockPos pos) {
        var blockOptional = BuiltInRegistries.BLOCK
                .listElements().filter(block -> block.getKey().identifier().getPath().equals(cropName))
                .findFirst();
        return blockOptional.filter(blockReference -> GrowthDetectorItem.getGrowChance(level, pos, blockReference.value().defaultBlockState()) > 0)
                .isPresent();
    }
}
