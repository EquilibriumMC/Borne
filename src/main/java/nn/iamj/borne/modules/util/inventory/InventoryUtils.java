package nn.iamj.borne.modules.util.inventory;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class InventoryUtils {

    private InventoryUtils() {}

    public static boolean addItems(final Player player, final List<ItemStack> items) {
        final Inventory inventory = player.getInventory();

        for (final ItemStack itemStack : items) {
            inventory.addItem(itemStack);
            if (inventory.firstEmpty() == -1)
                return false;
        }

        return true;
    }

    public static boolean addItems(final Player player, final ItemStack... items) {
        final Inventory inventory = player.getInventory();

        for (final ItemStack itemStack : items) {
            inventory.addItem(itemStack);
            if (inventory.firstEmpty() == -1)
                return false;
        }

        return true;
    }

    public static boolean addItems(final Player player, final ItemStack items) {
        final Inventory inventory = player.getInventory();
        final World world = player.getWorld();

        inventory.addItem(items);
        return inventory.firstEmpty() != -1;
    }

    public static boolean hasItems(final Player player, final ItemStack stack, final int i) {
        final Inventory inv = player.getInventory();
        int count = 0;

        for (ItemStack st : inv.getContents()) {
            if (st == null)
                continue;

            final ItemStack cl = st.clone();
            cl.setAmount(1);

            if (cl.equals(stack)) {
                count += st.getAmount();
            }
        }

        return count >= i;
    }

    public static int countItems(final Player player, final ItemStack stack) {
        final Inventory inv = player.getInventory();
        int count = 0;

        for (ItemStack st : inv.getContents()) {
            if (st == null)
                continue;

            final ItemStack cl = st.clone();
            cl.setAmount(1);

            if (cl.equals(stack)) {
                count += st.getAmount();
            }
        }

        return count;
    }

    public static void removeItems(final Player player, final ItemStack stack, final int i) {
        final Inventory inv = player.getInventory();
        int count = i;

        for (ItemStack s : inv) {
            if (s == null)
                continue;

            ItemStack clone = s.clone();
            clone.setAmount(1);

            if (count <= 0)
                return;

            if (clone.equals(stack)) {
                int g = s.getAmount();

                if (s.getAmount() <= count)
                    s.setAmount(-1);
                else s.setAmount(s.getAmount() - count);

                count -= g;
            }
        }
    }

}
