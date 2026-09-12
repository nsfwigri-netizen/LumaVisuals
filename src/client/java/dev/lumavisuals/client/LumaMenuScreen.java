package dev.lumavisuals.client;

import dev.lumavisuals.config.ConfigManager;
import dev.lumavisuals.config.LumaConfig;
import dev.lumavisuals.config.QualityPreset;
import dev.lumavisuals.config.ThemePreset;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class LumaMenuScreen extends Screen {
    private static final String[] TABS = {"Визуалы", "Косметика", "Темы", "Настройки"};

    private final Screen parent;
    private final int selectedTab;

    public LumaMenuScreen(Screen parent) {
        this(parent, 0);
    }

    public LumaMenuScreen(Screen parent, int selectedTab) {
        super(Text.literal("Luma Visuals"));
        this.parent = parent;
        this.selectedTab = Math.max(0, Math.min(TABS.length - 1, selectedTab));
    }

    @Override
    protected void init() {
        int panelX = panelX();
        int panelY = panelY();
        int contentX = panelX + 232;
        int contentWidth = panelWidth() - 264;

        for (int index = 0; index < TABS.length; index++) {
            final int tab = index;
            addDrawableChild(ButtonWidget.builder(Text.literal(TABS[index]), button -> openTab(tab))
                    .dimensions(panelX + 22, panelY + 94 + index * 42, 178, 32)
                    .build());
        }

        if (selectedTab == 0) {
            addDrawableChild(ButtonWidget.builder(
                            Text.literal("Профиль: " + ConfigManager.get().quality),
                            button -> cycleQuality())
                    .dimensions(contentX, panelY + 132, contentWidth, 30)
                    .build());
            addDrawableChild(ButtonWidget.builder(
                            Text.literal("Свечение: " + onOff(ConfigManager.get().glowEnabled())),
                            button -> toggleGlow())
                    .dimensions(contentX, panelY + 178, contentWidth, 30)
                    .build());
            addDrawableChild(ButtonWidget.builder(
                            Text.literal("Размытие: " + onOff(ConfigManager.get().blurEnabled())),
                            button -> toggleBlur())
                    .dimensions(contentX, panelY + 238, contentWidth, 30)
                    .build());
        } else if (selectedTab == 2) {
            for (int index = 0; index < ThemePreset.values().length; index++) {
                ThemePreset theme = ThemePreset.values()[index];
                addDrawableChild(ButtonWidget.builder(
                                Text.literal((ConfigManager.get().theme == theme ? "✓ " : "") + theme.displayName),
                                button -> selectTheme(theme))
                        .dimensions(contentX, panelY + 132 + index * 46, contentWidth, 34)
                        .build());
            }
        } else if (selectedTab == 3) {
            addDrawableChild(ButtonWidget.builder(Text.literal("Сбросить настройки"), button -> resetConfig())
                    .dimensions(contentX, panelY + 132, contentWidth, 30)
                    .build());
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Закрыть"), button -> close())
                .dimensions(panelX + panelWidth() - 122, panelY + panelHeight() - 48, 100, 28)
                .build());
    }

    private void openTab(int tab) {
        if (client != null) client.setScreen(new LumaMenuScreen(parent, tab));
    }

    private void cycleQuality() {
        LumaConfig config = ConfigManager.get();
        QualityPreset[] values = QualityPreset.values();
        config.quality = values[(config.quality.ordinal() + 1) % values.length];
        ConfigManager.save();
        openTab(selectedTab);
    }

    private void toggleGlow() {
        LumaConfig config = ConfigManager.get();
        if (config.quality != QualityPreset.CUSTOM) config.quality = QualityPreset.CUSTOM;
        config.customGlow = !config.customGlow;
        ConfigManager.save();
        openTab(selectedTab);
    }

    private void toggleBlur() {
        LumaConfig config = ConfigManager.get();
        if (config.quality != QualityPreset.CUSTOM) config.quality = QualityPreset.CUSTOM;
        config.customBlur = !config.customBlur;
        ConfigManager.save();
        openTab(selectedTab);
    }

    private void selectTheme(ThemePreset theme) {
        ConfigManager.get().theme = theme;
        ConfigManager.save();
        openTab(selectedTab);
    }

    private void resetConfig() {
        LumaConfig config = ConfigManager.get();
        config.quality = QualityPreset.POTATO;
        config.theme = ThemePreset.MIDNIGHT;
        config.customParticleLimit = 500;
        config.customEffectDistance = 24;
        config.customGlow = true;
        config.customBlur = false;
        config.normalize();
        ConfigManager.save();
        openTab(selectedTab);
    }

    private String onOff(boolean value) {
        return value ? "ВКЛ" : "ВЫКЛ";
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        LumaConfig config = ConfigManager.get();
        ThemePreset theme = config.theme;
        int panelX = panelX();
        int panelY = panelY();
        int panelRight = panelX + panelWidth();
        int panelBottom = panelY + panelHeight();
        int contentX = panelX + 232;

        context.fill(0, 0, width, height, 0x66000000);
        context.fill(panelX, panelY, panelRight, panelBottom, theme.panelColor);
        context.fill(panelX + 14, panelY + 14, panelX + 214, panelBottom - 14, theme.sidebarColor);
        context.fill(panelX + 232, panelY + 14, panelRight - 14, panelBottom - 14, 0x44101014);

        context.drawTextWithShadow(textRenderer, Text.literal("Luma"), panelX + 28, panelY + 28, theme.accentColor);
        context.drawTextWithShadow(textRenderer, Text.literal("VISUALS"), panelX + 29, panelY + 48, 0xFFBFC7D0);
        context.drawTextWithShadow(textRenderer, Text.literal(TABS[selectedTab]), contentX, panelY + 34, 0xFFFFFFFF);

        if (selectedTab == 0) {
            context.drawTextWithShadow(textRenderer, Text.literal("Профиль качества"), contentX, panelY + 92, 0xFFE7EAF0);
            context.drawTextWithShadow(textRenderer, Text.literal("Ограничения применяются до запуска визуальных эффектов."), contentX, panelY + 154, 0xFFB5BBC5);
            context.drawTextWithShadow(textRenderer, Text.literal("Переключатели эффектов"), contentX, panelY + 214, 0xFFE7EAF0);
            context.drawTextWithShadow(textRenderer, Text.literal("Текущий лимит частиц: " + config.particleLimit() + " | Дистанция: " + config.effectDistance()), contentX, panelY + 274, 0xFFB5BBC5);
        } else if (selectedTab == 1) {
            context.drawTextWithShadow(textRenderer, Text.literal("3D-косметика"), contentX, panelY + 92, 0xFFE7EAF0);
            context.drawTextWithShadow(textRenderer, Text.literal("Кастомные предметы, модельки и анимации будут отдельным модулем."), contentX, panelY + 126, 0xFFB5BBC5);
            context.drawTextWithShadow(textRenderer, Text.literal("Сначала подготовлен раздел меню, чтобы позже добавлять косметику без переделки интерфейса."), contentX, panelY + 160, 0xFFB5BBC5);
        } else if (selectedTab == 2) {
            context.drawTextWithShadow(textRenderer, Text.literal("Тема интерфейса"), contentX, panelY + 92, 0xFFE7EAF0);
            context.drawTextWithShadow(textRenderer, Text.literal("Выбери цветовую схему меню Luma Visuals."), contentX, panelY + 112, 0xFFB5BBC5);
        } else {
            context.drawTextWithShadow(textRenderer, Text.literal("Общие настройки"), contentX, panelY + 92, 0xFFE7EAF0);
            context.drawTextWithShadow(textRenderer, Text.literal("Сброс вернёт безопасный профиль Potato и тему Полночь."), contentX, panelY + 112, 0xFFB5BBC5);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (client != null) client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private int panelWidth() {
        return Math.min(980, width - 32);
    }

    private int panelHeight() {
        return Math.min(520, height - 32);
    }

    private int panelX() {
        return (width - panelWidth()) / 2;
    }

    private int panelY() {
        return (height - panelHeight()) / 2;
    }
}
