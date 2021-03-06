
package AFKplayers;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

public class AFKplayer implements IAFKplayer {

	public Entity specEntity;
	private GameMode playerGameMode;
	private Player player;
	
	public AFKplayer(Player player, GameMode mode) { 
		
		this.player = player;
		this.playerGameMode = mode;
		
		specEntity = player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
		specEntity.setInvulnerable(true);
		specEntity.setGravity(false);
		specEntity.setCustomName(player.getCustomName());
		specEntity.setCustomNameVisible(true);
		specEntity.setVelocity(new Vector());
		
		ArmorStand as = (ArmorStand)specEntity;
		as.setBasePlate(false);
		as.setArms(true);
		as.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		as.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		as.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta iceMeta = ice.getItemMeta();
		iceMeta.setDisplayName("AFK");
		ice.setItemMeta(iceMeta);
		as.setItemInHand(ice);
		as.setCollidable(false);
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta sm = (SkullMeta)skull.getItemMeta();
		sm.setOwner(player.getName());
		sm.setDisplayName(player.getName());
		skull.setItemMeta(sm);
		
		as.setHelmet(skull);
		
		player.setGameMode(GameMode.SPECTATOR);
		player.setSpectatorTarget(specEntity);
	}
	
	@Override
	public final GameMode getGameMode() { return playerGameMode; }
	
	@Override
	public final Player getPlayer() { return player; }
	
	@Override
	public final Entity getEntity() { return specEntity; }
	
	@Override
	public boolean equals(final Object obj) {
		
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) 
			if(!(obj instanceof Player))
				return false;
		
		Player rhsPlayer;
		if(obj instanceof AFKplayer) {
			rhsPlayer = ((AFKplayer)obj).getPlayer();
		}
		else {
			rhsPlayer = (Player)obj;
		}
		
		if(!player.equals(rhsPlayer))
			return false;

		return true;
	}
	
	@Override
	public int hashCode() {
		
		return player.hashCode();
	}
}
