package io.github.lycanlucy.ominous_phantoms;

import io.github.lycanlucy.ominous_phantoms.effect.OminousPhantomsEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

public class OminousPhantomsEvents {
    @EventBusSubscriber(modid = OminousPhantoms.MOD_ID)
    public static class NeoForgeEvents {
        @SubscribeEvent
        public static void onAnvilUpdate(AnvilUpdateEvent event) {
            if (!OminousPhantomsConfig.mendingMembranes)
                return;
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();
            if (event.getOutput().isEmpty() && left.isDamageableItem() && right.is(Items.PHANTOM_MEMBRANE)) {
                ItemStack copy = left.copy();
                int l2 = Math.min(copy.getDamageValue(), copy.getMaxDamage() / 8);
                if (l2 > 0) {
                    int j3;
                    for (j3 = 0; l2 > 0 && j3 < right.getCount(); j3++) {
                        int k3 = copy.getDamageValue() - l2;
                        copy.setDamageValue(k3);
                        l2 = Math.min(copy.getDamageValue(), copy.getMaxDamage() / 8);
                    }
                    if (event.getName() != null) {
                        if (event.getName().isEmpty() && copy.has(DataComponents.CUSTOM_NAME))
                            copy.remove(DataComponents.CUSTOM_NAME);
                        else if (!copy.getHoverName().getString().equals(event.getName()))
                            copy.set(DataComponents.CUSTOM_NAME, Component.literal(event.getName()));
                    }

                    event.setCost(1L);
                    event.setMaterialCost(j3);
                    event.setOutput(copy);
                }
            }
        }

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
