package me.nikl.gamebox.guis.shop;

import me.nikl.gamebox.ClickAction;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.guis.GUIManager;
import me.nikl.gamebox.guis.button.AButton;
import me.nikl.gamebox.util.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Niklas on 13.04.2017.
 *
 * GUI containing all shop categories
 */
class MainShop extends Shop {

    MainShop(GameBox plugin, GUIManager guiManager, int slots, ShopManager shopManager, String[] args) {
        super(plugin, guiManager, slots, shopManager, args, plugin.lang.SHOP_TITLE_MAIN_SHOP);

        loadCategories();
    }



    private void loadCategories() {
        List<String> lore;
        ItemStack buttonItem;
        for(String cat : shop.getConfigurationSection("shop.categories").getKeys(false)){
            ConfigurationSection category = shop.getConfigurationSection("shop.categories." + cat);
            buttonItem = ItemStackUtil.getItemStack(category.getString("materialData"));

            if(buttonItem == null){
                Bukkit.getLogger().log(Level.WARNING, " error loading:   shop.categories." + cat);
                Bukkit.getLogger().log(Level.WARNING, "     invalid material data");
                continue;
            }
            if(category.getBoolean("glow")){
                buttonItem = plugin.getNMS().addGlow(buttonItem);
            }
            AButton button =  new AButton(buttonItem);
            ItemMeta meta = button.getItemMeta();

            if(category.isString("displayName")){
                meta.setDisplayName(GameBox.chatColor(category.getString("displayName")));
            }

            if(category.isList("lore")){
                lore = new ArrayList<>(category.getStringList("lore"));
                for(int i = 0; i < lore.size();i++){
                    lore.set(i, GameBox.chatColor(lore.get(i)));
                }
                meta.setLore(lore);
            }

            button.setItemMeta(meta);
            button.setAction(ClickAction.OPEN_SHOP_PAGE);
            button.setArgs(cat, "0");

            setButton(button);

            shopManager.loadCategory(cat);
        }
    }
}
