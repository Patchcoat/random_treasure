package com.metallicim.randomtreasure;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import javax.crypto.SealedObject;

/**
 * <h1>Treasure Builder Object</h1>
 * This object holds all of the functions for building all of the treasures. Each function returns
 * a TreasureComponent that has already had all of it's sub-components assembled together.
 *
 * @since 2020-1-26
 */
public class TreasureBuilder {

    /**
     * Generates a random integer between 0 and max exclusive
     * @param max the highest value to be generated
     * @return a random integer between 0 and max exclusive
     */
    public static int randomInt(int max) {
        return (int) ((max) * Math.random());
    }

    /**
     * Roll some number of d6 dice and return the sum
     * @param count the number of dice to roll
     * @return the total of all rolled dice added together
     */
    public static int rollD6(int count) {
        int total = 0;
        for (int i = 0; i < count; i++) {
            total += randomInt(6) + 1;
        }
        return total;
    }

    /**
     * Sum every element in an array
     * @param array an integer array
     * @return the sum of every element in an array
     */
    public static int ArraySum(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    /**
     * Sums only the beginning elements in an array
     * @param array an integer array
     * @param count the number of elements to sum, starting from the beginning
     * @return the sum of the first 'count' elements in the array
     */
    public static int PartialArraySum(int[] array, int count) {
        int sum = 0;
        for (int i = 0; i < count && i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    /**
     * The sum of all the contents of all the arrays
     * @param arrays an array of arrays containing integers
     * @return the sum of every element in every array
     */
    public static int SumArrays(int[][] arrays) {
        int sum = 0;
        for (int[] array : arrays) {
            sum += ArraySum(array);
        }
        return sum;
    }

    /**
     * Selects an index from an array, where each element has a weighted probability.
     * Imagine a deck of cards all laid out one right after the other. Each card maps to one index
     * in an array. If you were to select a card at random, you would select an index at random too,
     * and each index has the same chance of being picked: 1/n where n is the number of cards.\n
     * Now take some cards and stack them on top of other cards. Sometimes two or three cards will
     * map to a single index in an array. If you pick a card at random some indexes may have a
     * higher chance of being picked because they have more cards in them.\n
     * Now rather than actual cards, just keep the number of cards. That's how this function works.
     * @param probabilities an integer array of weighted probabilities
     * @param num the number to select
     * @return the index of the input array, or -1 if there's a problem
     */
    public static int selectFromProbabilityList(int[] probabilities, int num) {
        int total = 0;
        for (int category = 0; category < probabilities.length; category++) {
            total += probabilities[category];
            if (num < total) {
                return category;
            }
        }
        return -1;
    }

    /**
     * Build a jewel
     * @return the name, size, and cost of the jewel
     */
    public static TreasureComponent buildJewel() {
        String[] jewels = {"", "Agate", "Azurite", "Chalcedony", "Hematite", "Jade", "Jet", "Magnetite", "Malachite", "Obsidian", "Quartz", "Amber", "Amethyst", "Calcite",
                "Sard", "Coral", "Lapis Lazuli", "Onyx", "Tourmaline", "Turquoise", "Aquamarine", "Beryl", "Bloodstone", "Cat's Eye", "Emerald", "Garnet", "Iolite", "Moonstone",
                "Opal", "Pearl", "Peridot", "Ruby", "Sapphire", "Topaz", "Diamond"};
        int[] probabilities = {2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        double[] valueModifier = {0, 5, 10, 10, 5, 20, 10, 5, 15, 2, 15, 25, 30, 20, 25, 20, 25, 20, 25, 20, 30, 30, 30, 30, 35, 35, 30, 30, 35, 35, 30, 35, 35, 35, 40};

        TreasureComponent decoration = new TreasureComponent(0, TreasureComponentType.JEWEL);
        TreasureComponent weight = new TreasureComponent(1, TreasureComponentType.QUANTITY);

        int index = selectFromProbabilityList(probabilities, randomInt(ArraySum(probabilities)));

        int bigger = 0;
        while (index == 0) {
            index = selectFromProbabilityList(probabilities, randomInt(ArraySum(probabilities)));
            bigger++;
        }

        decoration.setName(jewels[index]);

        double weightValue = rollD6(2) / 4.0 + rollD6(bigger);
        weight.setName(weightValue + " carat");
        double costValue = (weightValue * weightValue + 4 * weightValue) * valueModifier[index] * 100.0;
        System.out.println(costValue);
        int cost = (int) (costValue);
        decoration.setCost(new Price(cost, 0));
        decoration.addComponent(weight);

        return decoration.assembleTreasure();
    }

    /**
     * What to prepend (or in the case of POSTWITH, append) to the description of an embellishment
     */
    private enum decorativePrepend {
        NONE,
        MADEOF,
        WITH,
        POSTWITH
    }
    /**
     * Build a soft embellishment
     * @param id since it's rare for an embellishment to be on it's own, it can take in an ID
     * @return the embellishment and cost factor
     */
    public static TreasureComponent buildSoftEmbellishment(int id) {
        String[] decorations = {"Fine Material", "Exceptional Material", "Dyed Cheaply", "Dyed", "Dyed Expensively", "Block Printing", "Resist Dyed", "Branding", "Cheap Patchwork", "Expensive Patchwork",
                "Cheap Fringe", "Expensive Fringe", "Minimal Lace", "Extensive Lace", "Simple Feathers", "Elaborate Feathers", "Cheap Fur Trim", "Expensive Fur Trim", "Minimal Cheap Beading",
                "Extensive Cheap Beading", "Minimal Expensive Beading", "Extensive Expensive Beading", "Expensive Bells", "Minimal Embroidery", "Extensive Embroidery", "Tattooed Minimally",
                "Tattooed Extensively", "Tapestry Weaving", "Quilting", "Patchwork Quilt"};
        int[] probabilities = {2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        double[] costFactors = {2, 19, 1.5, 4, 8, 0.5, 2.5, 0.5, 2, 5, 1, 6, 3.5, 9, 0.5, 4, 3, 8, 1.5, 4, 3, 7, 3, 10, 2, 5, 2, 6, 6, 4, 8};
        decorativePrepend[] prepend = {decorativePrepend.MADEOF, decorativePrepend.MADEOF, decorativePrepend.NONE, decorativePrepend.NONE, decorativePrepend.NONE, decorativePrepend.WITH,
                decorativePrepend.NONE, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH,
                decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH,
                decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.NONE, decorativePrepend.NONE,
                decorativePrepend.MADEOF, decorativePrepend.MADEOF, decorativePrepend.MADEOF};

        TreasureComponent decoration = new TreasureComponent(id, TreasureComponentType.SOFTEMBELLISHMENT);

        int index = selectFromProbabilityList(probabilities, randomInt(ArraySum(probabilities)));

        String name = decorations[index];
        switch(prepend[index]) {
            case MADEOF: {
                name = "made of " + name;
            } break;
            case WITH: {
                name = "with " + name;
            } break;
            default:
                break;
        }
        decoration.setName(name);
        decoration.setCost(new Price(0, costFactors[index]));

        return decoration;
    }
    /**
     * Build a hard embellishment
     * @param id since it's rare for an embellishment to be on it's own, it can take in an ID
     * @return the embellishment and cost factor or, for a jewel, the price
     */
    public static TreasureComponent buildHardEmbellishment(int id) {
        String[] decorations = {"Fine Material", "Exceptional Material", "Cheap Fringe", "Expensive Fringe", "Minimal Beads/Nails", "Extensive Beads/Nails", "Branding",
                "Minimal Painting/Enamel", "Extensive Painting/Enamel", "Minimal Relief", "Extensive Relief", "Minimal Cheap Inlay", "Extensive Cheap Inlay", "Minimal Expensive Inlay",
                "Extensive Expensive Inlay", "Silver Plating", "Guilding", "Jeweled"};
        int[] probabilities = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        double[] costFactors = {2, 19, 0.25, 0.5, 0.75, 2, 1, 2, 5, 1.5, 4, 2.5, 7, 6, 14, 2, 19, 0};
        decorativePrepend[] prepend = {decorativePrepend.MADEOF, decorativePrepend.MADEOF, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH,
                decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH,
                decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH, decorativePrepend.WITH,
                decorativePrepend.POSTWITH};

        TreasureComponent decoration = new TreasureComponent(id, TreasureComponentType.HARDEMBELLISHMENT);

        int index = selectFromProbabilityList(probabilities, randomInt(ArraySum(probabilities)));

        StringBuilder name = new StringBuilder(decorations[index]);
        switch(prepend[index]) {
            case MADEOF: {
                name.insert(0, "made of ");
            } break;
            case WITH: {
                name.insert(0, "with ");
            } break;
            case POSTWITH: {
                name.append(" with");
            }break;
            default:
                break;
        }

        // a Jewel is a special case
        if (decorations[index].equals("Jeweled")) {
            int count = 0;
            int cost = 0;
            name.append(" a");
            for (int i = randomInt(2) + 1; i > 0; i--) {
                count++;
                TreasureComponent jewel = buildJewel();

                name.append(" ").append(count > 1 ? "and " : "").append(jewel.name());
                cost += jewel.cost().value();
            }
            decoration.setCost(new Price(cost, 0));
        } else {
            decoration.setCost(new Price(0, costFactors[index]));
        }
        decoration.setName(name.toString());

        return decoration;
    }

    /**
     * Roll against the race table for the purpose of Contraband Leather
     * @return a fantasy race (including human)
     */
    public static String rollRaceLeather() {
        String[] races = {"Cat-Folk", "Coleopteran", "Corpse-Eater", "Dark One", "Dwarf", "Half-Elf", "High Elf", "Mountain Elf", "Sea Elf", "Shadow Elf", "Winged Elf", "Wood Elf", "Faun",
                "Leprecaun", "Nymph", "Pixie", "Gargoyle", "Gnome", "Goblin", "Half-Orc", "Hobgoblin", "Orc", "Halfling", "Celestial", "Elder-Spawn", "Infernal", "Human", "Air-Infused", "Earth-Infused",
                "Fire-Infused", "Water-Infused", "Minotaur", "Half-Ogre", "Ogre", "Dragon-Blooded", "Lizard Man", "Troll", "Wildman"};
        int[] probabilities = {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        return races[selectFromProbabilityList(probabilities, randomInt(ArraySum(probabilities)))];
    }

    /**
     * Build an animal, used for leather and fur in the buildFiber() function
     * @param type If SubTable.EXOTICANIMAL is specified it will use the Exotic Animal table, otherwise it will use the Common Animal table.
     * @return a Treasure Component with it's name equal to the randomly generated animal
     */
    public static TreasureComponent buildAnimal(SubTable type) {
        String[] commonAnimals = {"Seal", "Monkey", "Rabbit", "Fox", "Goat", "Horse", "Ox", "Deer", "Elk", "Reindeer", "Antelope", "Ibex"};
        String[] exoticAnimals = {"Sable", "Ermine", "Jaguar", "Lion", "Tiger", "Wolf", "Bear", "Wyvern", "Dire Wolf" , "Cave Bear", "Giant Ape", "Frost Snake"};
        String[] animals = commonAnimals;
        if (type == SubTable.EXOTICANIMAL) {
            animals = exoticAnimals;
        }
        TreasureComponent animal = new TreasureComponent(0, TreasureComponentType.TYPE);
        animal.setName(animals[randomInt(animals.length)]);

        return animal;
    }

    /**
     * Spices and Other Materials can be decorative, meaning their container is decorated. This function facilitates that.
     * @param contents what goes inside the container
     * @return the embellished container
     */
    public static TreasureComponent buildMaterialContainer(TreasureComponent contents) {
        TreasureComponent embellishment;
        boolean softMaterial = randomInt(2) == 0;
        if (softMaterial) { // flip a coin
            embellishment = buildSoftEmbellishment(2);
        } else {
            embellishment = buildHardEmbellishment(2);
        }
        TreasureComponent container = new TreasureComponent(3, TreasureComponentType.CONTAINER);
        container.setName("in a " + (softMaterial ? "Soft" : "Hard") + " Container");
        container.addComponent(contents);
        embellishment.addComponent(container);
        return embellishment;
    }

    /**
     * Build a spice. This includes the type and weight of the spice.
     * @return a TreasureComponent with weight and spice name set as the name, and the monetary
     *         value of the spice set as the cost.
     */
    public static TreasureComponent buildSpice() {
        // apply properties from the parent treasure table
        int properties = randomInt(12);
        int multiplier = 1;
        if (properties >= 6 && properties <= 8) {
            multiplier = 2;
        } else if (properties >= 9) {
            multiplier = 3;
        }
        int decorative = 0;
        if (properties == 5 || properties == 8 || properties == 11) {
            decorative = 1;
        }

        String[] spices = { "Allspice", "Anise", "Annatto", "Asafetida", "Cardamom", "Cassia", "Chiles", "Cinnamon", "Clove", "Coriander", "Cumin", "Dwarven Savory Fungus", "Elven Pepperbark",
                "Faerie Glimmerseed", "Fennel", "Fenugreek", "Ginger", "Halfling Savory", "Huajiao (Szechuan Pepper)", "Mace", "Mustard", "Nigella", "Nutmeg", "Onion Seed", "Orcish Firegrain",
                "Black Pepper", "White Pepper", "Poppy Seed", "Saffron", "Salt", "Salt, Black", "Salt, Red", "Sumac", "Tamarind", "Tumericc", "Zeodary"};
        // in dollars, multiply by 100 to get the component price.
        int[] prices = {150, 150, 113, 75, 150, 75, 38, 150, 150, 150, 150, 75, 38, 270, 75, 150, 38, 150, 150, 225, 38, 75, 150, 38, 150, 150, 188, 38, 300, 15, 38, 38, 38, 15, 38, 150};
        // generate the spice
        TreasureComponent spice = new TreasureComponent(0, TreasureComponentType.SPICE);
        int spiceNum = randomInt(spices.length);
        spice.setName(spices[spiceNum]);
        spice.setCost(new Price(prices[spiceNum]*100, 0));
        spice.setBookReference("Dungeon Fantasy 8 p. 11");

        // generate the weight
        TreasureComponent weight = new TreasureComponent(1, TreasureComponentType.QUANTITY);
        double weightValue = rollD6(1) / 2.0;
        weightValue *= multiplier;
        weight.setName(new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(weightValue) + " oz");
        weight.setCost(new Price(0, weightValue - 1));

        // apply the weight to the spice
        spice.addComponent(weight);

        return decorative > 0 ? buildMaterialContainer(spice).assembleTreasure() : spice.assembleTreasure();
    }

    /* For debugging
    public static TreasureComponent buildFiberAll() {
        for (int i = 0;  i < 36; i++) {
            if (i >= 17 && i <= 23) {
                for (int j = 0; j < 12; j++) {
                    System.out.println(i + " " + j + " " + buildFiber(i, j).assembleTreasure());
                }
            } else if (i >= 24 && i <= 25) {
                for (int j = 0; j < 43; j++) {
                    System.out.println(i + " " + j + " " + buildFiber(i, j).assembleTreasure());
                }
            } else {
                System.out.println(i + " " + buildFiber(i, -1).assembleTreasure());
            }
        }

        return new TreasureComponent(0, TreasureComponentType.EMPTY);
    }
     */

    /**
     * Build a fiber, fabric, leather, or fur. This includes weight, area, and price
     * @return a treasure component containing the fiber, fur, fabric, or leather as well as components
     * describing it's cost, type, weight, and area if applicable.
     */
    public static TreasureComponent buildFiber() {
        // apply properties from the parent treasure table
        int properties = randomInt(12);
        int multiplier = 1;
        if (properties >= 6 && properties <= 8) {
            multiplier = 2;
        } else if (properties >= 9) {
            multiplier = 3;
        }

        String[] fibers = {"Cloth", "Fur", "Leather", "Fiber"};
        String[][] types = {
                {"Otherworldly", "Giant-Spider Silk", "Gauze", "Linen", "Pashmina Wool", "Plain Silk", "Samite", "Satin", "Velvet", "Wool"},
                {"Common", "Exotic"},
                {"Common", "Exotic", "Contraband", "Scale-Hide", "Otherworldly"},
                {"Linen", "Silk", "Wild Silk", "Wool", "Pashmina Wool", "Giant-Spider Silk", "Otherworldly"}};
        int[][] probabilities = {
                {1, 1, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2},
                {2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };
        int[][] prices = {
                {20000, 6500, 500, 1400, 4500, 1700, 4200, 7500, 1800, 1500},
                {20000, 50000},
                {15000, 25000, 50000, 27500, 100000},
                {25, 75, 65, 20, 60, 1000, 7}
        };
        int[][] areas = {{100}, {100}, {100}, {}};
        String[] areaType = {"bolt","bundle","bundle",""};
        double[][] weights = {
                {7.5, 1, 1.5, 2.5, 4, 2, 3, 2, 5, 6},
                {75, 75},
                {50, 50, 25, 50, 50},
                {}// no weight means the weight is rolled, 2d6*3 hardcoded
        };
        SubTable[][] subTable = {{},
                {SubTable.COMMONANIMAL, SubTable.EXOTICANIMAL},
                {SubTable.COMMONANIMAL, SubTable.EXOTICANIMAL, SubTable.RACELEATHER, SubTable.NONE, SubTable.NONE},
                {}};
        int[] page = {12, 12, 12, 13};

        // create a list of how probable each category is
        int[] probabilityCategories = new int[probabilities.length];
        for (int i = 0; i < probabilityCategories.length; i++) {
            probabilityCategories[i] += ArraySum(probabilities[i]);
        }

        // create all of the components that are likely to be used with this treasure
        int id = 0;
        TreasureComponent fiber = new TreasureComponent(id++, TreasureComponentType.FIBER);
        TreasureComponent weight = new TreasureComponent(id++, TreasureComponentType.QUANTITY);
        TreasureComponent area = new TreasureComponent(0, TreasureComponentType.QUANTITY);
        TreasureComponent type = new TreasureComponent(0, TreasureComponentType.TYPE);

        // select a random element from the table
        int num = randomInt(ArraySum(probabilityCategories));
        int fiberCategory = selectFromProbabilityList(probabilityCategories, num);
        fiber.setName(fibers[fiberCategory]);
        int index = selectFromProbabilityList(
                probabilities[fiberCategory], num - PartialArraySum(probabilityCategories, fiberCategory));

        // book reference
        fiber.setBookReference("Dungeon Fantasy 8 p. " + page[fiberCategory]);

        // cost
        fiber.setCost(new Price(prices[fiberCategory][index], 0));

        // weight
        double weightValue;
        if (weights[fiberCategory].length == 0) {
            weightValue = rollD6(2) * 3;
            weight.setCost(new Price(0, weightValue - 1));
        } else {
            weightValue = weights[fiberCategory][index];
        }
        weightValue *= multiplier;
        if (weightValue == 1) {
            weight.setName("1 lb.");
        } else {
            weight.setName(weightValue + " lbs.");
        }
        fiber.addComponent(weight);

        // area
        if (areas[fiberCategory].length != 0) {
            area.setName((areas[fiberCategory][0] * multiplier) + "-sq-foot " + areaType[fiberCategory] + " of");
            area.setID(id++);
            area.setCost(new Price(0, multiplier - 1));
            fiber.addComponent(area);
        }

        // type
        type.setName(types[fiberCategory][index]);
        type.setID(id);
        fiber.addComponent(type);
        if (subTable[fiberCategory].length != 0) {
            switch(subTable[fiberCategory][index]) {
                case COMMONANIMAL:
                case EXOTICANIMAL: {
                    TreasureComponent animal = buildAnimal(subTable[fiberCategory][index]);
                    fiber.addComponent(animal);
                } break;
                case RACELEATHER: {
                    TreasureComponent race = new TreasureComponent(0, TreasureComponentType.TYPE);
                    race.setName(rollRaceLeather());
                    fiber.addComponent(race);
                } break;
                default:
                    break;
            }
        }

        return fiber.assembleTreasure();
    }

    /**
     * Unit enum used for the buildOtherMaterial function
     */
    private enum unit {
        NONE,
        GALLON,
        PINT,
        OZ
    }
    /**
     * Build another material such as a beverage, perfume, dye, etc.
     * @return the material, cost, and quantity
     */
    public static TreasureComponent buildOtherMaterial() {
        // apply properties from the parent treasure table
        int properties = randomInt(12);
        int multiplier = 1;
        if (properties >= 6 && properties <= 8) {
            multiplier = 2;
        } else if (properties >= 9) {
            multiplier = 3;
        }
        int decorative = 0;
        if (properties == 5 || properties == 8 || properties == 11) {
            decorative = 1;
        }

        String[] materials = {"Ale", "Distilled Liquor", "Flavored Ale", "Flavored Brandy", "Kumiz", "Mead", "Opium", "Black Tea", "Green Tea", "Date Wine", "Grape Wine",
        "Rice Wine", "Otherworldly Wine", "Sealing Wax", "Ambergris", "Cedar Resin", "Copal", "Frankincense", "Musk", "Myrrh", "Onycha", "Patchouli", "Sandalwood Gum",
        "Flower Water", "Perfumed Essence", "Perfumed Oil", "Pomander", "Carmine", "Ochre", "Henna", "Indigo", "Madder", "Murex", "Orpiment", "Woad"};
        int[] prices = {500, 1600, 750, 2000, 1500, 1100, 2000, 225, 225, 900, 900, 800, 2000, 125, 3500, 1000, 1100, 1600, 2800, 1500, 2000, 900, 850, 500, 1200, 800,
                900, 4000, 1800, 75, 100, 3200, 200, 2900, 2200, 275};
        unit[] units = {unit.GALLON, unit.PINT, unit.GALLON, unit.PINT, unit.GALLON, unit.GALLON, unit.OZ, unit.OZ, unit.OZ, unit.GALLON, unit.GALLON, unit.GALLON,
                unit.GALLON, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ,
                unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ, unit.OZ};

        // generate the material
        TreasureComponent material = new TreasureComponent(0, TreasureComponentType.MATERIAL);
        int index = randomInt(materials.length);
        material.setName(materials[index]);
        material.setCost(new Price(prices[index], 0));
        material.setBookReference("Dungeon Fantasy 8 p. 13");

        // generate the quantity
        TreasureComponent quantity = new TreasureComponent(1, TreasureComponentType.QUANTITY);
        double quantityValue = rollD6(1) + 1;
        quantityValue *= multiplier;
        StringBuilder unitString = new StringBuilder();
        switch(units[index]) {
            case OZ: {
                unitString.append("oz.");
            } break;
            case GALLON: {
                unitString.append("gallon").append(quantityValue == 1 ? "" : "s");
            } break;
            case PINT: {
                unitString.append("pint").append(quantityValue == 1 ? "" : "s");
            } break;
            default:
                break;
        }
        quantity.setName(new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(quantityValue) + " " + unitString.toString());
        quantity.setCost(new Price(0, quantityValue - 1));
        material.addComponent(quantity);

        return decorative > 0 ? buildMaterialContainer(material).assembleTreasure() : material.assembleTreasure();
    }
}
