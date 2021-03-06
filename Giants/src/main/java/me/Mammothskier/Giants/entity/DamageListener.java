package me.Mammothskier.Giants.entity;

import me.Mammothskier.Giants.Giants;
import me.Mammothskier.Giants.files.Files;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DamageListener implements Listener	{
	private Giants _giants;
	public DamageListener(Giants giants) {
		_giants = giants;
		_giants.getServer().getPluginManager().registerEvents(this, giants);
	}
	
	@EventHandler 
	public void onEntityDamageEvent(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (Entities.isGiant(entity)) {
			if (event.getCause() == DamageCause.SUFFOCATION || event.getCause() == DamageCause.FALLING_BLOCK) {
				String string = "true";
				if (Entities.isGiantZombie(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Block Damage.Allow Suffocation.Giant Zombie");
				else if (Entities.isGiantSlime(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Block Damage.Allow Suffocation.Giant Slime");
				else if (Entities.isGiantLavaSlime(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Block Damage.Allow Suffocation.Giant Lava Slime");
				
				if (string.equalsIgnoreCase("false")) {
					event.setCancelled(true);
				}
			}
			
			if (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK) {
				String string = "true";
				if (Entities.isGiantZombie(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Fire.Allow Fire Damage.Giant Zombie");
				else if (Entities.isGiantSlime(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Fire.Allow Fire Damage.Giant Slime");
				if (string.equalsIgnoreCase("false")) {
					event.setCancelled(true);
				}
			}
			if (event.getCause() == DamageCause.THORNS) {
				String string = "true";
				if (Entities.isGiantZombie(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Block Damage.Allow Cacti Damage.Giant Zombie");
				else if (Entities.isGiantSlime(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Block Damage.Allow Cacti Damage.Giant Slime");
				else if (Entities.isGiantLavaSlime(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Block Damage.Allow Cacti Damage.Giant Lava Slime");
				if (string.equalsIgnoreCase("false")) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	public void onArrowDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if (Entities.isGiant(entity)) {
			if (event.getDamager() instanceof Arrow){
				int damage;
				String string = "";
				if (Entities.isGiantZombie(entity))
				string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Arrows.Damage done by arrow.Giant Zombie");
				else if (Entities.isGiantSlime(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Arrows.Damage done by arrow.Giant Slime");
				else if (Entities.isGiantSlime(entity))
					string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Damage Settings.Arrows.Damage done by arrow.Giant Lava Slime");
				try {
					damage = Integer.parseInt(string);
				} catch (Exception e) {
					damage = 10;
				}
				if(damage == 0){
					event.setCancelled(true);
					return;
				}
				event.setDamage(damage + 0.0);
			}
		}
	}
	
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent event){
		Entity entity = event.getEntity();
		int s;
		if ((event.getDamager() instanceof Player) && (Entities.isGiantLavaSlime(entity))){
			MagmaCube magmacube = (MagmaCube) event.getEntity();
			s = magmacube.getSize();
			if (s > 4){
				double damage = event.getDamage();
				double health =  ((Damageable) event.getEntity()).getHealth();
				((Damageable) entity).setHealth(Math.max(0, Math.min(health - damage, ((Damageable) entity).getMaxHealth())));
				event.setCancelled(true);
			}
		}
	}
}
