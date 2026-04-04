package luke.lukefix.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelPistonHead;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.piston.BlockLogicPistonHead;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.client.render.block.model.BlockModel.renderBlocks;

@Environment(EnvType.CLIENT)
@Mixin(value = BlockModelPistonHead.class, remap = false)
public abstract class BlockModelPistonHeadMixin {

    @Shadow
    protected IconCoordinate pistonDry;
    @Shadow
    protected IconCoordinate pistonSticky;
    @Shadow
    protected IconCoordinate pistonSteel;

    @Shadow
    public IconCoordinate faceTextureOverride;

    @Unique
    private IconCoordinate pistonHeadSticky;
    @Unique
    private IconCoordinate pistonShaftSticky;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void initStickyTextures(CallbackInfo ci) {
        this.pistonHeadSticky = TextureRegistry.getTexture("lukefix:block/piston_sticky/head");
        this.pistonShaftSticky = TextureRegistry.getTexture("lukefix:block/piston_sticky/shaft");
    }

    @Inject(method = "getBlockTextureFromSideAndMetadata", at = @At("HEAD"), cancellable = true)
    private void getTexture(Side side, int data, CallbackInfoReturnable<IconCoordinate> cir) {
        Direction direction = BlockLogicPistonHead.getDirectionFromMeta(data);
        int type = BlockLogicPistonHead.getPistonType(data);

        if (side.getDirection() == direction) {
            if (this.faceTextureOverride != null) {
                cir.setReturnValue(this.faceTextureOverride);
                return;
            }

            switch (type) {
                case BlockLogicPistonHead.TYPE_NORMAL:
                    cir.setReturnValue(this.pistonDry);
                    return;
                case BlockLogicPistonHead.TYPE_STICKY:
                    cir.setReturnValue(this.pistonSticky);
                    return;
                case BlockLogicPistonHead.TYPE_STEEL:
                    cir.setReturnValue(this.pistonSteel);
                    return;
            }
        }

        if (side.getDirection() == direction.getOpposite()) {
            switch (type) {
                case BlockLogicPistonHead.TYPE_STICKY:
                    cir.setReturnValue(this.pistonSticky);
                    return;
                case BlockLogicPistonHead.TYPE_STEEL:
                    cir.setReturnValue(this.pistonSteel);
                    return;
                default:
                    cir.setReturnValue(this.pistonDry);
                    return;
            }
        }

        if (type == BlockLogicPistonHead.TYPE_STICKY) {
            cir.setReturnValue(this.pistonHeadSticky);
        }
    }

    @Inject(method = "renderPistonHead", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/model/BlockModelPistonHead;renderStandardBlock(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/util/phys/AABB;III)Z", shift = At.Shift.AFTER))
    private void setShaftTextureAfterHead(Tessellator tessellator, Block<BlockLogicPistonHead> block, int x, int y, int z, boolean flag, CallbackInfoReturnable<Boolean> cir) {
        int meta = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
        int type = BlockLogicPistonHead.getPistonType(meta);

        if (type == BlockLogicPistonHead.TYPE_STICKY) {
            renderBlocks.overrideBlockTexture = this.pistonShaftSticky;
        }
    }

    @Inject(method = "renderPistonHead", at = @At("RETURN"))
    private void clearOverride(Tessellator tessellator, Block<BlockLogicPistonHead> block, int x, int y, int z, boolean flag, CallbackInfoReturnable<Boolean> cir) {
        renderBlocks.overrideBlockTexture = null;
    }
}
