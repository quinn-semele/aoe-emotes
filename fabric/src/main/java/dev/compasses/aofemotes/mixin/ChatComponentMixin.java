package dev.compasses.aofemotes.mixin;

import dev.compasses.aofemotes.Constants;
import dev.compasses.aofemotes.render.EmoteRenderHelper;
import dev.compasses.aofemotes.text.TextReaderVisitor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ChatComponent.class, priority = 1010)
public abstract class ChatComponentMixin {
    @Redirect(
            method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIIZ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;III)I"
            )
    )
    private int drawWithShadow(GuiGraphics graphics, Font fontUtils, FormattedCharSequence text, int x, int y, int color) {
        graphics.pose().translate(0.0D, -0.5D, 0.0D);
        TextReaderVisitor textReaderVisitor = new TextReaderVisitor();
        text.accept(textReaderVisitor);
        float emoteSize = (float) fontUtils.width(Constants.EMOTE_PLACEHOLDER);
        float emoteAlpha = (float) (color >> 24 & 255) / 255.0f;
        EmoteRenderHelper.extractEmotes(textReaderVisitor, fontUtils, x, y, (emote, emoteX, emoteY) -> {
            EmoteRenderHelper.drawEmote(graphics, emote, emoteX, emoteY, emoteSize, emoteAlpha, 1.05F, 1.5F);
        });
        graphics.pose().translate(0.0D, 0.5D, 0.0D);
        return graphics.drawString(fontUtils, textReaderVisitor.getOrderedText(), x, y, color);
    }
}
