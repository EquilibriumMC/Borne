package nn.iamj.borne.modules.commerce.unit;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommerceSlot extends CommerceUnit {

    private int slot = -1;

    public static CommerceSlot convert(final CommerceUnit unit) {
        final CommerceSlot slot = new CommerceSlot();

        slot.setDisplay(unit.getDisplay());
        slot.setPrice(unit.getPrice());
        slot.setInflation(unit.getInflation());
        slot.setRatio(unit.getRatio());

        slot.setStackList(unit.getStackList());
        slot.setCommandList(unit.getCommandList());

        return slot;
    }

}
