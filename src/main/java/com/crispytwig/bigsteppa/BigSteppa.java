package com.crispytwig.bigsteppa;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(BigSteppa.MODID)
public class BigSteppa {
    public static final String MODID = "bigsteppa";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BigSteppa(IEventBus modEventBus, ModContainer modContainer) {
    }
}
