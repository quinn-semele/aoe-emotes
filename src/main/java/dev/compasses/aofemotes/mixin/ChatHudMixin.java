package dev.compasses.aofemotes.mixin;

import dev.compasses.aofemotes.Constants;
import dev.compasses.aofemotes.render.EmoteRenderHelper;
import dev.compasses.aofemotes.text.TextReaderVisitor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {ChatHud.class}, priority = 1010)
public abstract class ChatHudMixin {
    @Redirect(
            method = {"render"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I"
            )
    )
    private int drawWithShadow(DrawContext drawContext, TextRenderer textRenderer, OrderedText text, int x, int y, int color) {
        drawContext.getMatrices().translate(0.0D, -0.5D, 0.0D);
        TextReaderVisitor textReaderVisitor = new TextReaderVisitor();
        text.accept(textReaderVisitor);
        float emoteSize = (float) textRenderer.getWidth(Constants.EMOTE_PLACEHOLDER);
        float emoteAlpha = (float) (color >> 24 & 255) / 255.0f;
        EmoteRenderHelper.extractEmotes(textReaderVisitor, textRenderer, x, y, (emote, emoteX, emoteY) -> {
            EmoteRenderHelper.drawEmote(drawContext, emote, emoteX, emoteY, emoteSize, emoteAlpha, 1.05F, 1.5F);
        });
        drawContext.getMatrices().translate(0.0D, 0.5D, 0.0D);
        return drawContext.drawTextWithShadow(textRenderer, textReaderVisitor.getOrderedText(), x, y, color);
    }
}
