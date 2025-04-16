package io.github.lycanlucy.ominous_phantoms.mixin;

import io.github.lycanlucy.ominous_phantoms.effect.OminousPhantomsEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.PlayerSpawnPhantomsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
    @Shadow
    private int nextTick;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(ServerLevel serverLevel, boolean spawnHostiles, boolean spawnPassives, CallbackInfoReturnable<Integer> cir) {
        if (!spawnHostiles) {
            cir.setReturnValue(0);
        }
        RandomSource randomSource = serverLevel.getRandom();
        --this.nextTick;
        if (this.nextTick > 0) {
            cir.setReturnValue(0);
        } else {
            this.nextTick += (20 + randomSource.nextInt(20)) * 20;
            if (serverLevel.getSkyDarken() < 5 && serverLevel.dimensionType().hasSkyLight()) {
                cir.setReturnValue(0);
            } else {
                int i = 0;

                for (ServerPlayer serverPlayer : serverLevel.players()) {
                    if (!serverPlayer.isSpectator()) {
                        BlockPos playerPosition = serverPlayer.blockPosition();
                        PlayerSpawnPhantomsEvent event = EventHooks.firePlayerSpawnPhantoms(serverPlayer, serverLevel, playerPosition);
                        boolean isAllow = event.getResult() == PlayerSpawnPhantomsEvent.Result.ALLOW;

                        if (event.shouldSpawnPhantoms(serverLevel, playerPosition)) {
                            if (isAllow || serverPlayer.hasEffect(OminousPhantomsEffects.NIGHT_OMEN)) {
                                BlockPos blockPos = playerPosition.above(20 + randomSource.nextInt(15)).east(-10 + randomSource.nextInt(21)).south(-10 + randomSource.nextInt(21));
                                BlockState blockState = serverLevel.getBlockState(blockPos);
                                FluidState fluidState = serverLevel.getFluidState(blockPos);
                                if (NaturalSpawner.isValidEmptySpawnBlock(serverLevel, blockPos, blockState, fluidState, EntityType.PHANTOM)) {
                                    SpawnGroupData spawnGroupData = null;
                                    int phantomsToSpawn = event.getPhantomsToSpawn();
                                    MobEffectInstance effect = serverPlayer.getEffect(OminousPhantomsEffects.NIGHT_OMEN);
                                    if (effect != null) {
                                        for (int i1 = 0; i1 < phantomsToSpawn; ++i1) {
                                            Phantom phantom = EntityType.PHANTOM.create(serverLevel);
                                            if (phantom != null) {
                                                phantom.moveTo(blockPos, 0.0F, 0.0F);
                                                spawnGroupData = phantom.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(playerPosition), MobSpawnType.NATURAL, spawnGroupData);
                                                phantom.setPhantomSize(effect.getAmplifier());
                                                serverLevel.addFreshEntityWithPassengers(phantom);
                                                ++i;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                cir.setReturnValue(i);
            }
        }
        cir.cancel();
    }
}
