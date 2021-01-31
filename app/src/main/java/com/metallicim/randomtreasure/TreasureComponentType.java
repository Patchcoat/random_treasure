package com.metallicim.randomtreasure;

/**
 * <h1>Treasure Component Type</h1>
 * This enum contains every single type of treasure and treasure component.
 *
 * @since 2021-1-25
 */
public enum TreasureComponentType {
    /**
     * Used for a treasure component that doesn't have a type assigned yet
     */
    EMPTY,
    /**
     * The quantity of the parent component. Could be the weight in lbs or oz or volume in gallons.
     */
    QUANTITY,
    /**
     * The component is a spice
     */
    SPICE
}
