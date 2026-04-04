package luke.lukefix;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.ModelEntrypoint;


public class LukeFixMod implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint, ModelEntrypoint {
    public static final String MOD_ID = "lukefix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Luke's Fixes initialized.");
    }

    @Override
    public void beforeGameStart() {

    }

    @Override
    public void afterGameStart() {

    }

    @Override
    public void beforeClientStart() {

    }

    @Override
    public void afterClientStart() {

    }

    @Override
    public void initBlockModels(BlockModelDispatcher dispatcher) {

    }

    @Override
    public void initItemModels(ItemModelDispatcher dispatcher) {
        dispatcher.addDispatch(new ItemModelStandard(Blocks.TORCH_COAL.asItem(), null)
            .setIcon("lukefix:item/torch").setFullBright().setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(Blocks.TORCH_REDSTONE_ACTIVE.asItem(), null)
            .setIcon("lukefix:item/torch_redstone").setFullBright().setFull3D());

        dispatcher.addDispatch(new ItemModelStandard(Blocks.TORCH_REDSTONE_IDLE.asItem(), null)
            .setIcon("lukefix:item/torch_redstone_unlit").setFull3D());

    }

    @Override
    public void initEntityModels(EntityRenderDispatcher dispatcher) {

    }

    @Override
    public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

    }

    @Override
    public void initBlockColors(BlockColorDispatcher dispatcher) {

    }
}
