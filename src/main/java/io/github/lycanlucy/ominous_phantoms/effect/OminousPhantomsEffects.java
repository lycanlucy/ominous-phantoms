package io.github.lycanlucy.ominous_phantoms.effect;

import io.github.lycanlucy.ominous_phantoms.OminousPhantoms;
import io.github.lycanlucy.ominous_phantoms.sound.OminousPhantomsSoundEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OminousPhantomsEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, OminousPhantoms.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> NIGHT_OMEN = MOB_EFFECTS.register("night_omen", () -> new NightOmenMobEffect().withSoundOnAdded(OminousPhantomsSoundEvents.APPLY_EFFECT_NIGHT_OMEN.get()));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
