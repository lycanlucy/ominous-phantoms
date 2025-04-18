package io.github.lycanlucy.ominous_phantoms.mixin;

import io.github.lycanlucy.ominous_phantoms.OminousPhantomsConfig;
import io.github.lycanlucy.ominous_phantoms.effect.OminousPhantomsEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomAttackPlayerTargetGoal")
public abstract class PhantomAttackPlayerTargetGoalMixin {
    @Shadow @Final
    Phantom this$0;
    @Unique
    private final TargetingConditions ominous_phantoms$pickyAttackTargeting = TargetingConditions.forCombat().selector(livingEntity -> livingEntity.hasEffect(OminousPhantomsEffects.NIGHT_OMEN)).range(64.0F);

    @Inject(method = "canUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Phantom$PhantomAttackPlayerTargetGoal;reducedTickDelay(I)I", shift = At.Shift.AFTER), cancellable = true)
    private void canUse(CallbackInfoReturnable<Boolean> cir) {
        if (OminousPhantomsConfig.pickyPhantoms) {
            List<Player> list = this$0.level().getNearbyPlayers(this.ominous_phantoms$pickyAttackTargeting, this$0, this$0.getBoundingBox().inflate(16.0F, 64.0F, 16.0F));
            if (!list.isEmpty()) {
                list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                for(Player player : list) {
                    if (this$0.canAttack(player, TargetingConditions.DEFAULT)) {
                        this$0.setTarget(player);
                        cir.setReturnValue(true);
                    }
                }
            }
            cir.setReturnValue(false);
        }
    }
}
