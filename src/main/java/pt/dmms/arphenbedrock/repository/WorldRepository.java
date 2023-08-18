package pt.dmms.arphenbedrock.repository;

import org.bukkit.World;
import pt.dmms.arphenbedrock.ArphenBedrock;

import java.util.ArrayList;
import java.util.List;

public class WorldRepository {
    
    private final ArphenBedrock plugin;

    public WorldRepository(ArphenBedrock plugin) {
        this.plugin = plugin;
        this.load();
    }
    
    private final List<String> worlds = new ArrayList<>();
    
    public void update() {
        this.worlds.clear();
        this.load();
    }
    
    private void load() {
        this.worlds.addAll(this.plugin.getConfig().getStringList("worlds"));
    }

    public boolean isNotWorld(World world) {
        return !worlds.contains(world.getName());
    }
    
    public List<String> getWorlds() {
        return worlds;
    }
    
}
