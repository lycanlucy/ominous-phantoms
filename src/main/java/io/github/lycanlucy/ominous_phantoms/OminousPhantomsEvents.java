package io.github.lycanlucy.ominous_phantoms;

import io.github.lycanlucy.ominous_phantoms.effect.OminousPhantomsEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

public class OminousPhantomsEvents {
    @EventBusSubscriber(modid = OminousPhantoms.MOD_ID)
    public static class NeoForgeEvents {
        @SubscribeEvent
        public static void alterPlayerSleepCondition(CanPlayerSleepEvent event) {
            if (event.getVanillaProblem() != null)
                return;
            if (event.getEntity().hasEffect(OminousPhantomsEffects.NIGHT_OMEN)) {
                event.setProblem(Player.BedSleepingProblem.NOT_SAFE);
            } else {
                MobEffectInstance badOmenInstance = event.getEntity().getEffect(MobEffects.BAD_OMEN);
                if (badOmenInstance != null) {
                    int amplifier = badOmenInstance.getAmplifier();
                    event.getEntity().removeEffect(MobEffects.BAD_OMEN);

                    event.getEntity().addEffect(new MobEffectInstance(OminousPhantomsEffects.NIGHT_OMEN, 12010 - ((int)(event.getEntity().level().getDayTime() % 24000L) - 12010), amplifier));
                    event.setProblem(Player.BedSleepingProblem.NOT_SAFE);
                }
            }
        }
    }
}
