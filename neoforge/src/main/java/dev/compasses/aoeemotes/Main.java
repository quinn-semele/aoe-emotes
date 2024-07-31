package dev.compasses.aoeemotes;

import dev.compasses.aoeemotes.config.ConfigEmote;
import dev.compasses.aoeemotes.config.ConfigHelper;
import dev.compasses.aoeemotes.emotes.EmoteRegistry;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

import java.util.List;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class Main {
    public Main(IEventBus bus, ModContainer container) {
        ConfigHelper.configFile = FMLPaths.CONFIGDIR.get().resolve(Constants.MOD_ID + ".json");

        List<ConfigEmote> emotes = ConfigHelper.loadOrSaveConfig();
        EmoteRegistry.construct(emotes);

        bus.addListener(this::onRegisterReloadListeners);
    }

    void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) manager -> EmoteRegistry.getInstance().init());
    }
}
