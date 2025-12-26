package com.aeroshide.mixin;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.aeroshide.RemoveAmbientFOV.config;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

	@Redirect(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getAttributeValue(Lnet/minecraft/core/Holder;)D"))
	private double ignoreSpeedModifiers(AbstractClientPlayer player, Holder<Attribute> attribute) {
		double totalValue = player.getAttributeValue(attribute);

		if (attribute == Attributes.MOVEMENT_SPEED) {
			AttributeInstance instance = player.getAttribute(Attributes.MOVEMENT_SPEED);
			if (instance == null) return totalValue;

			AttributeModifier soulSpeed = instance.getModifier(Identifier.parse("enchantment.soul_speed/feet"));
			if (soulSpeed != null && !(boolean) config.getOption("soulSpeedEffects")) {
				totalValue -= soulSpeed.amount();
			}

			if (player.hasEffect(MobEffects.SPEED) && !(boolean) config.getOption("beaconEffects")) {
				MobEffectInstance effect = player.getEffect(MobEffects.SPEED);
				if (effect != null && effect.isAmbient()) {
					double multiplier = 1.0 + (0.2 * (effect.getAmplifier() + 1));

					totalValue /= multiplier;
				}
			}
		}

		return totalValue;
	}
}