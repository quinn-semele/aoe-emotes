package dev.compasses.aoeemotes;

import dev.compasses.aoeemotes.emotes.EmoteRegistry;
import dev.compasses.aoeemotes.config.ConfigEmote;
import dev.compasses.aoeemotes.config.ConfigHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.List;

public class Main implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigHelper.configFile = FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json");

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
