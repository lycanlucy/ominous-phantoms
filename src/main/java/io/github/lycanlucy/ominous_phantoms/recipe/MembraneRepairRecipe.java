package io.github.lycanlucy.ominous_phantoms.recipe;

import com.google.common.collect.Lists;
import io.github.lycanlucy.ominous_phantoms.OminousPhantomsConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import oshi.util.tuples.Pair;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MembraneRepairRecipe extends CustomRecipe {
    public MembraneRepairRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Nullable
    public Pair<ItemStack, ArrayList<ItemStack>> getPair(CraftingInput craftingInput) {
        ItemStack itemToRepair = ItemStack.EMPTY;
        ArrayList<ItemStack> list = Lists.newArrayList();

        for(int i = 0; i < craftingInput.size(); ++i) {
            ItemStack itemStack = craftingInput.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.isDamageableItem() && itemStack.isRepairable()) {
                    if (itemToRepair.isEmpty()) {
                        itemToRepair = itemStack;
                    }
                } else {
                    if (itemStack.is(Items.PHANTOM_MEMBRANE)) {
                        list.add(itemStack);
                    }

                }
            }
        }
        if (list.isEmpty() || itemToRepair.isEmpty())
            return null;
        return new Pair<>(itemToRepair, list);
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        if (!OminousPhantomsConfig.mendingMembranes)
            return false;
        Pair<ItemStack, ArrayList<ItemStack>> pair = getPair(craftingInput);
        if (pair == null)
            return false;
        ItemStack itemToRepair = pair.getA();
        List<ItemStack> list = pair.getB();

        return !itemToRepair.isEmpty() && !list.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        Pair<ItemStack, ArrayList<ItemStack>> pair = getPair(craftingInput);
        if (pair == null)
            return ItemStack.EMPTY;
        ItemStack itemToRepair = pair.getA();
        List<ItemStack> list = pair.getB();

        ItemStack copy = itemToRepair.copy();
        int l2 = Math.min(copy.getDamageValue(), copy.getMaxDamage() / 8);
        if (l2 > 0) {
            int j3;
            for (j3 = 0; l2 > 0 && j3 < list.size(); j3++) {
                int k3 = copy.getDamageValue() - l2;
                copy.setDamageValue(k3);
                l2 = Math.min(copy.getDamageValue(), copy.getMaxDamage() / 8);
            }

            return copy;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInput) {
        NonNullList<ItemStack> remainingItems = NonNullList.withSize(craftingInput.size(), ItemStack.EMPTY);

        Pair<ItemStack, ArrayList<ItemStack>> pair = getPair(craftingInput);
        if (pair == null)
            return remainingItems;
        ItemStack repairableItem = pair.getA().copy();
        ArrayList<ItemStack> membranes = pair.getB();

        for (int i = 0; i < craftingInput.size(); i++) {
            if (craftingInput.getItem(i).is(Items.PHANTOM_MEMBRANE)) {
                remainingItems.set(i, new ItemStack(Items.PHANTOM_MEMBRANE));
            }
        }

        int nSteps = 0;

        if (repairableItem.getDamageValue() > 0) {
            int healStep = repairableItem.getMaxDamage() / 8;
            nSteps = Math.min(membranes.size(), (int)Math.ceil(repairableItem.getDamageValue() / (double)healStep));
        }

        if (nSteps > 0) {
            int m = 0;
            for (int i = 0; m < nSteps && i < remainingItems.size(); i++) {
                ItemStack itemStack = remainingItems.get(i);
                if (itemStack.is(Items.PHANTOM_MEMBRANE)) {
                    itemStack.shrink(1);
                    m++;
                }
            }
        }

        return remainingItems;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return OminousPhantomsRecipeSerializers.MEMBRANE_REPAIR.get();
    }
}
