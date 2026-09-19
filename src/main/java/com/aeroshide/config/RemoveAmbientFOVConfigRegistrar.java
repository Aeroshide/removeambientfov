package com.aeroshide.config;

import com.aeroshide.RemoveAmbientFOV;
import com.aeroshide.rose_bush.api.RoselibVanillaSettings;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;

import java.io.IOException;

public class RemoveAmbientFOVConfigRegistrar {

    public static void register() {
        OptionInstance<Boolean> beaconOption = OptionInstance.createBoolean(
                "options.accessibility.removeBeacon",
                OptionInstance.cachedConstantTooltip(Component.translatable("options.accessibility.removeBeacon.description")),
                getSafeBoolean("beaconEffects", true),
                (newValue) -> saveConfig("beaconEffects", newValue)
        );

        OptionInstance<Boolean> soulSpeedOption = OptionInstance.createBoolean(
                "options.accessibility.removeSoulSpeed",
                OptionInstance.cachedConstantTooltip(Component.translatable("options.accessibility.removeSoulSpeed.description")),
                getSafeBoolean("soulSpeedEffects", true),
                (newValue) -> saveConfig("soulSpeedEffects", newValue)
        );

        RoselibVanillaSettings.addAccessibilityOption(beaconOption);
        RoselibVanillaSettings.addAccessibilityOption(soulSpeedOption);
    }

    private static boolean getSafeBoolean(String key, boolean fallback) {
        try {
            Object rawVal = RemoveAmbientFOV.config.getOption(key);
            if (rawVal instanceof Boolean) return (Boolean) rawVal;
            if (rawVal instanceof String) return Boolean.parseBoolean((String) rawVal);
            return fallback;
        } catch (Exception e) {
            return fallback;
        }
    }

    private static void saveConfig(String key, boolean value) {
        try {
            RemoveAmbientFOV.config.setOption(key, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}