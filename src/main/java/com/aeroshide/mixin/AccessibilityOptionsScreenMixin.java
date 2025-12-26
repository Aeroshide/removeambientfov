package com.aeroshide.mixin;

import com.aeroshide.RemoveAmbientFOV;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.Arrays;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {

	@Inject(method = "options", at = @At("RETURN"), cancellable = true)
	private static void injectCustomAccessibilityOptions(Options options, CallbackInfoReturnable<OptionInstance<?>[]> cir) {
		OptionInstance<?>[] originalOptions = cir.getReturnValue();


		OptionInstance<Boolean> customOption1 = OptionInstance.createBoolean(
				"options.accessibility.removeBeacon",
				OptionInstance.cachedConstantTooltip(Component.translatable("options.accessibility.removeBeacon.description")),
                (Boolean) RemoveAmbientFOV.config.getOption("beaconEffects"),
				(newValue) -> {
                    try {
                        RemoveAmbientFOV.config.setOption("beaconEffects", newValue);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
				}
		);

		OptionInstance<Boolean> customOption2 = OptionInstance.createBoolean(
				"options.accessibility.removeSoulSpeed",
				OptionInstance.cachedConstantTooltip(Component.translatable("options.accessibility.removeSoulSpeed.description")),
				(Boolean) RemoveAmbientFOV.config.getOption("soulSpeedEffects"),
				(newValue) -> {
					try {
						RemoveAmbientFOV.config.setOption("soulSpeedEffects", newValue);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
		);

		OptionInstance<?>[] modifiedOptions = Arrays.copyOf(originalOptions, originalOptions.length + 2);

		modifiedOptions[originalOptions.length] = customOption1;
		modifiedOptions[originalOptions.length + 1] = customOption2;

		cir.setReturnValue(modifiedOptions);
	}
}