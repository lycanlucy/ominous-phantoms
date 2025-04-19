package io.github.lycanlucy.ominous_phantoms.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lycanlucy.ominous_phantoms.OminousPhantomsConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AddConfigurableTableLootModifier extends AddTableLootModifier {
    private final Config config;

    public static final MapCodec<AddConfigurableTableLootModifier> CODEC =
            RecordCodecBuilder.mapCodec(
                    instance -> instance.group(IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(glm -> glm.conditions), ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("table").forGetter(AddConfigurableTableLootModifier::table), StringRepresentable.fromEnum(Config::values).fieldOf("config").forGetter(AddConfigurableTableLootModifier::config)).apply(instance, AddConfigurableTableLootModifier::new)
            );

    public AddConfigurableTableLootModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> table, Config config) {
        super(conditionsIn, table);
        this.config = config;
    }

    public Config config() {
        return config;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        switch (this.config) {
            case BONY_PHANTOMS -> {
                if (OminousPhantomsConfig.bonyPhantoms)
                    return super.doApply(generatedLoot, context);
            }
            case DUNGEON_BOTTLES -> {
                if (OminousPhantomsConfig.dungeonBottles)
                    return super.doApply(generatedLoot, context);
            }
        }
        return generatedLoot;
    }

    public enum Config implements StringRepresentable {
        BONY_PHANTOMS("bony_phantoms"),
        DUNGEON_BOTTLES("dungeon_bottles");

        private final String name;

        Config(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
