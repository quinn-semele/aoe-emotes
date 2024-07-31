package dev.compasses.aofemotes.render;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Style;
import dev.compasses.aofemotes.Constants;
import dev.compasses.aofemotes.emotes.Emote;
import dev.compasses.aofemotes.emotes.EmoteRegistry;
import dev.compasses.aofemotes.text.TextReaderVisitor;

import java.util.regex.Matcher;

public class EmoteRenderHelper {
    public static void extractEmotes(TextReaderVisitor textReaderVisitor, TextRenderer textRenderer, float renderX, float renderY, EmoteRenderConsumer consumer) {
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
                        float beforeTextWidth = (float) textRenderer.getWidth(textStr.substring(0, startPos));
                        consumer.accept(emote, renderX + beforeTextWidth, renderY);
                        textReaderVisitor.replaceBetween(startPos, endPos, "  ", Style.EMPTY);
                        break;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
    }

    public static void drawEmote(DrawContext drawContext, Emote emote, float emoteX, float emoteY, float size, float alpha, float sizeMult, float maxWidthMult) {
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
        drawContext.drawTexture(emote.getTextureIdentifier(),  x, y, Math.round(size * scaleX), Math.round(size * scaleY), 0.0F, (float) (emote.getHeight() * frameNumber), emote.getWidth(), emote.getHeight(), emote.getSheetWidth(), emote.getSheetHeight());
    }
}
