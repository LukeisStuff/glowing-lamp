package luke.lukefix.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = ItemModelStandard.class, remap = false)
public class ItemModelStandardMixin {

    @Inject(method = "renderAsItemEntity(Lnet/minecraft/client/render/tessellator/Tessellator;Lnet/minecraft/core/entity/Entity;Ljava/util/Random;Lnet/minecraft/core/item/ItemStack;IFFF)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", shift = At.Shift.AFTER))
    private void addPitchBillboard(CallbackInfo ci) {
        float pitch = EntityRenderDispatcher.instance.viewLerpPitch;
        GL11.glRotatef(-pitch, 1.0F, 0.0F, 0.0F);
    }

    @Inject(method = "renderFlat", at = @At("HEAD"))
    private void startGlow(CallbackInfo ci) {
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    @Inject(method = "renderFlat", at = @At("RETURN"))
    private void endGlow(CallbackInfo ci) {
        GL11.glPopAttrib();
    }
}
