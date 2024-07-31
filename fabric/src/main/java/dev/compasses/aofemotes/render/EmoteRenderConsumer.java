package dev.compasses.aofemotes.render;

import dev.compasses.aofemotes.emotes.Emote;

/**
 * @author Ellie Semele
 */
public interface EmoteRenderConsumer {
    void accept(Emote emote, float emoteX, float emoteY);
}
