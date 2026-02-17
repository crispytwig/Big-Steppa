package com.crispytwig.webbed.events;

import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class BreakEvents {

    @SubscribeEvent
    public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (event.getTargetBlock().is(Blocks.COBWEB)) {
            event.setCanHarvest(true);
        }
    }
}
