package com.example.randomtreasure;

import android.util.Log;
import android.util.Pair;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * <h1>Treasure Builder Object</h1>
 * This object holds all of the functions for building all of the treasures. Each function returns
 * a TreasureComponent that has already had all of it's sub-components assembled together.
 *
 * @since 2020-1-26
 */
public class TreasureBuilder {

    /**
     * Build a spice. This includes the type and weight of the spice.
     * @return a TreasureComponent with weight and spice name set as the name, and the monetary
     *         value of the spice set as the cost.
     */
    public static TreasureComponent buildSpice() {
        String[] spices = { "Allspice", "Anise", "Annatto", "Asafetida", "Cardamom", "Cassia", "Chiles", "Cinnamon", "Clove", "Coriander", "Cumin", "Dwarven Savory Fungus", "Elven Pepperbark", "Faerie Glimmerseed", "Fennel", "Fenugreek", "Ginger", "Halfling Savory", "Huajiao (Szechuan Pepper)", "Mace", "Mustard", "Nigella", "Nutmeg", "Onion Seed", "Orcish Firegrain", "Pepper, Black", "Pepper, White", "Poppy Seed", "Saffron", "Salt", "Salt, Black", "Salt, Red", "Sumac", "Tamarind", "Tumericc", "Zeodary"};
        // in dollars, multiply by 100 to get the component price.
        int[] prices = {150, 150, 113, 75, 150, 75, 38, 150, 150, 150, 150, 75, 38, 270, 75, 150, 38, 150, 150, 225, 38, 75, 150, 38, 150, 150, 188, 38, 300, 15, 38, 38, 38, 15, 38, 150};
        // generate the spice
        TreasureComponent spice = new TreasureComponent(0, TreasureComponentType.SPICE);
        int spiceNum = (int) (36 * Math.random());
        spice.setName(spices[spiceNum]);
        spice.setCost(new Price(prices[spiceNum]*100, 0));
        spice.setBookReference("Dungeon Fantasy 8 p. 11");

        // generate the weight
        TreasureComponent weight = new TreasureComponent(1, TreasureComponentType.QUANTITY);
        double weightValue = ((int) (7 * Math.random() + 1)) / 2.0;
        weight.setName(new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(weightValue) + " oz");
        weight.setCost(new Price(0, weightValue - 1));

        // apply the weight to the spice
        spice.addComponent(weight);

        return spice.assembleTreasure();
    }
}
