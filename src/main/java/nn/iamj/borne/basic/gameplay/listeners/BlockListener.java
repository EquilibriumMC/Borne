package nn.iamj.borne.basic.gameplay.listeners;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import nn.iamj.borne.modules.util.inventory.InventoryUtils;

import java.util.Collection;
import java.util.List;

public final class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBreak(final BlockBreakEvent event) {
        if (event.isCancelled())
            return;

        event.setDropItems(false);

        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();

        final Collection<ItemStack> stack = block.getDrops(hand);

        if (stack.isEmpty() || player.getGameMode() == GameMode.CREATIVE)
            return;

        InventoryUtils.addItems(event.getPlayer(), (List<ItemStack>) stack);
    }

}
