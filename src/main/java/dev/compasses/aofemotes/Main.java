package dev.compasses.aofemotes;

import dev.compasses.aofemotes.emotes.EmoteRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import dev.compasses.aofemotes.config.ConfigEmote;
import dev.compasses.aofemotes.config.ConfigHelper;

import java.util.List;

public class Main implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        List<ConfigEmote> emotes = ConfigHelper.loadOrSaveConfig();
        EmoteRegistry.construct(emotes);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            public ResourceLocation getFabricId() {
                return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "emotes");
            }

            public void onResourceManagerReload(ResourceManager manager) {
                EmoteRegistry.getInstance().init();
            }
        });
    }
}
