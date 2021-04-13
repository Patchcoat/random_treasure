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
     * Build a spice. This includes the type and weight of the spice.
     * @return a TreasureComponent with weight and spice name set as the name, and the monetary
     *         value of the spice set as the cost.
     */
    public static TreasureComponent buildSpice() {
        String[] spices = { "Allspice", "Anise", "Annatto", "Asafetida", "Cardamom", "Cassia", "Chiles", "Cinnamon", "Clove", "Coriander", "Cumin", "Dwarven Savory Fungus", "Elven Pepperbark",
                "Faerie Glimmerseed", "Fennel", "Fenugreek", "Ginger", "Halfling Savory", "Huajiao (Szechuan Pepper)", "Mace", "Mustard", "Nigella", "Nutmeg", "Onion Seed", "Orcish Firegrain",
                "Pepper, Black", "Pepper, White", "Poppy Seed", "Saffron", "Salt", "Salt, Black", "Salt, Red", "Sumac", "Tamarind", "Tumericc", "Zeodary"};
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
        double weightValue = (randomInt(6) + 1) / 2.0;
        weight.setName(new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(weightValue) + " oz");
        weight.setCost(new Price(0, weightValue - 1));

        // TODO decorations

        // apply the weight to the spice
        spice.addComponent(weight);

        return spice.assembleTreasure();
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
        if (weightValue == 1) {
            weight.setName("1 lb.");
        } else {
            weight.setName(weightValue + " lbs.");
        }
        fiber.addComponent(weight);

        // area
        if (areas[fiberCategory].length != 0) {
            area.setName("per " + areas[fiberCategory][0] + "-sq-foot " + areaType[fiberCategory] + " of");
            area.setID(id++);
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
}
