package luke.lukefix.mixin;

import net.minecraft.core.player.inventory.menu.MenuInventoryCreative;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MenuInventoryCreative.class, remap = false)
public abstract class MenuInventoryCreativeMixin {

//    @Shadow
//    public static List<ItemStack> creativeItems;
//
//    @Inject(method = "<clinit>", at = @At("TAIL"))
//    private static void rebuildCreativeList(CallbackInfo ci) {
//        creativeItems.clear();
//        CreativeBlocks.populate(creativeItems);
//        CreativeItems.populate(creativeItems);
//    }


}
