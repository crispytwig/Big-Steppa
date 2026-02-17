package com.crispytwig.webbed.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Inject(method = "getDestroyProgress", at = @At("RETURN"), cancellable = true)
    private void webbed$cobwebSpeed(BlockState state, Player player, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (!state.is(Blocks.COBWEB)) return;
        if (!player.getMainHandItem().is(ItemTags.SWORDS) && !player.getMainHandItem().is(Tags.Items.TOOLS_SHEAR)) {
            cir.setReturnValue(Math.min(1.0F, cir.getReturnValue() * 3.0F));
        } else {
            cir.setReturnValue(1.0F);
        }
    }
}
