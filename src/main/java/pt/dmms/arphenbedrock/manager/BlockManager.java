package pt.dmms.arphenbedrock.manager;

import lombok.val;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import pt.dmms.arphenbedrock.ArphenBedrock;
import pt.dmms.arphenbedrock.repository.BlockRepository;
import pt.dmms.arphenbedrock.repository.WorldRepository;

public class BlockManager {

    private final ArphenBedrock plugin;
    private final BlockRepository blockRepository;
    private final WorldRepository worldRepository;

    public BlockManager(ArphenBedrock plugin) {
        this.plugin = plugin;
        this.blockRepository = plugin.getBlockRepository();
        this.worldRepository = plugin.getWorldRepository();
    }

    public void breakBlock(Block block) {
        if (worldRepository.isNotWorld(block.getLocation().getWorld())) {
            return;
        }
        val type = block.getType();
        if (blockRepository.isBlackListed(type)) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.BEDROCK);
            }
        }.runTaskLater(plugin, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(type);
            }
        }.runTaskLater(plugin, getTime());
    }

    private long getTime() {
        return plugin.getConfig().getLong("regen-speed");
    }

}
