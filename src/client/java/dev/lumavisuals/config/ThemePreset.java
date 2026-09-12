package dev.lumavisuals.config;

public enum ThemePreset {
    MIDNIGHT("Полночь", 0xD91B2530, 0xC9151D26, 0xFFE5B5BE),
    GRAPHITE("Графит", 0xD91F2226, 0xC916191D, 0xFF9CCBFF),
    ROSE("Розовая", 0xD92B202B, 0xC91D151E, 0xFFFFA9C5);

    public final String displayName;
    public final int panelColor;
    public final int sidebarColor;
    public final int accentColor;

    ThemePreset(String displayName, int panelColor, int sidebarColor, int accentColor) {
        this.displayName = displayName;
        this.panelColor = panelColor;
        this.sidebarColor = sidebarColor;
        this.accentColor = accentColor;
    }
}
