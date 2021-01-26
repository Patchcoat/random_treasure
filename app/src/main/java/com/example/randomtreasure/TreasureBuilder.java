package com.example.randomtreasure;

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
    TreasureComponent buildSpice() {
        TreasureComponent spice = new TreasureComponent();

        // TODO generate the spice
        // TODO generate the weight

        return spice.assembleTreasure();
    }
}
