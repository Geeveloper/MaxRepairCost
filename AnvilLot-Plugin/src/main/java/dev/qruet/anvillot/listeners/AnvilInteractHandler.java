package dev.qruet.anvillot.listeners;

import dev.qruet.anvillot.nms.VersionHandler;
import dev.qruet.anvillot.utils.Tasky;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.LinkedList;

public class AnvilInteractHandler implements Listener {

    private static final LinkedList<Material> MATERIAL_NODES = new LinkedList<Material>() {{
        add(Material.ANVIL);
        add(Material.CHIPPED_ANVIL);
        add(Material.DAMAGED_ANVIL);
    }};

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE)
            return;

        Block block = e.getClickedBlock();
        if (!MATERIAL_NODES.contains(block.getType()))
            return;

        e.setCancelled(true);
        Tasky.sync(t -> {
            if (!player.isOnGround()) {
                player.setVelocity(new Vector(0, -0.3, 0));
            } else {
                t.cancel();
            }
        }, 1L, 5L);

        VersionHandler.openContainer(player);
    }

}