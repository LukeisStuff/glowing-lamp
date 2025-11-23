package luke.glowinglamp.mixin;

import net.minecraft.core.player.inventory.slot.SlotCreative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = {
	SlotCreative.class},
	remap = false)
public abstract class SlotStackSizeMixin {

	@Inject(method = "getMaxStackSize",
		at = @At("HEAD"),
		cancellable = true)
	private void overrideMaxStackSize(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(128);
	}
}
