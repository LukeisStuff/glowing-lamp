package luke.lukefix.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicLamp;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BlockLogicLamp.class, remap = false)
public class BlockLogicLampMixin extends BlockLogic {

	@Shadow
	public boolean isActive;

	public BlockLogicLampMixin(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public float getAmbientOcclusionStrength(WorldSource blockAccess, int x, int y, int z) {
		if (this.isActive) {
			return 0.0F;
		}
		return super.getAmbientOcclusionStrength(blockAccess, x, y, z);
	}
}
