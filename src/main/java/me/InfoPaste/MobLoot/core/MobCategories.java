package me.InfoPaste.MobLoot.core;

import me.InfoPaste.MobLoot.objects.ItemStackBuilder;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public enum MobCategories {

    PASSIVE("Passive", ChatColor.GREEN) {
        @Override
        public List<EntityType> getMobs() {

            String[] mobList = {
                    "BAT",
                    "CHICKEN",
                    "COW",
                    "MUSHROOM_COW",
                    "PIG",
                    "RABBIT",
                    "SHEEP",
                    "SKELETON_HORSE",
                    "SQUID",
                    "VILLAGER"
            };

            return stringToEntityType(mobList);
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();

            for (EntityType entityType : getMobs()) {
                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 5).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    },

    NEUTRAL("Neutral", ChatColor.WHITE) {
        @Override
        public List<EntityType> getMobs() {

            String[] mobList = {
                    "CAVE_SPIDER",
                    "ENDERMAN",
                    "POLAR_BEAR",
                    "SPIDER",
                    "PIG_ZOMBIE"
            };

            return stringToEntityType(mobList);
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();
            for (EntityType entityType : getMobs()) {

                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 0).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    },

    HOSTILE("Hostile", ChatColor.RED) {
        @Override
        public List<EntityType> getMobs() {

            String[] mobList = {
                    "BLAZE",
                    "CREEPER",
                    "ENDER_GUARDIAN",
                    "ENDERMITE",
                    "EVOKER",
                    "GHAST",
                    "GUARDIAN",
                    "HUSK",
                    "MAGMA_CUBE",
                    "SHULKER",
                    "SILVERFISH",
                    "SKELETON",
                    "SLIME",
                    "STRAY",
                    "VEX",
                    "VINDICATOR",
                    "WITCH",
                    "WITHER_SKELETON",
                    "ZOMBIE",
                    "ZOMBIE_VILLAGER"
            };

            return stringToEntityType(mobList);
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();
            for (EntityType entityType : getMobs()) {
                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 14).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    },

    TAMEABLE("Tameable", ChatColor.GOLD) {
        @Override
        public List<EntityType> getMobs() {

            String[] mobList = {
                    "DONKEY",
                    "HORSE",
                    "LLAMA",
                    "MULE",
                    "OCELOT",
                    "WOLF"
            };

            return stringToEntityType(mobList);
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();
            for (EntityType entityType : getMobs()) {
                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 3).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    },

    UTILITY("Utility", ChatColor.YELLOW) {
        @Override
        public List<EntityType> getMobs() {

            String[] mobList = {
                    "IRON_GOLEM",
                    "SNOWMAN"
            };

            return stringToEntityType(mobList);
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();
            for (EntityType entityType : getMobs()) {
                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 4).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    },

    BOSS("Boss", ChatColor.LIGHT_PURPLE) {
        @Override
        public List<EntityType> getMobs() {

            String[] mobList = {
                    "ENDER_DRAGON",
                    "WITHER",
                    "ELDER_GUARDIAN"
            };

            return stringToEntityType(mobList);
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();
            for (EntityType entityType : getMobs()) {
                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 7).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    },

    UNUSED("Unused", ChatColor.AQUA) {
        @Override
        public List<EntityType> getMobs() {

            String[] mobList = {
                    "GIANT",
                    "ZOMBIE_HORSE"
            };

            return stringToEntityType(mobList);
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();
            for (EntityType entityType : getMobs()) {
                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 1).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    },

    UNCATEGORIZED("Uncategorized", ChatColor.BLACK) {
        @Override
        public List<EntityType> getMobs() {

            List<EntityType> categorized = new ArrayList<EntityType>();
            List<EntityType> uncategorized = new ArrayList<EntityType>();

            for (MobCategories mobCategories : MobCategories.values()) {
                if (mobCategories != MobCategories.UNCATEGORIZED) {
                    categorized.addAll(mobCategories.getMobs());
                }
            }

            for (EntityType entityType : EntityType.values()) {
                if (entityType.isAlive() && !categorized.contains(entityType)) {
                    uncategorized.add(entityType);
                }
            }

            return uncategorized;
        }

        @Override
        public ItemStack getIcon() {

            List<String> array = new ArrayList<String>();
            for (EntityType entityType : getMobs()) {
                array.add("&7" + WordUtils.capitalize(entityType.name().replaceAll("_", " ").toLowerCase()));
            }

            ItemStackBuilder icon = new ItemStackBuilder(Material.STAINED_GLASS_PANE, 1).hideAttributes().withLore(array).withName(getFormattedName());

            return icon.build();
        }
    };

    private String name;
    private ChatColor chatColor;

    MobCategories(String name, ChatColor chatColor) {
        this.name = name;
        this.chatColor = chatColor;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return chatColor;
    }

    public String getFormattedName() {
        return chatColor + name + " Mobs";
    }

    public abstract List<EntityType> getMobs();

    public abstract ItemStack getIcon();

    private static List<EntityType> stringToEntityType(String[] names) {

        List<EntityType> entityTypes = new ArrayList<EntityType>();
        for (String name : names) {
            for (EntityType entityType : EntityType.values()) {
                if (!entityTypes.contains(entityType)
                        && entityType.isAlive()) {

                    if (entityType.toString().equalsIgnoreCase(name)) {
                        entityTypes.add(EntityType.valueOf(name.toUpperCase()));
                    }
                }
            }
        }
        return entityTypes;
    }

    @Nullable
    public static MobCategories getCategory(String string) {

        for (MobCategories mobCategories : MobCategories.values()) {
            if (mobCategories.getName().equalsIgnoreCase(string)) {
                return mobCategories;
            }
        }

        return null;
    }

    public static MobCategories getCategory(EntityType entityType) {
        if (PASSIVE.getMobs().contains(entityType)) {
            return PASSIVE;
        } else if (HOSTILE.getMobs().contains(entityType)) {
            return HOSTILE;
        } else if (NEUTRAL.getMobs().contains(entityType)) {
            return NEUTRAL;
        } else if (BOSS.getMobs().contains(entityType)) {
            return BOSS;
        } else if (TAMEABLE.getMobs().contains(entityType)) {
            return TAMEABLE;
        } else if (UNUSED.getMobs().contains(entityType)) {
            return UNUSED;
        } else if (UTILITY.getMobs().contains(entityType)) {
            return UTILITY;
        } else {
            return UNCATEGORIZED;
        }
    }

    public static int catigories() {

        int size = 8;

        if (UNCATEGORIZED.getMobs().size() > 0) {
            size++;
        }

        return size;
    }
}
