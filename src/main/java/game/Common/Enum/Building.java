package game.Common.Enum;

public enum Building {

    BARRACKS(80 , 1 , TypeOfTechnology.BRONZE_WORKING),
    GRANARY(100 , 1 , TypeOfTechnology.POTTERY),
    LIBRARY(80 ,  1 , TypeOfTechnology.WRITING),
    MONUMENT(60 , 1 , null),
    WALLS(100 , 1 , TypeOfTechnology.MASONRY),
    WATER_MILL(120 , 2 , TypeOfTechnology.THE_WHEEL),
    ARMORY(130 , 3 , TypeOfTechnology.IRON_WORKING),
    BURIAL_TOMB(120 , 0 , TypeOfTechnology.PHILOSOPHY),
    CIRCUS(150 , 3, TypeOfTechnology.HORSEBACK_RIDING),
    COLOSSEUM(150 , 3 , TypeOfTechnology.CONSTRUCTION),
    COURTHOUSE(200 , 5 , TypeOfTechnology.MATHEMATICS),
    STABLE(100 , 1 , TypeOfTechnology.HORSEBACK_RIDING),
    TEMPLE(120 , 2 , TypeOfTechnology.PHILOSOPHY),
    CASTLE(200 , 3 , TypeOfTechnology.CHIVALRY),
    FORGE(150 , 2 , TypeOfTechnology.METAL_CASTING),
    GARDEN(120 , 2 , TypeOfTechnology.THEOLOGY),
    MARKET(120 , 0 , TypeOfTechnology.CURRENCY),
    MINT(120 , 0 , TypeOfTechnology.CURRENCY),
    MONASTERY(120 , 2 , TypeOfTechnology.THEOLOGY),
    UNIVERSITY(200 , 3, TypeOfTechnology.EDUCATION),
    WORKSHOP(100 , 2 , TypeOfTechnology.METAL_CASTING),
    BANK(220 , 0 , TypeOfTechnology.BANKING),
    MILITARY_ACADEMY(350 , 3 , TypeOfTechnology.MILITARY_SCIENCE),
    MUSEUM(350 , 3 , TypeOfTechnology.ARCHAEOLOGY),
    OPERA_HOUSE(220 , 3 , TypeOfTechnology.ACOUSTICS),
    PUBLIC_SCHOOL(350 , 3 , TypeOfTechnology.SCIENTIFIC_THEORY),
    SATRAPS_COURT(220 , 0 , TypeOfTechnology.BANKING),
    THEATER(300 , 5 , TypeOfTechnology.PRINTING_PRESS),
    WINDMILL(180 , 2 , TypeOfTechnology.ECONOMICS),
    ARSENAL(350 , 3 , TypeOfTechnology.RAILROAD),
    BROADCAST_TOWER(600 , 3 , TypeOfTechnology.RADIO),
    FACTORY(300 , 3 , TypeOfTechnology.STEAM_POWER),
    HOSPITAL(400 , 2 , TypeOfTechnology.BIOLOGY),
    MILITARY_BASE(450 , 4, TypeOfTechnology.TELEGRAPH),
    STOCK_EXCHANGE(650 , 0 , TypeOfTechnology.ELECTRICITY);

    private int cost;
    private int maintenance;
    private TypeOfTechnology technologyRequired;

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public TypeOfTechnology getTechnologyRequired() {
        return technologyRequired;
    }

    Building(int cost, int maintenance, TypeOfTechnology technologyRequired) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.technologyRequired = technologyRequired;
    }
}
