package com.metallicim.randomtreasure;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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


    public static String rollRaceLeather() {
        String[] races = {"Cat-Folk", "Coleopteran", "Corpse-Eater", "Dark One", "Dwarf", "Half-Elf", "High Elf", "Mountain Elf", "Sea Elf", "Shadow Elf", "Winged Elf", "Wood Elf", "Faun",
                "Leprecaun", "Nymph", "Pixie", "Gargoyle", "Gnome", "Goblin", "Half-Orc", "Hobgoblin", "Orc", "Halfling", "Celestial", "Elder-Spawn", "Infernal", "Human", "Air-Infused", "Earth-Infused",
                "Fire-Infused", "Water-Infused", "Minotaur", "Half-Ogre", "Ogre", "Dragon-Blooded", "Lizard Man", "Troll", "Wildman"};
        int[] probabilities = {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        return races[selectFromProbabilityList(probabilities, randomInt(ArraySum(probabilities)))];
    }

    /**
     * Build an animal, used for leather and fur in the buildFiber() function
     * @param type 0 for a common animal, 1 for an exotic animal
     * @return a Treasure Component with it's name equal to the randomly generated animal
     */
    public static TreasureComponent buildAnimal(int type) {
        String[] commonAnimals = {"Seal", "Monkey", "Rabbit", "Fox", "Goat", "Horse", "Ox", "Deer", "Elk", "Reindeer", "Antelope", "Ibex"};
        String[] exoticAnimals = {"Sable", "Ermine", "Jaguar", "Lion", "Tiger", "Wolf", "Bear", "Wyvern", "Dire Wolf" , "Cave Bear", "Giant Ape", "Frost Snake"};
        String[] animals = commonAnimals;
        if (type == 1) {
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

        // apply the weight to the spice
        spice.addComponent(weight);

        return spice.assembleTreasure();
    }

    public static TreasureComponent buildFiber() {
        String[] fibers = {"Cloth", "Fur", "Leather", "Fiber", "Scale-Hide"};
        int[] probabilities = {0,0,0,0,2};
        int[] prices = {0,0,0,0,27500};
        int[] weights = {0,0,0,0,50};
        String[] clothType = {"Otherworldly", "Giant-Spider Silk", "Gauze", "Linen", "Pashmina Wool", "Plain Silk", "Samite", "Satin", "Velvet", "Wool"};
        int[] clothProbabilities = {1, 1, 2, 2, 2, 2, 2, 2, 2, 1};
        int[] clothPrices = {200, 65, 5, 14, 45, 17, 42, 75, 18, 15};
        double[] clothWeights = {7.5, 1, 1.5, 2.5, 4, 2, 3, 2, 5, 6};
        String[] furType = {"Common", "Exotic"};
        int[] furProbabilities = {1, 2};
        int[] furPrices = {200, 500};
        double[] furWeights = {75, 75};
        String[] leatherType = {"Common", "Exotic", "Contraband", "Otherworldly"};
        int[] leatherProbabilities = {2, 2, 2, 1};
        int[] leatherPrices = {150, 250, 500, 1000};
        double[] leatherWeights = {50, 50, 25, 50};
        String[] fiberType = {"Linen", "Silk", "Wild Silk", "Wool", "Pashmina Wool", "Giant-Spider Silk", "Otherworldly"};
        int[] fiberProbabilities = {1, 1, 1, 1, 1, 1, 1};
        int[] fiberPrices = {25, 75, 65, 20, 60, 1000, 7};

        TreasureComponent fiber = new TreasureComponent(0, TreasureComponentType.FIBER);
        TreasureComponent type = new TreasureComponent(0, TreasureComponentType.TYPE);
        TreasureComponent weight = new TreasureComponent(0, TreasureComponentType.QUANTITY);
        TreasureComponent area = new TreasureComponent(0, TreasureComponentType.QUANTITY);

        probabilities[0] = ArraySum(clothProbabilities);
        probabilities[1] = ArraySum(furProbabilities);
        probabilities[2] = ArraySum(leatherProbabilities);
        probabilities[3] = ArraySum(fiberProbabilities);

        int num = randomInt(ArraySum(probabilities));
        int fiberCategory = selectFromProbabilityList(probabilities, num);
        fiber.setName(fibers[fiberCategory]);
        switch(fiberCategory) {
            case 0: {
                int clothIndex = selectFromProbabilityList(clothProbabilities, num);
                fiber.setCost(new Price(clothPrices[clothIndex] * 100, 0));
                fiber.setBookReference("Dungeon Fantasy 8 p. 12");
                // weight
                if (clothIndex == 1) {
                    weight.setName("1 lb.");
                } else {
                    weight.setName(clothWeights[clothIndex] + " lbs.");
                }
                // area
                area.setName("per 100-sq-foot bolt of");
                // type
                type.setName(clothType[clothIndex]);

                fiber.addComponent(weight);
                fiber.addComponent(area);
                fiber.addComponent(type);
            } break;
            case 1: {
                int furIndex = selectFromProbabilityList(
                        furProbabilities, num - PartialArraySum(probabilities, fiberCategory));
                fiber.setCost(new Price(furPrices[furIndex] * 100, 0));
                fiber.setBookReference("Dungeon Fantasy 8 p. 12");
                // weight
                weight.setName(furWeights[furIndex] + " lbs");
                // area
                area.setName("per 100-sq-foot bundle of");
                // type
                type.setName(furType[furIndex]);

                fiber.addComponent(weight);
                fiber.addComponent(area);
                fiber.addComponent(type);
                TreasureComponent animal = buildAnimal(furIndex);
                fiber.addComponent(animal);
            } break;
            case 2: {
                int leatherIndex = selectFromProbabilityList(
                        leatherProbabilities, num - PartialArraySum(probabilities, fiberCategory));
                fiber.setCost(new Price(leatherPrices[leatherIndex] * 100, 0));
                fiber.setBookReference("Dungeon Fantasy 8 p. 12");
                // weight
                weight.setName(leatherWeights[leatherIndex] + " lbs.");
                // area
                area.setName("per 100-sq-foot bundle of");
                // type
                type.setName(leatherType[leatherIndex]);

                fiber.addComponent(weight);
                fiber.addComponent(area);
                fiber.addComponent(type);
                if (leatherIndex <= 1) {
                    TreasureComponent animal = buildAnimal(leatherIndex);
                    fiber.addComponent(animal);
                } else if (leatherIndex == 2) {
                    TreasureComponent race = new TreasureComponent(0, TreasureComponentType.TYPE);
                    race.setName(rollRaceLeather());
                    fiber.addComponent(race);
                }
            } break;
            case 3: {
                int fiberIndex = selectFromProbabilityList(
                        fiberProbabilities, num - PartialArraySum(probabilities, fiberCategory));
                fiber.setCost(new Price(fiberPrices[fiberIndex], 0));
                fiber.setBookReference("Dungeon Fantasy 8 p. 12");
                // weight
                double weightValue = (randomInt(6) + randomInt(6) + 2) * 3;
                weight.setName(weightValue + " lbs. of ");
                weight.setCost(new Price(0, weightValue - 1));
                // type
                type.setName(fiberType[fiberIndex]);

                fiber.addComponent(weight);
                fiber.addComponent(type);
            } break;
            case 4: {
                fiber.setCost(new Price(prices[fiberCategory], 0));
                fiber.setBookReference("Dungeon Fantasy 8 p. 12");
                // weight
                weight.setName(weights[fiberCategory] + " lbs.");
                // area
                area.setName("per 100-sq-foot bundle of");

                // apply the weight and area
                fiber.addComponent(weight);
                fiber.addComponent(area);
            } break;
            default:
                break;
        }

        return fiber.assembleTreasure();
    }
}
