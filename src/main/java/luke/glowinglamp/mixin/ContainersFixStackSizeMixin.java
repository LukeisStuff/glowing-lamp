package luke.glowinglamp.mixin;

import net.minecraft.core.block.entity.*;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.player.inventory.container.ContainerCompound;
import net.minecraft.core.player.inventory.container.ContainerCrafting;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.player.inventory.container.ContainerSimple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = {
	ContainerCompound.class,
	ContainerCrafting.class,
	ContainerInventory.class,
	ContainerSimple.class,
	EntityMinecart.class,
	TileEntityActivator.class,
	TileEntityChest.class,
	TileEntityDispenser.class,
	TileEntityFurnace.class,
	TileEntityTrommel.class},
	remap = false)
public abstract class ContainersFixStackSizeMixin {

	@Inject(method = "getMaxStackSize",
		at = @At("HEAD"),
		cancellable = true)
	private void overrideMaxStackSize(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(128);
	}
}
