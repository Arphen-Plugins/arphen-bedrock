package pt.dmms.arphenbedrock;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pt.dmms.arphenbedrock.command.BedrockCommand;
import pt.dmms.arphenbedrock.listener.BedrockListener;
import pt.dmms.arphenbedrock.manager.BlockManager;
import pt.dmms.arphenbedrock.repository.BlockRepository;
import pt.dmms.arphenbedrock.repository.WorldRepository;
import pt.dmms.arphenheart.database.Database;
import pt.dmms.arphenheart.database.HikariDatabase;
import pt.dmms.arphenheart.database.SQLite;
import pt.dmms.arphenheart.factory.ConfigFactory;
import pt.dmms.arphenheart.util.SoundsUtil;
import pt.dmms.arphenheart.util.TitleUtil;
import pt.dmms.arphenheart.util.message.ColorUtil;
import pt.dmms.arphenheart.util.message.MessageUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArphenBedrock extends JavaPlugin {

    private Database database;
    private ConfigFactory langConfigFactory, soundsConfigFactory, titleConfigFactory;
    private MessageUtil messageUtil;
    private SoundsUtil soundsUtil;
    private TitleUtil titleUtil;
    private BlockRepository blockRepository;
    private WorldRepository worldRepository;
    private BlockManager blockManager;

    @Override
    public void onEnable() {
        loadConfig();
        loadUtil();
        loadRepository();
        loadManager();
        loadCommand();
        loadListener();
    }

    private final List<ConfigFactory> configFactories = new ArrayList<>();

    private void loadConfig() {
        saveDefaultConfig();
        configFactories.addAll(
                List.of(
                        langConfigFactory = new ConfigFactory(this, "lang.yml"),
                        soundsConfigFactory = new ConfigFactory(this, "sounds.yml"),
                        titleConfigFactory = new ConfigFactory(this, "title.yml")
                )
        );
        //Update the info.yml every run
        ConfigFactory.delete(this, "info.yml");
        new ConfigFactory(this, "info.yml");
    }

    private void loadUtil() {
        soundsUtil = new SoundsUtil(soundsConfigFactory.getConfig());
        titleUtil = new TitleUtil(titleConfigFactory.getConfig());
        messageUtil = new MessageUtil(langConfigFactory.getConfig(), soundsUtil, titleUtil);
    }

    private void loadRepository() {
        blockRepository = new BlockRepository(this);
        worldRepository = new WorldRepository(this);
    }

    private void loadManager() {
        blockManager = new BlockManager(this);
    }

    private void loadListener() {
        listener(new BedrockListener(this));
    }

    private void listener(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void loadCommand() {
        command(new BedrockCommand(this));
    }

    private PaperCommandManager paperCommandManager;


    private void command(BaseCommand... command) {
        paperCommandManager = new PaperCommandManager(this);
        for (BaseCommand baseCommand : command) {
            paperCommandManager.registerCommand(baseCommand);
        }
    }

    public void reloadConfig(CommandSender sender) {
        reloadConfig();
        for (ConfigFactory configFactory : configFactories) {
            configFactory.reload();
        }
        loadUtil();
        sender.sendMessage(ColorUtil.apply("<green>Config reloaded!"));
    }

}