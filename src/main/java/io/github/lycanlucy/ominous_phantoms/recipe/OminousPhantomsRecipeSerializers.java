package io.github.lycanlucy.ominous_phantoms.recipe;

import io.github.lycanlucy.ominous_phantoms.OminousPhantoms;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OminousPhantomsRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, OminousPhantoms.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MembraneRepairRecipe>> MEMBRANE_REPAIR = RECIPE_SERIALIZERS.register("crafting_special_membrane_repair", () -> new SimpleCraftingRecipeSerializer<>(MembraneRepairRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
