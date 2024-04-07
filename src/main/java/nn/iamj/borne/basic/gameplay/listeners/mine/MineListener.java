package nn.iamj.borne.basic.gameplay.listeners.mine;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Breedable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.api.events.mine.MineRegenerateEvent;
import nn.iamj.borne.modules.api.events.profile.action.ProfileBlockBreakEvent;
import nn.iamj.borne.modules.mine.Mine;
import nn.iamj.borne.modules.profile.Profile;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.util.blocks.Cuboid;
import nn.iamj.borne.modules.util.event.EventUtils;

import java.awt.event.FocusEvent;
import java.util.List;

public final class MineListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBroke(final PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFood(final FoodLevelChangeEvent event) {
        final HumanEntity entity = event.getEntity();

        if (entity.getFoodLevel() < 20.0D)
            entity.setFoodLevel(20);

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(final BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        event.setCancelled(!event.getPlayer().hasPermission("borne.build"));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(final BlockBreakEvent event) {
        if (event.isCancelled()) return;

        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        event.setCancelled(!player.hasPermission("borne.break"));

        final Mine mine = Borne.getBorne().getMineManager().getMine(block.getLocation());
        final Profile profile = Profile.asEntity(player);

        if (profile == null) return;

        if (mine != null && mine.getMineArea().isIn(block.getLocation())) {
            EventUtils.callStaticEvent(new ProfileBlockBreakEvent(profile, mine, block));
            event.setCancelled(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(final EntityDamageEvent event) {
        if (event.isCancelled()) return;

        final Entity entity = event.getEntity();
        final Mine mine = Borne.getBorne().getMineManager().getMine(entity.getLocation());

        if (mine == null || mine.getSettings().isAllowPvP()) return;

        event.setDamage(0.0D);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegenerate(final MineRegenerateEvent event) {
        if (event.isCancelled()) return;

        final Mine mine = event.getMine();

        final Cuboid cuboid = mine.getMineArea();
        final Location location = event.getMine().getSpawnLocation();

        if (location == null) return;

        final List<? extends Player> players = cuboid.playerList();
        Scheduler.handle(() -> players.forEach(player ->
                player.teleportAsync(location)));

        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("config.yml");

        if (configuration == null) return;

        if (configuration.getBoolean("MINES.ADDONS.FORCE-TICK-WITH-REGENERATION", false))
            mine.getHologram().updateHologram();
    }

}
