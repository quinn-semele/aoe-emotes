package dev.compasses.aofemotes.mixin;

import dev.compasses.aofemotes.emotes.EmoteRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

@Mixin(CommandSuggestions.class)
public class CommandSuggestorMixin {
    @Redirect(
            method = "updateCommandInfo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientSuggestionProvider;getCustomTabSugggestions()Ljava/util/Collection;"
            )
    )
    private Collection<String> breakRenderedChatMessageLines(ClientSuggestionProvider clientCommandSource) {
        List<String> suggestions = new ArrayList<>();
        suggestions.addAll(clientCommandSource.getCustomTabSugggestions());
        suggestions.addAll(EmoteRegistry.getInstance().getEmoteSuggestions());
        return suggestions;
    }
}
