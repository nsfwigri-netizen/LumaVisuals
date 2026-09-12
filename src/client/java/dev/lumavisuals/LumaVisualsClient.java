package dev.lumavisuals;

import dev.lumavisuals.client.LumaMenuScreen;
import dev.lumavisuals.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LumaVisualsClient implements ClientModInitializer {
    public static final String MOD_ID = "lumavisuals";
    public static final Logger LOGGER = LoggerFactory.getLogger("Luma Visuals");
    private boolean announced;
    private KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lumavisuals.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.lumavisuals"
        ));

        var config = ConfigManager.get();
        LOGGER.info(
                "Luma Visuals Alpha loaded with {} quality (particles: {}, distance: {}, glow: {}, blur: {})",
                config.quality,
                config.particleLimit(),
                config.effectDistance(),
                config.glowEnabled(),
                config.blurEnabled()
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new LumaMenuScreen(null));
            }

            if (!announced && client.player != null) {
                announced = true;
                client.player.sendMessage(Text.literal("§aLuma Visuals Alpha запущен §7[" + ConfigManager.get().quality + "]"), false);
            }
            if (client.player == null) announced = false;
        });
    }
}
