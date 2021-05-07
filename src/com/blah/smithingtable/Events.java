package com.blah.smithingtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Events implements Listener {
	private List<LivingEntity> cantJump;
	LivingEntity entityWasDamaged;
	ItemStack axeUsed;
	ItemStack result;
	ItemMeta resultMeta;
	List<String> lore;
	List<Player> cantTakeExplosionDamage;
	private void setLore(String lore) {
		if (resultMeta.getLore() != null) {
			if (!resultMeta.getLore().contains(lore)) {
				this.lore = resultMeta.getLore();
				this.lore.add(lore);
				resultMeta.setLore(this.lore);
				result.setItemMeta(resultMeta);
			}
		} else {
			resultMeta.setLore(Arrays.asList(lore));
			result.setItemMeta(resultMeta);
		}
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();
			entityWasDamaged = (LivingEntity) e.getEntity();
			if (damager.getInventory().getItemInMainHand().getType().toString().endsWith("_AXE")) {
				axeUsed = damager.getInventory().getItemInMainHand();
				if (damager.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
					if (axeUsed.getItemMeta().getLore().contains("Bones")) {
						entityWasDamaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 10));
						cantJump = new ArrayList<>();
						cantJump.add(entityWasDamaged);
						Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
							@Override
							public void run() {
								cantJump.remove(entityWasDamaged);
							}
						}, 100L);
					}
					if (axeUsed.getItemMeta().getLore().contains("Exploder")) {
						cantTakeExplosionDamage = new ArrayList<>();
						cantTakeExplosionDamage.add(damager);
						entityWasDamaged.getWorld().createExplosion(entityWasDamaged.getLocation(), 5, false);
						cantTakeExplosionDamage.remove(damager);
					}
					if (axeUsed.getItemMeta().getLore().contains("SpiderEye")) {
						entityWasDamaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 10));
						entityWasDamaged.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 10));
					}
					if (axeUsed.getItemMeta().getLore().contains("RottenFlesh")) {
						entityWasDamaged.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 10));
						entityWasDamaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
					}
					if (axeUsed.getItemMeta().getLore().contains("GhastTear")) {
						entityWasDamaged.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 10));
					}
					if (axeUsed.getItemMeta().getLore().contains("Teleporter")) {
						Random rand = new Random();
						int x = entityWasDamaged.getLocation().getBlockX() + rand.nextInt(20) - 10;
						int y = entityWasDamaged.getLocation().getBlockY();
						int z = entityWasDamaged.getLocation().getBlockZ() + rand.nextInt(20) - 10;
						entityWasDamaged.teleport(entityWasDamaged.getWorld().getBlockAt(x, y, z).getLocation());
					}
				}
			}
		}
	}
	@EventHandler
	public void onJump(PlayerMoveEvent e) {
		if (cantJump != null && entityWasDamaged != null) {
			if (cantJump.contains(entityWasDamaged) && e.getPlayer() == entityWasDamaged) {
				if (e.getFrom().getY() < e.getTo().getY() && !entityWasDamaged.isSwimming() && entityWasDamaged.getLocation().getBlock().getType() != Material.LADDER) {
					e.setCancelled(true);
					entityWasDamaged.sendMessage(ChatColor.GREEN + "You can't jump, since your 'bones are broken :)'");
				}
			}
		}
	}
	@EventHandler
	public void onExplosionDamage(EntityDamageEvent e) {
		if (cantTakeExplosionDamage != null) {
			if (e.getEntity() instanceof Player) {
				if (cantTakeExplosionDamage.contains((Player) e.getEntity())) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onSmith(PrepareSmithingEvent e) {
		if (e.getInventory().getItem(0) != null && e.getInventory().getItem(1) != null) {
			if (e.getInventory().getItem(1).getType().toString().endsWith("_AXE")) {
				result = e.getInventory().getItem(1);
				resultMeta = result.getItemMeta();
				switch (e.getInventory().getItem(0).getType()) {
				case BLAZE_POWDER:
					result.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
					break;
				case BONE:
					result.addUnsafeEnchantment(Main.boneEnchant, 1);
					setLore("Bones");
					break;
				case GUNPOWDER:
					result.addUnsafeEnchantment(Main.gunpowderEnchant, 1);
					setLore("Exploder");
					break;
				case SPIDER_EYE:
					result.addUnsafeEnchantment(Main.spiderEyeEnchant, 1);
					setLore("SpiderEye");
					break;
				case ROTTEN_FLESH:
					result.addUnsafeEnchantment(Main.rottenFleshEnchant, 1);
					setLore("RottenFlesh");
					break;
				case GHAST_TEAR:
					result.addUnsafeEnchantment(Main.ghastTearEnchant, 1);
					result.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
					setLore("GhastTear");
					break;
				case ENDER_PEARL:
					result.addUnsafeEnchantment(Main.enderpearlEnchant, 1);
					setLore("Teleporter");
				default:
					break;
				}
				e.setResult(result);
			}
		}
	}
}
