package luke.lukefix.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(value = BlockModelEmpty.class, remap = false)
public class BlockModelEmptyMixin<T extends BlockLogic> extends BlockModelStandard<T> {
    public BlockModelEmptyMixin(Block<T> block) {
        super(block);
    }

    @Override
    public IconCoordinate getParticleTexture(Side side, int meta) {
        return this.getBlockTextureFromSideAndMetadata(side, meta);
    }

}
