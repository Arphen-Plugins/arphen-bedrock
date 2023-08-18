package pt.dmms.arphenbedrock.repository;

import org.bukkit.Material;
import pt.dmms.arphenbedrock.ArphenBedrock;

import java.util.ArrayList;
import java.util.List;

public class BlockRepository {

    private final ArphenBedrock plugin;

    public BlockRepository(ArphenBedrock plugin) {
        this.plugin = plugin;
        this.load();
    }

    private final List<Material> blackListedMaterials = new ArrayList<>();

    public void update() {
        this.blackListedMaterials.clear();
        this.load();
    }

    private void load() {
        this.blackListedMaterials.addAll(this.plugin.getConfig().getStringList("black-listed-materials")
                .stream().map(Material::valueOf).toList());
    }

    public boolean isBlackListed(Material material) {
        return blackListedMaterials.contains(material);
    }

    public List<Material> getBlackListedMaterials() {
        return blackListedMaterials;
    }
}