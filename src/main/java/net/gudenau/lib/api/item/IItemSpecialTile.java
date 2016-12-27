package net.gudenau.lib.api.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by gudenau on 12/26/2016.
 * <p>
 * lib
 */
public interface IItemSpecialTile {
    @SideOnly(Side.CLIENT)
    default void preItemTileRender(ItemStack stack){}

    @SideOnly(Side.CLIENT)
    default void postItemTileRender(ItemStack stack){};
}
