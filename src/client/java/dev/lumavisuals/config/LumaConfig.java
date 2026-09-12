package dev.lumavisuals.config;

public final class LumaConfig {
    public static final int MIN_PARTICLE_LIMIT = 0;
    public static final int MAX_PARTICLE_LIMIT = 5000;
    public static final int MIN_EFFECT_DISTANCE = 4;
    public static final int MAX_EFFECT_DISTANCE = 64;

    public QualityPreset quality = QualityPreset.POTATO;
    public ThemePreset theme = ThemePreset.MIDNIGHT;
    public int customParticleLimit = 500;
    public int customEffectDistance = 24;
    public boolean customGlow = true;
    public boolean customBlur = false;

    public void normalize() {
        if (quality == null) quality = QualityPreset.POTATO;
        if (theme == null) theme = ThemePreset.MIDNIGHT;
        customParticleLimit = clamp(customParticleLimit, MIN_PARTICLE_LIMIT, MAX_PARTICLE_LIMIT);
        customEffectDistance = clamp(customEffectDistance, MIN_EFFECT_DISTANCE, MAX_EFFECT_DISTANCE);
    }

    public int particleLimit() {
        return quality == QualityPreset.CUSTOM ? customParticleLimit : quality.particleLimit;
    }

    public int effectDistance() {
        return quality == QualityPreset.CUSTOM ? customEffectDistance : quality.effectDistance;
    }

    public boolean glowEnabled() {
        return quality == QualityPreset.CUSTOM ? customGlow : quality.glow;
    }

    public boolean blurEnabled() {
        return quality == QualityPreset.CUSTOM && customBlur || quality != QualityPreset.CUSTOM && quality.blur;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
