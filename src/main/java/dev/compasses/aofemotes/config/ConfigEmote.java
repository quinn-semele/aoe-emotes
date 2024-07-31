package dev.compasses.aofemotes.config;

import dev.compasses.aofemotes.emotes.Emote;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

/**
 * @author Ellie Semele
 */
@SuppressWarnings("ClassCanBeRecord")
public final class ConfigEmote {
    private final String name;
    private final String path;
    private final int frameTime;

    public ConfigEmote(String name, String path, int frameTime) {
        this.name = name;
        this.path = path;
        this.frameTime = frameTime;
    }

    public Emote asRegistryEmote(int emoteId) throws IOException {
        return Emote.create(emoteId, name, ResourceLocation.tryParse(path), frameTime);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getFrameTime() {
        return frameTime;
    }
}
