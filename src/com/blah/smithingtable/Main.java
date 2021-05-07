package com.blah.smithingtable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static Main main;
	public static BonesEnchant boneEnchant;
	public static GunpowderEnchant gunpowderEnchant;
	public static RottenFleshEnchant rottenFleshEnchant;
	public static SpiderEyeEnchant spiderEyeEnchant;
	public static GhastTearEnchant ghastTearEnchant;
	public static EnderpearlEnchant enderpearlEnchant;
	private List<Enchantment> enchantments;
	public int recipeCounter;
	@Override
	public void onEnable() {
		enchantments = new ArrayList<>();
		main = this;
		recipeCounter = 0;
		boneEnchant = new BonesEnchant();
		gunpowderEnchant = new GunpowderEnchant();
		rottenFleshEnchant = new RottenFleshEnchant();
		spiderEyeEnchant = new SpiderEyeEnchant();
		ghastTearEnchant = new GhastTearEnchant();
		enderpearlEnchant = new EnderpearlEnchant();
		new Recipes();
		enchantments.add(boneEnchant); enchantments.add(gunpowderEnchant); enchantments.add(rottenFleshEnchant); enchantments.add(spiderEyeEnchant); enchantments.add(ghastTearEnchant); enchantments.add(enderpearlEnchant);
		for (Enchantment ench : enchantments) {
			try {
				Field field = Enchantment.class.getDeclaredField("acceptingNew");
				field.setAccessible(true);
				field.set(null, true);
				Enchantment.registerEnchantment(ench);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Bukkit.getPluginManager().registerEvents(new Events(), this);
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void onDisable() {
		for (Enchantment ench : enchantments) {
			try {
				Field keyField = Enchantment.class.getDeclaredField("byKey");
				keyField.setAccessible(true);
				HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);
				if (byKey.containsKey(ench.getKey())) {
					byKey.remove(ench.getKey());
				}
				Field nameField = Enchantment.class.getDeclaredField("byName");
				nameField.setAccessible(true);
				HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);
				if (byName.containsKey(ench.getName())) {
					byName.remove(ench.getName());
				}
			} catch (Exception e) { }
		}
	}
	public static Main getInstance() { return main; }
}
