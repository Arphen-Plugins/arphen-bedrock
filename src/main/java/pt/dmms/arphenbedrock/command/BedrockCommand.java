package pt.dmms.arphenbedrock.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import pt.dmms.arphenbedrock.ArphenBedrock;

@CommandAlias("arphenbedrock")
@CommandPermission("arphenbedrock.admin")
public class BedrockCommand extends BaseCommand {

    private final ArphenBedrock plugin;

    public BedrockCommand(ArphenBedrock plugin) {
        this.plugin = plugin;
    }

    @Default
    public void onCommand(CommandSender sender) {
        plugin.reloadConfig(sender);
    }

}
