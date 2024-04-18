package nn.iamj.borne.plugin.loader;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import nn.iamj.borne.basic.commands.admin.admin.control.game.SellCommand;
import org.bukkit.event.HandlerList;
import nn.iamj.borne.Borne;
import nn.iamj.borne.basic.commands.admin.BorneCommand;
import nn.iamj.borne.basic.commands.admin.admin.control.GamemodeCommand;
import nn.iamj.borne.basic.commands.admin.admin.control.KillCommand;
import nn.iamj.borne.basic.commands.admin.admin.control.mine.EMineCommand;
import nn.iamj.borne.basic.commands.admin.admin.control.mine.EPriceCommand;
import nn.iamj.borne.basic.commands.game.skills.SkillsCommand;
import nn.iamj.borne.basic.commands.game.talents.TalentsCommand;
import nn.iamj.borne.basic.gameplay.listeners.BlockListener;
import nn.iamj.borne.basic.gameplay.listeners.ExperienceListener;
import nn.iamj.borne.basic.gameplay.listeners.HudListener;
import nn.iamj.borne.basic.gameplay.listeners.ProfileBasicListener;
import nn.iamj.borne.basic.gameplay.listeners.ability.SkillListener;
import nn.iamj.borne.basic.gameplay.listeners.boss.BossListener;
import nn.iamj.borne.basic.gameplay.listeners.entity.SkillEntityListener;
import nn.iamj.borne.basic.gameplay.listeners.menu.admin.MineSettingsListener;
import nn.iamj.borne.basic.gameplay.listeners.mine.MineListener;
import nn.iamj.borne.basic.gameplay.listeners.statistic.StatisticListener;
import nn.iamj.borne.basic.gameplay.listeners.ability.TalentListener;
import nn.iamj.borne.basic.protocol.crashes.TabPacketListener;
import nn.iamj.borne.basic.providers.HudProvider;
import nn.iamj.borne.managers.impl.*;
import nn.iamj.borne.managers.impl.addons.BossManager;
import nn.iamj.borne.managers.impl.addons.MineManager;
import nn.iamj.borne.managers.impl.addons.SkillManager;
import nn.iamj.borne.managers.impl.addons.TalentManager;
import nn.iamj.borne.modules.command.listener.CommandListener;
import nn.iamj.borne.modules.database.DataBase;
import nn.iamj.borne.modules.human.listeners.HumanPacketListener;
import nn.iamj.borne.modules.menu.listener.MenuListener;
import nn.iamj.borne.modules.profile.listener.ProfileListener;
import nn.iamj.borne.modules.server.scheduler.Scheduler;
import nn.iamj.borne.modules.storage.PricesStorage;
import nn.iamj.borne.modules.util.gson.GsonProvider;
import nn.iamj.borne.modules.util.logger.LoggerProvider;
import nn.iamj.borne.plugin.BornePlugin;

import java.util.Arrays;
import java.util.List;

@Getter
public final class BorneLoader implements Borne {

    @Getter
    private static BorneLoader loader;

    private final BornePlugin plugin;

    private final DataBase dataBase;

    private final ConfigManager configManager;
    private final ProfileManager profileManager;
    private final MenuManager menuManager;
    private final CommandManager commandManager;
    private final ListenerManager listenerManager;
    private final EntityManager entityManager;
    private final HumanManager humanManager;

    private final TalentManager talentManager;
    private final SkillManager skillManager;
    private final MineManager mineManager;
    private final BossManager bossManager;

    public BorneLoader(final BornePlugin plugin) {
        loader = this;

        this.plugin = plugin;

        LoggerProvider.loadLogger();

        this.configManager = new ConfigManager();
        this.configManager.preload();

        this.dataBase = new DataBase();

        this.profileManager = new ProfileManager();
        this.commandManager = new CommandManager();
        this.menuManager = new MenuManager();
        this.listenerManager = new ListenerManager();
        this.entityManager = new EntityManager();
        this.humanManager = new HumanManager();

        this.talentManager = new TalentManager();
        this.skillManager = new SkillManager();
        this.mineManager = new MineManager();
        this.bossManager = new BossManager();
    }

    @Override
    public void preload() {
        this.dataBase.connect();
        this.dataBase.create();

        this.profileManager.preload();
        this.commandManager.preload();
        this.menuManager.preload();
        this.listenerManager.preload();
        this.entityManager.preload();
        this.humanManager.preload();

        this.talentManager.preload();
        this.skillManager.preload();
        this.mineManager.preload();
        this.bossManager.preload();
    }

    @Override
    public void initialize() {
        GsonProvider.loadGson();

        this.configManager.initialize();
        this.profileManager.initialize();
        this.commandManager.initialize();
        this.menuManager.initialize();
        this.listenerManager.initialize();
        this.entityManager.initialize();
        this.humanManager.initialize();

        this.talentManager.initialize();
        this.skillManager.initialize();
        this.mineManager.initialize();
        this.bossManager.initialize();

        this.loadClasses();
        this.loadListeners();
        this.loadCommands();
    }

    private void loadListeners() {
        // ProtocolLib listeners..
        this.listenerManager.registerListener(new HumanPacketListener());
        this.listenerManager.registerListener(new TabPacketListener());

        // Listeners..
        this.listenerManager.registerListener(new ProfileListener());
        this.listenerManager.registerListener(new CommandListener());
        this.listenerManager.registerListener(new MenuListener());

        this.listenerManager.registerListener(new ExperienceListener());
        this.listenerManager.registerListener(new BlockListener());
        this.listenerManager.registerListener(new HudListener());
        this.listenerManager.registerListener(new ProfileBasicListener());
        this.listenerManager.registerListener(new BossListener());

        this.listenerManager.registerListener(new TalentListener());
        this.listenerManager.registerListener(new SkillListener());
        this.listenerManager.registerListener(new SkillEntityListener());
        this.listenerManager.registerListener(new MineListener());
        this.listenerManager.registerListener(new StatisticListener());

        // Providers..
        this.listenerManager.registerListener(new HudProvider());

        // Commands and menus..
        this.listenerManager.registerListener(new EMineCommand());

        this.listenerManager.registerListener(new MineSettingsListener());
    }

    private void loadCommands() {
        // Borne commands.
        this.commandManager.registerCommand(new BorneCommand());

        // Admin commands.
        this.commandManager.registerCommand(new GamemodeCommand());
        this.commandManager.registerCommand(new KillCommand());
        this.commandManager.registerCommand(new SellCommand());
        this.commandManager.registerCommand(new EMineCommand());
        this.commandManager.registerCommand(new EPriceCommand());

        // Game commands.
        this.commandManager.registerCommand(new SkillsCommand());
        this.commandManager.registerCommand(new TalentsCommand());
    }

    private void loadClasses() {
        Scheduler.asyncHandleRate(() ->
                this.dataBase.fetchData("SELECT 1")
        , 10 * 20 * 60, 10 * 20 * 60);

        PricesStorage.loadPrices();

        final List<String> paths = Arrays.asList(
                "nn.iamj.borne.modules.util.math.FastMath",
                "nn.iamj.borne.modules.util.math.NumbersUtils"
        );

        for (final String path : paths) {
            try {
                Class.forName(path);
            } catch (ClassNotFoundException exception) {
                LoggerProvider.getInstance().error("Ops!", exception);
            }
        }
    }

    @Override
    public void shutdown() {
        this.bossManager.shutdown();
        this.mineManager.shutdown();
        this.skillManager.shutdown();
        this.talentManager.shutdown();

        this.humanManager.shutdown();
        this.entityManager.shutdown();
        this.listenerManager.shutdown();
        this.menuManager.shutdown();
        this.commandManager.shutdown();
        this.profileManager.shutdown();
        this.configManager.shutdown();

        ProtocolLibrary.getProtocolManager().removePacketListeners(this.plugin);
        HandlerList.unregisterAll(this.plugin);

        Scheduler.shutdown();

        this.dataBase.close();

        loader = null;
    }

}
