package nn.iamj.borne.modules.commerce.unit;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.commerce.utils.CommerceUtils;
import nn.iamj.borne.modules.menu.executable.ExecutableClick;
import nn.iamj.borne.modules.menu.slot.MenuSlot;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.component.Component;
import nn.iamj.borne.modules.util.inventory.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class CommerceSlot extends CommerceUnit {

    private int slot = -1;

    public static MenuSlot convertToMenu(final CommerceSlot slot) {
        final MenuSlot item = new MenuSlot(slot.getDisplay());

        item.setPosition(slot.getSlot());
        item.setExecutableClick(new ExecutableClick() {
            @Override
            public void onLeft(final @NotNull Profile profile) {
                final Player player = profile.asBukkit();

                if (player == null) return;

                if (!CommerceUtils.has(profile, slot.getPrice())) {
                    profile.sendText(Component.text(Component.Type.ERROR, "У Вас недостаточно материалов для покупки этого товара."));
                    return;
                }

                CommerceUtils.pay(profile, slot.getPrice());

                InventoryUtils.addItems(player, slot.getStackList());
                slot.getCommandList().forEach(command -> {
                    final Runnable runnable = () -> Borne.getBorne().getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), command);

                    if (!Bukkit.isPrimaryThread()) {
                        Scheduler.handle(runnable);
                    } else runnable.run();
                });

                profile.sendText(Component.text(Component.Type.SUCCESS, "Вы успешно приобрели этот товар."));
            }
        });

        return item;
    }

    public static CommerceSlot convertToSlot(final CommerceUnit unit) {
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
