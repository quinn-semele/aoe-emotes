package dev.compasses.aoeemotes.render;

import dev.compasses.aoeemotes.Constants;
import dev.compasses.aoeemotes.emotes.Emote;
import dev.compasses.aoeemotes.emotes.EmoteRegistry;
import dev.compasses.aoeemotes.text.TextReaderVisitor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Style;

import java.util.regex.Matcher;

public class EmoteRenderHelper {
    public static void extractEmotes(TextReaderVisitor textReaderVisitor, Font textRenderer, float renderX, float renderY, EmoteRenderConsumer consumer) {
        boolean emotesLeft = true;

        while (emotesLeft) {
            String textStr = textReaderVisitor.getString();
            Matcher emoteMatch = Constants.EMOTE_ID_PATTEN.matcher(textStr);

            //noinspection AssignmentUsedAsCondition
            while (emotesLeft = emoteMatch.find()) {
                String emoteIdStr = emoteMatch.group(2);

                try {
                    int emoteId = Integer.parseInt(emoteIdStr);
                    Emote emote = EmoteRegistry.getInstance().getEmoteById(emoteId);
                    int startPos = emoteMatch.start(1);
                    int endPos = emoteMatch.end(1);

                    if (emote != null) {
                        float beforeTextWidth = (float) textRenderer.width(textStr.substring(0, startPos));
                        consumer.accept(emote, renderX + beforeTextWidth, renderY);
                        textReaderVisitor.replaceBetween(startPos, endPos, "  ", Style.EMPTY);
                        break;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
    }

    public static void drawEmote(GuiGraphics drawContext, Emote emote, float emoteX, float emoteY, float size, float alpha, float sizeMult, float maxWidthMult) {
        float scaleX = sizeMult * emote.getWidth() / emote.getHeight();
        float scaleY = sizeMult;

        if (scaleX > maxWidthMult) {
            scaleX = maxWidthMult;
            scaleY = maxWidthMult * ((float) emote.getHeight() / (float) emote.getWidth());
        }

        scaleX = (float) Math.round(size * scaleX) / size;
        scaleY = (float) Math.round(size * scaleY) / size;
        int x = (int) (emoteX + size * (1.0F - scaleX) / 2.0F);
        int y = (int) (emoteY + size * (1.0F - scaleY) / 2.0F);
        int frameNumber = emote.isAnimated() ? (int) (System.currentTimeMillis() / emote.getFrameTimeMs() % emote.getFrameCount()) : 1;
        drawContext.blit(emote.getTextureIdentifier(), x, y, Math.round(size * scaleX), Math.round(size * scaleY), 0.0F, (float) (emote.getHeight() * frameNumber), emote.getWidth(), emote.getHeight(), emote.getSheetWidth(), emote.getSheetHeight());
    }
}
