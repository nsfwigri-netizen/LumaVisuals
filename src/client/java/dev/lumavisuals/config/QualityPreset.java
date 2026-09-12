package dev.lumavisuals.config;

public enum QualityPreset {
    POTATO(120, 12, false, false),
    MEDIUM(500, 24, true, false),
    HIGH(1600, 48, true, true),
    CUSTOM(500, 24, true, false);

    public final int particleLimit;
    public final int effectDistance;
    public final boolean glow;
    public final boolean blur;

    QualityPreset(int particleLimit, int effectDistance, boolean glow, boolean blur) {
        this.particleLimit = particleLimit;
        this.effectDistance = effectDistance;
        this.glow = glow;
        this.blur = blur;
    }
}
