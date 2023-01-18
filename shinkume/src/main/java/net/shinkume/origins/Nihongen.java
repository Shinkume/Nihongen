package net.shinkume.origins;


import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.shinkume.origins.powers.FoxMorphPower;
import net.shinkume.origins.powers.ProjectilePower;
import net.shinkume.origins.powers.ShadowCloneJutsuPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Nihongen implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("nihongen");

	public static final PowerFactory<Power> SHADOW_CLONE_JUTSU = new PowerFactory<>(new Identifier("nihongen", "shadow_clone_jutsu"), new SerializableData().add("key", ApoliDataTypes.KEY, new Active.Key()), data -> (type, entity) -> {
		ShadowCloneJutsuPower power = new ShadowCloneJutsuPower(type, entity);
		power.setKey(data.get("key"));
		return power;
	}).allowCondition();

	public static final PowerFactory<Power> FOX_MORPH = new PowerFactory<>(new Identifier("nihongen", "fox_morph"), new SerializableData().add("key", ApoliDataTypes.KEY, new Active.Key()), data -> (type, entity) -> {
		FoxMorphPower power = new FoxMorphPower(type, entity);
		power.setKey(data.get("key"));
		return power;
	}).allowCondition();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		PowerFactory<Power> PROJECTILE_DAMAGE  = new PowerFactory<>(new Identifier("nihongen", "thrown_projectile_damage"), new SerializableData(), data -> (type, entity) -> new ProjectilePower(type, entity)).allowCondition();

		Registry.register(ApoliRegistries.POWER_FACTORY, PROJECTILE_DAMAGE.getSerializerId(), PROJECTILE_DAMAGE);
		Registry.register(ApoliRegistries.POWER_FACTORY, SHADOW_CLONE_JUTSU.getSerializerId(), SHADOW_CLONE_JUTSU);
		Registry.register(ApoliRegistries.POWER_FACTORY, FOX_MORPH.getSerializerId(), FOX_MORPH);
		LOGGER.info("Hello Fabric world!");

	}
}
