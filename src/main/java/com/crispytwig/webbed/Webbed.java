package com.crispytwig.webbed;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

import com.crispytwig.webbed.events.BreakEvents;
import com.crispytwig.webbed.registry.WebbedLootModifiers;

@Mod(Webbed.MODID)
public class Webbed {
    public static final String MODID = "webbed";

    public Webbed(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(BreakEvents.class);
        WebbedLootModifiers.LOOT_MODIFIERS.register(modEventBus);
    }
}
