package dev.compasses.aoeemotes.mixin;

import dev.compasses.aoeemotes.emotes.EmoteRegistry;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Mixin(CommandSuggestions.class)
public class CommandSuggestionsMixin {
    @Redirect(method = "updateCommandInfo", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientSuggestionProvider;getCustomTabSugggestions()Ljava/util/Collection;"))
    private Collection<String> breakRenderedChatMessageLines(ClientSuggestionProvider suggestionProvider) {
        Set<String> suggestions = new HashSet<>();

        suggestions.addAll(suggestionProvider.getCustomTabSugggestions());
        suggestions.addAll(EmoteRegistry.getInstance().getEmoteSuggestions());

        return suggestions;
    }
}
