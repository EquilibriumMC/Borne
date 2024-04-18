package nn.iamj.borne.modules.commerce.unit;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.commerce.wallet.CommercePrice;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter @Setter
public class CommerceUnit {

    private CommercePrice price;

    private int inflation;
    private int ratio;

    private ItemStack display;

    private List<ItemStack> stackList;
    private List<String> commandList;

    public ItemStack getDisplay() {
        if (this.display != null)
            return this.display;

        return this.stackList.isEmpty() ?
                new ItemStack(Material.AIR) :
                this.stackList.get(0);
    }

    public void addItem(final @NotNull ItemStack stack) {
        this.stackList.add(stack);
    }

    public void removeItem(final @NotNull ItemStack stack) {
        this.stackList.remove(stack);
    }

    public void addCommand(final @NotNull String command) {
        this.commandList.add(command);
    }

    public void removeCommand(final @NotNull String command) {
        this.commandList.remove(command);
    }

}
