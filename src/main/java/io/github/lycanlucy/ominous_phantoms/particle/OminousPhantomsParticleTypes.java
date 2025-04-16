package io.github.lycanlucy.ominous_phantoms.particle;

import io.github.lycanlucy.ominous_phantoms.OminousPhantoms;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OminousPhantomsParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, OminousPhantoms.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<SimpleParticleType>> NIGHT_OMEN = PARTICLE_TYPES.register("night_omen", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
