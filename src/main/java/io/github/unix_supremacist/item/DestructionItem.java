package io.github.unix_supremacist.item;

import io.github.unix_supremacist.interfaces.AreaBox;
import io.github.unix_supremacist.interfaces.Destroyer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import java.util.ArrayList;

public class DestructionItem extends AbstractEmpowerableItem implements AreaBox, Destroyer {
    protected final int[] MODES;
    private final int maxWidth;
    private final int maxDepth;
    private final int mult;

    public DestructionItem(Properties properties, int maxWidth, int maxDepth, int mult) {
        super(properties, maxWidth * maxDepth);
        this.maxWidth = maxWidth;
        this.maxDepth = maxDepth;
        this.mult = mult;

        MODES = new int[maxWidth * maxDepth * 2];
        for (int i = 0; i < maxPower; i++) {
            MODES[i * 2] = i % maxWidth;
            MODES[i * 2 + 1] = i / maxWidth;
        }
    }

    @Override
    public void empower(ItemStack item, Player p){
        super.empower(item, p);
        p.displayClientMessage(Component.nullToEmpty("Width: "+((maxWidth-1)*2-MODES[getPower(item)*2]*2+1)+" and Depth: "+((maxDepth-1-MODES[getPower(item)*2+1])*mult+1)), true);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ArrayList<BlockPos> blocks = getAreaFromFacing(context.getClickedFace(), context.getClickedPos(), maxWidth-1-MODES[getPower(context.getItemInHand())*2], (maxDepth-1-MODES[getPower(context.getItemInHand())*2+1])*mult);
        return destroyArea(context.getLevel(), blocks, context.getPlayer());
    }
}
