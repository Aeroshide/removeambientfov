package com.aeroshide;

import com.aeroshide.config.RemoveAmbientFOVConfigRegistrar;
import com.aeroshide.rose_bush.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class RemoveAmbientFOV implements ClientModInitializer {
	public static final String MOD_ID = "removeambientfov";
	public static boolean beaconEffects = false;
	public static boolean soulSpeedEffects = false;
	public static Config config;

	static {
		try {
			config = new Config(
					Path.of("config", MOD_ID + ".json")
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitializeClient() {
		if (config.getOption("beaconEffects") == null)
		{
			try {
				config.setOption("beaconEffects", false);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (config.getOption("soulSpeedEffects") == null)
		{
			try {
				config.setOption("soulSpeedEffects", false);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		beaconEffects = ((boolean) config.getOption("beaconEffects"));
		soulSpeedEffects = ((boolean) config.getOption("soulSpeedEffects"));

		RemoveAmbientFOVConfigRegistrar.register();

	}
}