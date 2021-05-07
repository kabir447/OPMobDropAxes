package com.blah.smithingtable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

public class Recipes {
	private List<Material> axes;
	public Recipes() {
		axes = new ArrayList<>();
		axes.add(Material.WOODEN_AXE);
		axes.add(Material.STONE_AXE);
		axes.add(Material.IRON_AXE);
		axes.add(Material.GOLDEN_AXE);
		axes.add(Material.DIAMOND_AXE);
		axes.add(Material.NETHERITE_AXE);
		//blaze
		for (int i = 0; i < 6; i++) {
			Main.getInstance().getServer().addRecipe(new SmithingRecipe(new NamespacedKey(Main.getInstance(), "blaze" + i), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.BLAZE_POWDER), new RecipeChoice.MaterialChoice(axes.get(i))));
		}
		//bone
		for (int i = 0; i < 6; i++) {
			Main.getInstance().getServer().addRecipe(new SmithingRecipe(new NamespacedKey(Main.getInstance(), "bone" + i), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.BONE), new RecipeChoice.MaterialChoice(axes.get(i))));
		}
		// gunpowder
		for (int i = 0; i < 6; i++) {
			Main.getInstance().getServer().addRecipe(new SmithingRecipe(new NamespacedKey(Main.getInstance(), "gunpowder" + i), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.GUNPOWDER), new RecipeChoice.MaterialChoice(axes.get(i))));
		}
		//spider eye
		for (int i = 0; i < 6; i++) {
			Main.getInstance().getServer().addRecipe(new SmithingRecipe(new NamespacedKey(Main.getInstance(), "spidereye" + i), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.SPIDER_EYE), new RecipeChoice.MaterialChoice(axes.get(i))));
		}
		//rottenFlesh
		for (int i = 0; i < 6; i++) {
			Main.getInstance().getServer().addRecipe(new SmithingRecipe(new NamespacedKey(Main.getInstance(), "rottenflesh" + i), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.ROTTEN_FLESH), new RecipeChoice.MaterialChoice(axes.get(i))));
		}
		// ghast tear
		for (int i = 0; i < 6; i++) {
			Main.getInstance().getServer().addRecipe(new SmithingRecipe(new NamespacedKey(Main.getInstance(), "ghasttear" + i), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.GHAST_TEAR), new RecipeChoice.MaterialChoice(axes.get(i))));
		}
		//enderpearl
		for (int i = 0; i < 6; i++) {
			Main.getInstance().getServer().addRecipe(new SmithingRecipe(new NamespacedKey(Main.getInstance(), "teleporter" + i), new ItemStack(Material.AIR), new RecipeChoice.MaterialChoice(Material.ENDER_PEARL), new RecipeChoice.MaterialChoice(axes.get(i))));
		}
	}
}
