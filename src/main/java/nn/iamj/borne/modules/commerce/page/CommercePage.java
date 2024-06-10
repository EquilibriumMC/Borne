package nn.iamj.borne.modules.commerce.page;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.commerce.unit.CommerceSlot;
import nn.iamj.borne.modules.commerce.unit.CommerceUnit;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CommercePage {

    private List<CommerceSlot> unitList = new ArrayList<>();

}
