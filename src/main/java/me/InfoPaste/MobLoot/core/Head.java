package me.InfoPaste.MobLoot.core;

import org.bukkit.entity.EntityType;

public enum Head {

    ARMOR_STAND(null),
    BAT(null),
    BLAZE("MHF_Blaze"),
    CAVE_SPIDER(null),
    CHICKEN("MHF_Chicken"),
    COW("MHF_Cow"),
    CREEPER("MHF_Creeper"),
    DONKEY(null),
    ELDER_GUARDIAN(null),
    ENDER_DRAGON("KingEndermen"),
    ENDERMAN("MHF_Enderman"),
    ENDERMITE(null),
    EVOKER("MHF_Evoker"),
    GHAST("MHF_Ghast"),
    GIANT("MHF_Zombie"),
    GUARDIAN("Guardian"),
    HORSE(null),
    HUSK(null),
    IRON_GOLEM(null),
    LLAMA(null),
    MAGMA_CUBE(null),
    MULE(null),
    MUSHROOM_COW(null),
    OCELOT(null),
    PIG("MHF_Pig"),
    PIG_ZOMBIE(null),
    PLAYER(null),
    POLAR_BEAR("Bear"),
    RABBIT(null),
    SHEEP(null),
    SHULKER(null),
    SILVERFISH(null),
    SKELETON("MHF_Skeleton"),
    SKELETON_HORSE(null),
    SLIME("MHF_Slime"),
    SNOWMAN(null),
    SPIDER("MHF_Spider"),
    SQUID(null),
    STRAY(null),
    UNKNOWN("Agusrodri09"),
    VEX("MHF_Vex"),
    VILLAGER(null),
    VINDICATOR(null),
    WEATHER(null),
    WITCH(null),
    WITHER("MHF_Wither"),
    WITHER_SKELETON(null),
    WOLF("MHF_Wolf"),
    ZOMBIE("MHF_Zombie"),
    ZOMBIE_HORSE(null),
    ZOMBIE_VILLAGER(null);

    private String head;

    Head(String head) {
        this.head = head;
    }

    public String getHead() {
        if (head == null || head == "") {
            return UNKNOWN.getHead();
        }
        return head;
    }

    public static String getHeadOf(EntityType entity) {

        for (Head head : Head.values()) {
            if (head.name().equalsIgnoreCase(entity.name())) {
                return head.getHead();
            }
        }

        return UNKNOWN.getHead();
    }

}
