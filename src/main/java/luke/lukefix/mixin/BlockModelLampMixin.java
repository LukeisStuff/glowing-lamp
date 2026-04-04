package luke.lukefix.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelLamp;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicLamp;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(value = BlockModelLamp.class, remap = false)
public class BlockModelLampMixin<T extends BlockLogic> extends BlockModelStandard<T> {
    @Shadow
    @Final
    private static IconCoordinate[] texCoordsActive;

    public BlockModelLampMixin(Block<T> block) {
        super(block);
    }

    @Override
    public boolean hasOverbright() {
        return ((BlockLogicLamp)this.block.getLogic()).isActive;
    }

    @Override
    public IconCoordinate getBlockOverbrightTextureFromSideAndMeta(Side side, int data) {
        return texCoordsActive[data & 15];
    }
}
