package luke.glowinglamp.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLamp;
import net.minecraft.core.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Blocks.class, remap = false)
public class BlocksMixin {

	@Final
	@Shadow
	public static int[] lightEmission;

	@Shadow
	@Final
	public static Block<BlockLogicLamp> LAMP_ACTIVE;

	@Shadow
	@Final
	public static Block<BlockLogicLamp> LAMP_INVERTED_ACTIVE;

	@Inject(method = "init", at = @At("TAIL"))
	private static void adjustLampEmission(CallbackInfo ci) {
		LAMP_ACTIVE.emission = 15;
		lightEmission[LAMP_ACTIVE.id()] = 15;

		LAMP_INVERTED_ACTIVE.emission = 15;
		lightEmission[LAMP_INVERTED_ACTIVE.id()] = 15;
	}
}
