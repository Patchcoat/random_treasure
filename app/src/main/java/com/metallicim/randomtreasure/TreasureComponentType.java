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
     * The quantity of the parent component. Could be the weight in lbs or oz, area in square feet, or volume in gallons.
     */
    QUANTITY,
    /**
     * The type of the component.
     */
    TYPE,
    /**
     * The component is a spice
     */
    SPICE,
    /**
     * The component is a fiber, leather, fur, or fabric
     */
    FIBER,
    /**
     * The component is some other material other than a spice or fiber
     */
    MATERIAL,
    /**
     * The component is a container
     */
    CONTAINER,
    /**
     * The component is a jewel
     */
    JEWEL,
    /**
     * The component is a soft material embellishment
     */
    SOFTEMBELLISHMENT,
    /**
     * The component is a hard material embellishment
     */
    HARDEMBELLISHMENT
}
