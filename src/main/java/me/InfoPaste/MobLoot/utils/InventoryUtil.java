package me.InfoPaste.MobLoot.utils;

public class InventoryUtil {

    /*
     * Converts number to how many rows (divisible by 9)
     */
    public static int convertToRows(int amount) {
        return (int) (((double) amount / 9.0) + 1) * 9;
    }
}
