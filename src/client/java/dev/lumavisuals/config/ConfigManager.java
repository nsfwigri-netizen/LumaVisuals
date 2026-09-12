package dev.lumavisuals.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.lumavisuals.LumaVisualsClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("luma-visuals.json");
    private static LumaConfig config = new LumaConfig();

    private ConfigManager() {}

    public static LumaConfig get() { return config; }

    public static void load() {
        try {
            if (Files.exists(PATH)) {
                LumaConfig loaded = GSON.fromJson(Files.readString(PATH), LumaConfig.class);
                if (loaded != null) config = loaded;
            }
        } catch (Exception e) {
            LumaVisualsClient.LOGGER.error("Failed to load config; using safe defaults", e);
            config = new LumaConfig();
        }

        config.normalize();
        save();
    }

    public static void save() {
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(config));
        } catch (IOException e) {
            LumaVisualsClient.LOGGER.error("Failed to save config", e);
        }
    }
}
