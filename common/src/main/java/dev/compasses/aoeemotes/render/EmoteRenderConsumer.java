package dev.compasses.aoeemotes.render;

import dev.compasses.aoeemotes.emotes.Emote;

/**
 * @author Ellie Semele
 */
public interface EmoteRenderConsumer {
    void accept(Emote emote, float emoteX, float emoteY);
}
