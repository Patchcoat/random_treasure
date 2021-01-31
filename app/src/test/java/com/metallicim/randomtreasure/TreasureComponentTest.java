package com.metallicim.randomtreasure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <h1>Treasure Component Unit Tests</h1>
 * This is a collection of tests that verifies the Treasure Component functions like it needs to
 * and meshes well with other objects such as Price and the TreasureComponentType enum.
 *
 * @since 2021-1-31
 */

public class TreasureComponentTest {

    /**
     * This tests the basic functionality of the Treasure Component object including constructors
     * setters, and getters.
     */
    @Test
    public void treasureComponent_isCorrect() {
        // test the default constructor
        TreasureComponent testComponent1 = new TreasureComponent();
        assertEquals(0, testComponent1.ID());
        assertEquals(TreasureComponentType.EMPTY, testComponent1.type());

        // test the custom constructor
        TreasureComponent testComponent2 =
                new TreasureComponent(1, TreasureComponentType.QUANTITY);
        assertEquals(1, testComponent2.ID());
        assertEquals(TreasureComponentType.QUANTITY, testComponent2.type());

        // test the setters and getters
        testComponent2.setID(2);
        testComponent2.setName("Test Name");
        testComponent2.setBookReference("B20");
        testComponent2.setDescription("Test description goes here.");
        testComponent2.setType(TreasureComponentType.SPICE);
        testComponent2.setCost(new Price(1, 1.5));
        assertEquals(2, testComponent2.ID());
        assertEquals("Test Name", testComponent2.name());
        assertEquals("B20", testComponent2.bookReference());
        assertEquals("Test description goes here.", testComponent2.description());
        assertEquals(TreasureComponentType.SPICE, testComponent2.type());
        assertEquals(1, testComponent2.cost().value());
        assertEquals(1.5, testComponent2.cost().CF(), 0.0001);
    }

    /**
     * This tests the treasure assembler function in the Treasure Component object to ensure it
     * assembles the name correctly.
     */
    @Test
    public void treasureComponentNameAssembler_isCorrect() {
        TreasureComponent TC1 = new TreasureComponent(0, TreasureComponentType.EMPTY);
        TreasureComponent TC2 = new TreasureComponent(1, TreasureComponentType.EMPTY);
        TreasureComponent TC3 = new TreasureComponent(2, TreasureComponentType.EMPTY);
        TreasureComponent TC4 = new TreasureComponent(3, TreasureComponentType.EMPTY);
        TreasureComponent TC5 = new TreasureComponent(4, TreasureComponentType.EMPTY);
        TC1.setName("1"); TC2.setName("2"); TC3.setName("3"); TC4.setName("4"); TC5.setName("5");

        TC3.addComponent(TC4); TC3.addComponent(TC5);
        TC1.addComponent(TC2); TC1.addComponent(TC3);

        assertEquals("2 4 5 3 1", TC1.assembleTreasure().name());
    }

    /**
     * This tests the treasure assembler function in the Treasure Component object to ensure it
     * assembles the price correctly.
     */
    @Test
    public void treasureComponentPriceAssembler_isCorrect() {
        TreasureComponent TC1 = new TreasureComponent(0, TreasureComponentType.EMPTY);
        TreasureComponent TC2 = new TreasureComponent(1, TreasureComponentType.EMPTY);
        TreasureComponent TC3 = new TreasureComponent(2, TreasureComponentType.EMPTY);
        TreasureComponent TC4 = new TreasureComponent(3, TreasureComponentType.EMPTY);
        TreasureComponent TC5 = new TreasureComponent(4, TreasureComponentType.EMPTY);

        TC1.setCost(new Price(10, 0));
        TC2.setCost(new Price(0, 2));
        TC3.setCost(new Price(100, 0));
        TC4.setCost(new Price(0, 1));
        TC5.setCost(new Price(1, 0));

        TC3.addComponent(TC4); TC3.addComponent(TC5);
        TC1.addComponent(TC2); TC1.addComponent(TC3);

        Price assembledPrice = TC1.assembleTreasure().cost();

        assertEquals(231, assembledPrice.value());
        assertEquals(0, assembledPrice.CF(), 0.0001);
    }
}
