package com.sargant.bukkit.toughblocks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;

/**
 * Handle events for all Player related events
 * @author <yourname>
 */
public class ToughblocsPlayerListener extends PlayerListener {
    private final ToughBlocks plugin;

    public ToughblocsPlayerListener(ToughBlocks instance) {
        plugin = instance;
    }

    //Insert Player related code here
}
