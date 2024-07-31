package dev.compasses.aoeemotes.text;

import net.minecraft.network.chat.Style;

public final class TextPart {
    private final Style style;
    private final char chr;

    public TextPart(Style style, char chr) {
        this.style = style;
        this.chr = chr;
    }

    public Style getStyle() {
        return style;
    }

    public char getChar() {
        return chr;
    }
}
