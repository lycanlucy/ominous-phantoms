package io.github.lycanlucy.ominous_phantoms.effect;

import io.github.lycanlucy.ominous_phantoms.particle.OminousPhantomsParticleTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class NightOmenMobEffect extends MobEffect {
    protected NightOmenMobEffect() {
        super(MobEffectCategory.NEUTRAL, 4051230);
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effect) {
        Function<MobEffectInstance, ParticleOptions> particleFactory = mobEffectInstance -> (ParticleOptions) OminousPhantomsParticleTypes.NIGHT_OMEN.get();
        return particleFactory.apply(effect);
    }
}
