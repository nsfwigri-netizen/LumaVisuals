# Luma Visuals Alpha

Lightweight but expandable client-side visual mod for **Minecraft 1.21.4 Fabric**, designed with Zalith Launcher and MobileGlues testing in mind.

## Alpha 0.1
- Java 21
- Fabric Loader 0.16.14+
- Fabric API
- Config with Potato / Medium / High / Custom profiles
- In-game startup confirmation
- Native Luma Visuals menu opened with Right Shift
- Visuals, Cosmetics, Themes and Settings sections
- Three menu themes: Midnight, Graphite and Rose
- Config validation and safe reset
- GitHub Actions JAR build

## Build on GitHub
Push to `main`, open **Actions**, choose the latest successful run, and download `Luma-VisualsAlpha-JAR`.
The playable JAR is inside the downloaded artifact under `build/libs` output selection.

## Test
Install Fabric 1.21.4 and Fabric API in Zalith, put the built `luma-visuals-0.1.0-alpha.jar` into `mods`, then start the game. A green startup message confirms the mod loaded. Press **Right Shift** to open the Luma Visuals menu. The Cosmetics section is currently a prepared UI placeholder for future 3D items, models and animations.
