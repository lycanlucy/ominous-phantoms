package io.github.lycanlucy.ominous_phantoms.loot;

import com.mojang.serialization.MapCodec;
import io.github.lycanlucy.ominous_phantoms.OminousPhantoms;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class OminousPhantomsLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, OminousPhantoms.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddConfigurableTableLootModifier>> ADD_CONFIGURABLE_TABLE = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_configurable_table", () -> AddConfigurableTableLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
