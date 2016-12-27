package net.gudenau.lib.core;

import net.gudenau.lib.api.item.IItemSpecialTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by gudenau on 12/26/2016.
 * <p>
 * lib
 */
public class ClientHooks {
    public static void preItemTileRender(ItemStack stack) {
        Item item = stack.getItem();
        if(item instanceof IItemSpecialTile){
            ((IItemSpecialTile)item).preItemTileRender(stack);
        }
    }

    public static void postItemTileRender(ItemStack stack) {
        Item item = stack.getItem();
        if(item instanceof IItemSpecialTile){
            ((IItemSpecialTile)item).postItemTileRender(stack);
        }
    }
}
