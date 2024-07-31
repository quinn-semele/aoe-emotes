package dev.compasses.aoeemotes.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;

import java.util.ArrayList;
import java.util.List;

public class TextReaderVisitor implements FormattedCharSink {
    private final List<TextPart> parts = new ArrayList<>();

    @Override
    public boolean accept(int val, Style style, int currCharInt) {
        parts.add(new TextPart(style, (char) currCharInt));

        return true;
    }

    public void replaceBetween(int beginIndex, int endIndex, String text, Style style) {
        this.deleteBetween(beginIndex, endIndex);
        this.insertAt(beginIndex, text, style);
    }

    private void deleteBetween(int beginIndex, int endIndex) {
        if (endIndex > beginIndex) {
            parts.subList(beginIndex, endIndex).clear();
        }
    }

    private void insertAt(int index, String text, Style style) {
        for (int i = 0; i < text.length(); ++i) {
            parts.add(index + i, new TextPart(style, text.charAt(i)));
        }
    }

    public FormattedCharSequence getOrderedText() {
        MutableComponent text = Component.literal("");

        for (TextPart part : parts) {
            text.append(Component.literal(Character.toString(part.getChar())).setStyle(part.getStyle()));
        }

        return text.getVisualOrderText();
    }

    public String getString() {
        StringBuilder builder = new StringBuilder();

        for (TextPart part : parts) {
            builder.append(part.getChar());
        }

        return builder.toString();
    }
}
