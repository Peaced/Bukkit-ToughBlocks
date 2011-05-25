// ToughBlocks - a Bukkit plugin
// Copyright (C) 2011 Robert Sargant
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.sargant.bukkit.toughblocks;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import java.util.logging.Logger;

public class ToughBlocksBlockListener extends BlockListener
{
	private ToughBlocks parent;

	public ToughBlocksBlockListener(ToughBlocks instance)
	{
		parent = instance;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event.isCancelled()) {
			return;
		}
		//final int mdata = event.getBlock().getData();
		ItemStack tool = event.getPlayer().getItemInHand();
		Block block = event.getBlock();
		Material type = tool.getType();
		if (type == Material.WOOD_AXE || type == Material.WOOD_HOE || 
				type == Material.WOOD_PICKAXE || type == Material.WOOD_SPADE || 
				type == Material.WOOD_SWORD || type == Material.STONE_AXE || type == Material.STONE_HOE || 
				type == Material.STONE_PICKAXE || type == Material.STONE_SPADE || 
				type == Material.STONE_SWORD || type == Material.IRON_AXE || type == Material.IRON_HOE || 
				type == Material.IRON_PICKAXE || type == Material.IRON_SPADE || 
				type == Material.IRON_SWORD || type == Material.GOLD_AXE || type == Material.GOLD_HOE || 
				type == Material.GOLD_PICKAXE || type == Material.GOLD_SPADE || 
				type == Material.GOLD_SWORD || type == Material.DIAMOND_AXE || type == Material.DIAMOND_HOE || 
				type == Material.DIAMOND_PICKAXE || type == Material.DIAMOND_SPADE || 
				type == Material.DIAMOND_SWORD) {
			short dur = tool.getDurability();
			dur = (short) (dur+1);
			//event.getPlayer().sendMessage("debug : "+ dur);
			if ((type == Material.WOOD_AXE || type == Material.WOOD_HOE || 
					type == Material.WOOD_PICKAXE || type == Material.WOOD_SPADE || 
					type == Material.WOOD_SWORD) && dur >= 60) {
					//event.getPlayer().sendMessage("debug : Should break");
					dur = 60;
					tool.setDurability(dur);
			}
			else if ((type == Material.STONE_AXE || type == Material.STONE_HOE || 
					type == Material.STONE_PICKAXE || type == Material.STONE_SPADE || 
					type == Material.STONE_SWORD) && dur >= 132) {
				//event.getPlayer().sendMessage("debug : Should break");	
				dur = 132;
				tool.setDurability(dur);
			}
			else if ((type == Material.IRON_AXE || type == Material.IRON_HOE || 
					type == Material.IRON_PICKAXE || type == Material.IRON_SPADE || 
					type == Material.IRON_SWORD) && dur >= 251) {
				//event.getPlayer().sendMessage("debug : Should break");
				dur = 251;
				tool.setDurability(dur);
			}		
			else if ((type == Material.GOLD_AXE || type == Material.GOLD_HOE || 
					type == Material.GOLD_PICKAXE || type == Material.GOLD_SPADE || 
					type == Material.GOLD_SWORD) && dur >= 33) {
				//event.getPlayer().sendMessage("debug : Should break");
				dur = 33;
				tool.setDurability(dur);
			}
			else if ((type == Material.DIAMOND_AXE || type == Material.DIAMOND_HOE || 
					type == Material.DIAMOND_PICKAXE || type == Material.DIAMOND_SPADE || 
					type == Material.DIAMOND_SWORD) && dur >= 1562) {
				//event.getPlayer().sendMessage("debug : Should break");
				dur = 1562;
				tool.setDurability(dur);
			}			
			else {
				tool.setDurability(dur);
			}
		}		

		for(ToughBlocksContainer obc : parent.toughList) {

			// Check held item matches
			if(!obc.tool.contains(null) && !obc.tool.contains(tool.getType())) {
				continue;
			}

			// Check target block matches
			if(obc.original != event.getBlock().getType()) {
				continue;
			}
			final byte mdata = event.getBlock().getData();
			//event.getPlayer().sendMessage("debug : "+ event.getBlock().getData() + " -- mdata =" + mdata);
			
			// If block is not in the map already, add it
			if(!parent.blockHealth.containsKey(block.hashCode())) {
				parent.blockHealth.put(block.hashCode(), ToughBlocks.MAX_BLOCK_HEALTH);
				
			}
			
			Integer newHealth = parent.blockHealth.get(block.hashCode());
			newHealth = newHealth - obc.damage;
			
			if(newHealth <= 0) {
				parent.blockHealth.remove(block.hashCode());
			} else {
				parent.blockHealth.put(block.hashCode(), newHealth);
				
				block.setType(Material.DIRT); // Can't set to AIR for some reason
				block.setType(obc.original);
				//event.getPlayer().sendMessage("debug : mdata : "+ mdata + "-- type " + block.getType());
				block.setData(mdata);	
				event.setCancelled(true);
				
				if(parent.showProgress && obc.damage > 0) {
				event.getPlayer().sendMessage("Block " + Math.round(100 * ((double) newHealth / (double) ToughBlocks.MAX_BLOCK_HEALTH)) + "% intact...");
				}
			}
			break;
		}
	}
}
