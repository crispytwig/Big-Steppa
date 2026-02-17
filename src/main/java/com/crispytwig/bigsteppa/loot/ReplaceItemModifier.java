package com.crispytwig.bigsteppa.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ReplaceItemModifier extends LootModifier {

    public static final MapCodec<ReplaceItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).and(inst.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(m -> m.count),
                    Codec.DOUBLE.optionalFieldOf("chance", 1.0).forGetter(m -> m.chance)
            )).apply(inst, ReplaceItemModifier::new));

    private final Item item;
    private final int count;
    private final double chance;

    public ReplaceItemModifier(LootItemCondition[] conditions, Item item, int count, double chance) {
        super(conditions);
        this.item = item;
        this.count = count;
        this.chance = chance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> lootTable, @NotNull LootContext context) {
        if (chance < 1.0 && context.hasParam(LootContextParams.TOOL)) {
            ItemStack tool = context.getParam(LootContextParams.TOOL);
            if (tool.is(Items.SHEARS) || tool.getItem() instanceof SwordItem) {
                return lootTable;
            }
        }
        lootTable.clear();
        if (chance < 1.0 && context.getRandom().nextDouble() >= chance) {
            return lootTable;
        }
        int toAdd = count;
        int maxStack = new ItemStack(item).getMaxStackSize();
        while (toAdd > 0) {
            int stackSize = Math.min(maxStack, toAdd);
            lootTable.add(new ItemStack(item, stackSize));
            toAdd -= stackSize;
        }
        return lootTable;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
