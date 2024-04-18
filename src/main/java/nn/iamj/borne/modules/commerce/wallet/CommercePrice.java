package nn.iamj.borne.modules.commerce.wallet;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter @Setter
public class CommercePrice {

    private Map<CommerceWallet, Integer> priceList;

    public void addPrice(final @NotNull CommerceWallet wallet, final int price) {
        this.priceList.put(wallet, price);
    }

    public int getPrice(final @NotNull CommerceWallet wallet) {
        return this.priceList.get(wallet);
    }

    public void removePrice(final @NotNull CommerceWallet wallet) {
        this.priceList.remove(wallet);
    }

}
