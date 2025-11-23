package luke.glowinglamp.mixin;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, remap = false)
public abstract class ItemStackLimitMixin {

    @Inject(method = "getItemStackLimit(Lnet/minecraft/core/item/ItemStack;)I",
		at = @At("HEAD"),
            cancellable = true, remap = false)
    private void newItemStackLimit(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int id = stack.itemID;
        if (id >= 0 && id < Blocks.blocksList.length && Blocks.blocksList[id] != null) {
            cir.setReturnValue(128);
        }
    }
}
