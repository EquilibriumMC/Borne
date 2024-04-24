package nn.iamj.borne.modules.commerce.utils;

import nn.iamj.borne.modules.commerce.wallet.CommercePrice;
import nn.iamj.borne.modules.commerce.wallet.CommerceWallet;
import nn.iamj.borne.modules.profile.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class CommerceUtils {

    private CommerceUtils() {}

    public static boolean has(final @NotNull Profile profile, final CommercePrice price) {
        final Map<CommerceWallet, Integer> priceList = price.getPriceList();

        if (priceList.isEmpty())
            return true;

        for (final Map.Entry<CommerceWallet, Integer> wallet : priceList.entrySet()) {
            final CommerceWallet commerce = wallet.getKey();
            final int count = wallet.getValue();

            switch (commerce) {
                case MONEY -> { if (profile.getWallet().getMoney() < count) return false; }
                case DONATED -> { if (profile.getWallet().getDonated() < count) return false; }

                default -> { if (profile.getStorage().getWallet(commerce.convert()) < count) return false; }
            }
        }

        return true;
    }

    public static void pay(final @NotNull Profile profile, final CommercePrice price) {
        final Map<CommerceWallet, Integer> priceList = price.getPriceList();

        if (priceList.isEmpty())
            return;

        for (final Map.Entry<CommerceWallet, Integer> wallet : priceList.entrySet()) {
            final CommerceWallet commerce = wallet.getKey();
            final int count = wallet.getValue();

            switch (commerce) {
                case MONEY -> profile.getWallet().removeMoney(count);
                case DONATED -> profile.getWallet().removeDonated(count);

                default -> profile.getStorage().removeWallet(commerce.convert(), count);
            }
        }

        profile.save(true);
    }

}
