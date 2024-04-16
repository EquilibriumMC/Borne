package nn.iamj.borne.modules.commerce.wallet;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class CommercePrice {

    private Map<CommerceWallet, Integer> priceList;

}
