package dev.compasses.aofemotes.emotes;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class Emote {
    private final int id;
    private final String name;
    private final ResourceLocation textureId;
    private final int frameTimeMs;
    private final int width;
    private final int height;
    private final int frameCount;

    private Emote(int id, String name, ResourceLocation textureId, int frameTimeMs, int width, int height, int frameCount) {
        this.id = id;
        this.name = name;
        this.textureId = textureId;
        this.frameTimeMs = frameTimeMs;
        this.width = width;
        this.height = height;
        this.frameCount = frameCount;
    }

    public static Emote create(int id, String name, ResourceLocation filePath, int frameTimeMs) throws IOException {
        if (frameTimeMs < 0) {
            throw new IllegalArgumentException("frame time must be greater than 0");
        }

        Resource resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(filePath);
        BufferedImage image = ImageIO.read(resource.open());

        if (image == null) {
            throw new IOException("Failed to load image: " + filePath);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int frameCount = 1;

        if (frameTimeMs > 0) {
            frameCount = height / width;
            //noinspection SuspiciousNameCombination
            height = width;
        }

        return new Emote(id, name, filePath, frameTimeMs, width, height, frameCount);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getFrameTimeMs() {
        return frameTimeMs;
    }

    public boolean isAnimated() {
        return frameTimeMs > 0;
    }

    public int getSheetWidth() {
        return width;
    }

    public int getSheetHeight() {
        return height * frameCount;
    }

    public ResourceLocation getTextureIdentifier() {
        return textureId;
    }
}
