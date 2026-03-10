package luke.lukefix.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.tileentity.TileEntityRendererSign;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.enums.EnumSignPicture;
import net.minecraft.core.util.helper.Color;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(value = TileEntityRendererSign.class, remap = false)
public abstract class TileEntityRendererSignMixin {

    @Final
    @Shadow
    private Minecraft mc;

    @Shadow
    private static void drawTexturedModalRect(double width, double height, int color, IconCoordinate coordinate) {
    }

    @Redirect(method = "doRender*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/tileentity/TileEntityRendererSign;drawTexturedModalRect(DDILnet/minecraft/client/render/texture/stitcher/IconCoordinate;)V"))
    private void fixPictureLighting(double width, double height, int colorSign, IconCoordinate coordinate, Tessellator t, TileEntitySign tileEntity, double x, double y, double z, float partialTick) {
        if (tileEntity.isGlowing()) {
            if (LightmapHelper.isLightmapEnabled()) {
                LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(15, 15));
            } else {
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }
        } else if (LightmapHelper.isLightmapEnabled()) {
            int sky = mc.currentWorld.getSavedLightValue(net.minecraft.core.enums.LightLayer.Sky, tileEntity.x, tileEntity.y, tileEntity.z);
            int block = mc.currentWorld.getSavedLightValue(net.minecraft.core.enums.LightLayer.Block, tileEntity.x, tileEntity.y, tileEntity.z);
            LightmapHelper.setLightmapCoord(LightmapHelper.getLightmapCoord(sky, block));
        }

        float brightness = (!LightmapHelper.isLightmapEnabled() && !mc.isFullbrightEnabled()) ? mc.currentWorld.getLightBrightness(tileEntity.x, tileEntity.y, tileEntity.z) : 1.0F;

        if (tileEntity.isGlowing()) {
            brightness = 1.0F;
        }

        int pictureColor = getPictureColor(colorSign, tileEntity, brightness);

        drawTexturedModalRect(width, height, pictureColor, coordinate);
    }

    @Unique
    private static int getPictureColor(int colorSign, TileEntitySign tileEntity, float brightness) {
        EnumSignPicture picture = tileEntity.getPicture();

        int r, g, b, a = Color.alphaFromInt(colorSign);

        if (picture == EnumSignPicture.BARRICADE) {
            int gray = (int) (255 * brightness);
            r = gray;
            g = gray;
            b = gray;
        } else {
            r = (int) (Color.redFromInt(colorSign) * brightness);
            g = (int) (Color.greenFromInt(colorSign) * brightness);
            b = (int) (Color.blueFromInt(colorSign) * brightness);
        }

        return Color.intToIntARGB(a, r, g, b);
    }
}
