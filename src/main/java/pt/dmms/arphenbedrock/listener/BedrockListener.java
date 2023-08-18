package pt.dmms.arphenbedrock.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pt.dmms.arphenbedrock.ArphenBedrock;
import pt.dmms.arphenbedrock.manager.BlockManager;

public class BedrockListener implements Listener {

    private final ArphenBedrock plugin;
    private final BlockManager blockManager;

    public BedrockListener(ArphenBedrock plugin) {
        this.plugin = plugin;
        this.blockManager = plugin.getBlockManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        blockManager.breakBlock(event.getBlock());
    }

}
