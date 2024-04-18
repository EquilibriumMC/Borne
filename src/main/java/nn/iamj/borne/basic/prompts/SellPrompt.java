package nn.iamj.borne.basic.prompts;

import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.storage.PricesStorage;
import nn.iamj.borne.modules.util.addons.messenger.Messenger;
import nn.iamj.borne.modules.util.inventory.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class SellPrompt {

    private SellPrompt() {}

    public static boolean handle(final @NotNull Profile profile) {
        final Player player = profile.asBukkit();

        if (player == null || !player.isOnline()) return false;

        final Inventory inventory = player.getInventory();

        double result = 0.0D;
        for (final ItemStack stack : inventory) {
            final Material material = stack.getType();

            final double price = PricesStorage.getPrice(material);
            if (price != 0.0D) {
                result += price * stack.getAmount();

                InventoryUtils.removeItems(player, stack,
                        stack.getAmount());
            }
        }

        if (result == 0.0D) {
            Messenger.sendMessage(player, "&cУ Вас нету чего продать..");
            return false;
        }

        profile.getWallet().addMoney(result);
        profile.save(true);

        Messenger.sendMessage(player, "&7Вы получили &e" + result + " &f혤");

        return true;
    }

    public static boolean handle(final @NotNull Profile profile, final ItemStack stack) {
        final Player player = profile.asBukkit();

        if (player == null || !player.isOnline()) return false;

        final Material material = stack.getType();

        final double price = PricesStorage.getPrice(material);
        if (price != 0.0D) {
            InventoryUtils.removeItems(player, stack,
                    stack.getAmount());
        }

        final double result = price * stack.getAmount();

        profile.getWallet().addMoney(result);
        profile.save(true);

        Messenger.sendMessage(player, "&7Вы получили &e" + result + " &f혤");

        return true;
    }

}
