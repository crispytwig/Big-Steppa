package com.crispytwig.bigsteppa;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

import com.crispytwig.bigsteppa.events.BreakEvents;
import com.crispytwig.bigsteppa.registry.BSLootModifiers;

@Mod(BigSteppa.MODID)
public class BigSteppa {
    public static final String MODID = "bigsteppa";

    public BigSteppa(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(BreakEvents.class);
        BSLootModifiers.LOOT_MODIFIERS.register(modEventBus);
    }
}
