package com.crispytwig.webbed.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class AddItemModifier extends LootModifier {

    public static final MapCodec<AddItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).and(inst.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.addedItem),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(m -> m.count),
                    Codec.BOOL.optionalFieldOf("if_empty", false).forGetter(m -> m.ifEmpty)
            )).apply(inst, AddItemModifier::new));

    private final Item addedItem;
    private final int count;
    private final boolean ifEmpty;

    public AddItemModifier(LootItemCondition[] conditions, Item item, int count, boolean ifEmpty) {
        super(conditions);
        this.addedItem = item;
        this.count = count;
        this.ifEmpty = ifEmpty;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> lootTable, @NotNull LootContext context) {
        if (ifEmpty && !lootTable.isEmpty()) {
            return lootTable;
        }
        int toAdd = count;
        ItemStack stack = new ItemStack(addedItem);
        int maxStack = stack.getMaxStackSize();
        while (toAdd > 0) {
            int stackSize = Math.min(maxStack, toAdd);
            lootTable.add(new ItemStack(addedItem, stackSize));
            toAdd -= stackSize;
        }
        return lootTable;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
