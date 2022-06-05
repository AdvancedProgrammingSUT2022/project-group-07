package game.Enum;


//TODO felan HP hamaro 10 gozashtam !!!
//TODO felan turnsNeedToBeCreated hamaro 0 gozashtam !!!
//TODO bakhshe combatRange UNIT haye (TANK va PANZER) niaz be barresi bishtar!!
//TODO ya ayoub ya hichkase Dge

public enum TypeOfUnit {

    ARCHER("archer", 70, CombatType.ARCHERY, 4, 6, 2, 2, 10, null, TypeOfTechnology.ARCHERY, 0),
    CHARIOT_ARCHER("chariotArcher", 60, CombatType.MOUNTED, 3, 6, 2, 4, 10, Resources.HORSE, TypeOfTechnology.THE_WHEEL, 0),
    SCOUT("scout", 25, CombatType.RECON, 4, 0, 0, 2, 10, null, null, 0),
    SETTLER("settler", 89, CombatType.CIVILIAN, 0, 0, 0, 2, 10, null, null, 0),
    SPEARMAN("spearman", 50, CombatType.MELEE, 7, 0, 0, 2, 10, null, TypeOfTechnology.BRONZE_WORKING, 0),
    WARRIOR("warrior", 40, CombatType.MELEE, 6, 0, 0, 2, 10, null, null, 0),
    WORKER("worker", 70, CombatType.CIVILIAN, 0, 0, 0, 2, 10, null, null, 0),
    CATAPULT("catapult", 100, CombatType.SIEGE, 4, 14, 2, 2, 10, Resources.IRON, TypeOfTechnology.MATHEMATICS, 0),
    HORSEMAN("horseman", 80, CombatType.MOUNTED, 12, 0, 0, 4, 10, Resources.HORSE, TypeOfTechnology.HORSEBACK_RIDING, 0),
    SWORDSMAN("swordsman", 80, CombatType.MELEE, 11, 0, 0, 2, 10, Resources.IRON, TypeOfTechnology.IRON_WORKING, 0),
    CROSSBOWMAN("crossbowman", 120, CombatType.ARCHERY, 6, 12, 2, 2, 10, null, TypeOfTechnology.MACHINERY, 0),
    KNIGHT("knight", 150, CombatType.MOUNTED, 18, 0, 0, 3, 10, Resources.HORSE, TypeOfTechnology.CHIVALRY, 0),
    LONG_SWORDSMAN("longSwordsman", 150, CombatType.MELEE, 18, 0, 0, 3, 10, Resources.IRON, TypeOfTechnology.STEEL, 0),
    PIKEMAN("pikeman", 100, CombatType.MELEE, 10, 0, 0, 2, 10, null, TypeOfTechnology.CIVIL_SERVICE, 0),
    TREBUCHET("trebuchet", 170, CombatType.SIEGE, 6, 20, 2, 2, 10, Resources.IRON, TypeOfTechnology.PHYSICS, 0),
    CANON("canon", 250, CombatType.SIEGE, 10, 26, 2, 2, 10, null, TypeOfTechnology.CHEMISTRY, 0),
    CAVALRY("cavalry", 260, CombatType.MOUNTED, 25, 0, 0, 3, 10, Resources.HORSE, TypeOfTechnology.MILITARY_SCIENCE, 0),
    LANCER("lancer", 220, CombatType.MOUNTED, 22, 0, 0, 4, 10, Resources.HORSE, TypeOfTechnology.METALLURGY, 0),
    MUSKETMAN("musketman", 120, CombatType.GUNPOWDER, 16, 0, 0, 2, 10, null, TypeOfTechnology.GUNPOWDER, 0),
    RIFLEMAN("rifleman", 200, CombatType.GUNPOWDER, 25, 0, 0, 2, 10, null, TypeOfTechnology.RIFLING, 0),
    ANTI_TANK_GUN("antiTankGun", 300, CombatType.GUNPOWDER, 32, 0, 0, 2, 10, null, TypeOfTechnology.REPLACEABLE_PARTS, 0),
    ARTILLERY("artillery", 420, CombatType.SIEGE, 16, 32, 3, 2, 10, null, TypeOfTechnology.DYNAMITE, 0),
    INFANTRY("infantry", 300, CombatType.GUNPOWDER, 36, 0, 0, 2, 10, null, TypeOfTechnology.REPLACEABLE_PARTS, 0),
    PANZER("panzer", 450, CombatType.ARMORED, 60, 1000, 0, 5, 10, null, TypeOfTechnology.COMBUSTION, 0),
    TANK("tank", 450, CombatType.ARMORED, 50, 1000, 0, 4, 10, null, TypeOfTechnology.COMBUSTION, 0);


    private String name;
    private int cost;
    private CombatType combatType;
    private int combatStrength;
    private int rangedCombatStrength;
    private int range;
    private int mp;
    private int hp;
    private Resources resources;
    private TypeOfTechnology technologyRequired;
    private int turnsNeededToCreate;

    TypeOfUnit(String name, int cost, CombatType combatType, int combatStrength, int rangedCombatStrength, int range, int mp, int hp, Resources resources, TypeOfTechnology technologyRequired, int turnsNeededToCreate) {
        this.name = name;
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.mp = mp;
        this.hp = hp;
        this.resources = resources;
        this.technologyRequired = technologyRequired;
        this.turnsNeededToCreate = turnsNeededToCreate;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public CombatType getCombatType() {
        return combatType;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public int getRangedCombatStrength() {
        return rangedCombatStrength;
    }

    public int getRange() {
        return range;
    }

    public int getMp() {
        return mp;
    }

    public int getHp() {
        return hp;
    }

    public Resources getResources() {
        return resources;
    }

    public TypeOfTechnology getTechnologyRequired() {
        return technologyRequired;
    }

    public int getTurnsNeededToCreate() {
        return turnsNeededToCreate;
    }

    public void setTurnsNeededToCreate(int turnsNeededToCreate) {
        this.turnsNeededToCreate = turnsNeededToCreate;
    }
}
