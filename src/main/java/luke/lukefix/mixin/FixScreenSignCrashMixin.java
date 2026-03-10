package luke.lukefix.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.ScreenSignEditor;
import net.minecraft.client.gui.modelviewer.elements.ListenerButtonElement;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSign;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.lang.I18n;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(value = ScreenSignEditor.class, remap = false)
public abstract class FixScreenSignCrashMixin extends Screen {
    @Shadow
    private TileEntitySign entitySign;
    @Shadow
    private int updateCounter;
    @Shadow
    private int editLine;
    @Shadow
    private int yOffset;

    /**
     * @author
     * @reason
     */
    @Override
    @Overwrite
    public void init() {
        this.buttons.clear();
        Keyboard.enableRepeatEvents(true);

        Block<?> block = this.entitySign.getBlock();
        boolean isFreeStanding = false;

        if (block != null && block.getLogic() instanceof BlockLogicSign) {
            BlockLogicSign logicSign = (BlockLogicSign) block.getLogic();
            isFreeStanding = logicSign.isFreeStanding;
        }

        this.yOffset = isFreeStanding ? 36 : 0;
        boolean wallSign = !isFreeStanding;

        this.buttons.add(new ButtonElement(0, this.width / 2 - 100, 200 + this.yOffset, I18n.getInstance().translateKey("gui.edit_sign.button.done")));
        this.buttons.add(new ButtonElement(1, this.width / 2 - 20, 170 + this.yOffset, 20, 20, "<"));
        this.buttons.add(new ButtonElement(2, this.width / 2, 170 + this.yOffset, 20, 20, ">"));

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            this.add((new ListenerButtonElement(30, this.width / 2 - 50, 67 + i * 12 + (wallSign ? 31 : 0), 100, 12, "").setActionListener(() -> this.editLine = finalI))).mute().hide();
        }
    }

    /**
     * @author
     * @reason
     */
    @Override
    @Overwrite
    public void render(int mx, int my, float partialTick) {
        this.renderBackground();
        this.drawStringCentered(this.font, I18n.getInstance().translateKey("gui.edit_sign.label.title"), this.width / 2, 40, 0xFFFFFF);

        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2f, 0f, 50f);
        float scale = 93.75f;
        GL11.glScalef(-scale, -scale, -scale);
        GL11.glRotatef(180f, 0f, 1f, 0f);

        Block<?> block = this.entitySign.getBlock();
        boolean isFreeStanding = false;

        if (block != null && block.getLogic() instanceof BlockLogicSign) {
            BlockLogicSign logicSign = (BlockLogicSign) block.getLogic();
            isFreeStanding = logicSign.isFreeStanding;
        }

        if (isFreeStanding) {
            float signAngle = (this.entitySign.getBlockMeta() & 15) * 360f / 16f;
            GL11.glRotatef(signAngle, 0f, 1f, 0f);
            GL11.glTranslatef(0f, -1.0625f, 0f);
        } else {
            int meta = this.entitySign.getBlockMeta() & 15;
            float signAngle = 0f;
            if (meta == 2) signAngle = 180f;
            else if (meta == 4) signAngle = 90f;
            else if (meta == 5) signAngle = -90f;
            GL11.glRotatef(signAngle, 0f, 1f, 0f);
            GL11.glTranslatef(0f, -1.0625f, 0f);
        }

        if (this.updateCounter / 6 % 2 == 0) {
            this.entitySign.lineBeingEdited = this.editLine;
        }

        GL11.glEnable(GL11.GL_BLEND);
        TileEntityRenderDispatcher.instance.renderTileEntity(Tessellator.instance, this.entitySign, -0.5F, -0.75F, -0.5F, 0.0F);
        this.entitySign.lineBeingEdited = -1;
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        if (this.entitySign.getPicture() != null) {
            this.drawStringCentered(this.font, I18n.getInstance().translateKey(this.entitySign.getPicture().getLanguageKey()), this.width / 2, 150 + this.yOffset, 0xFFFFFF);
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        super.render(mx, my, partialTick);
    }
}
