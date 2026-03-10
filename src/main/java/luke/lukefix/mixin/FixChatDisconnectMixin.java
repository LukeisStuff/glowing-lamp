package luke.lukefix.mixin;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestion;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.chat.GuiElementChatSuggestions;
import net.minecraft.core.net.command.CommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(value = GuiElementChatSuggestions.class, remap = false)
public abstract class FixChatDisconnectMixin {
    @Shadow
    private int tablessCursor;

    @Shadow
    private String tablessMessage;

    @Shadow
    private ParseResults<CommandSource> parseResults;

    @Shadow
    private List<Suggestion> suggestions;

    @Inject(method = "updateSuggestions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/net/handler/PacketHandlerClient;addToSendQueue(Lnet/minecraft/core/net/packet/Packet;)V"), cancellable = true)
    private void preventInvalidSuggestionRequest(CallbackInfo ci) {
        if (this.tablessMessage.startsWith("/") && this.tablessCursor <= 0) {
            ci.cancel();
            this.suggestions.clear();
            this.parseResults = null;
        }
    }
}
