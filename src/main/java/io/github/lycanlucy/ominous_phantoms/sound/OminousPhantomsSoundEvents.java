package io.github.lycanlucy.ominous_phantoms.sound;

import io.github.lycanlucy.ominous_phantoms.OminousPhantoms;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OminousPhantomsSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, OminousPhantoms.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> APPLY_EFFECT_NIGHT_OMEN = create("event.mob_effect.night_omen");

    public static DeferredHolder<SoundEvent, SoundEvent> create(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(OminousPhantoms.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
