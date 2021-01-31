package com.metallicim.randomtreasure;

import java.util.LinkedList;

/**
 * <h1>Treasure Component</h1>
 * The treasure component holds information about each individual
 * element of the treasure, as well as any sub components. A
 * treasure component is a tree of treasure components. A treasure
 * is simply all connected treasure components.
 *
 * @since 2021-01-25
 */

public class TreasureComponent {

    private int id;
    private TreasureComponentType type;
    private String name;
    private String bookReference;
    private String description;
    private Price cost;
    private LinkedList<TreasureComponent> components;

    /**
     * This method creates a default empty treasure component with an ID 0 and a type of EMPTY.
     */
    TreasureComponent(){
        id = 0;
        type = TreasureComponentType.EMPTY;
        components = new LinkedList<>();
        cost = new Price();
    }

    /**
     * This method creates a specific treasure component with a given ID and type.
     *
     * @param id   the unique identifier of the component
     * @param type the type of the component
     */
    TreasureComponent(int id, TreasureComponentType type){
        this.id = id;
        this.type = type;
        components = new LinkedList<>();
        cost = new Price();
    }

    /**
     * @param id the unique identifier for the component
     */
    void setID(int id) { this.id = id; }

    /**
     * @param type the type of component, or if the top level component, the type of treasure
     * @see TreasureComponentType
     */
    void setType(TreasureComponentType type) { this.type = type; }

    /**
     * @param name the name or value of the component
     */
    void setName(String name) { this.name = name; }

    /**
     * @param bookReference The book reference of the component. This should be in the style of
     *                      [book name] p. [page number]
     */
    void setBookReference(String bookReference) { this.bookReference = bookReference; }

    /**
     * @param description The description of the component. This is rarely needed, but can be
     *                    useful for things not immediately obvious from the name, or not clear
     *                    from the reference.
     */
    void setDescription(String description) { this.description = description; }

    /**
     * @param cost The monetary value of the component, or the amount that the component modifies
     *             the monetary value of the parent component.
     */
    void setCost(Price cost) { this.cost = cost; }

    /**
     * @param component the component to add as a child of this component
     */
    void addComponent(TreasureComponent component) { components.addLast(component); }

    /**
     * @return the unique identifier of the component
     */
    int ID() { return id; }

    /**
     * @return the component type
     * @see TreasureComponentType
     */
    TreasureComponentType type() { return type; }

    /**
     * @return the name or value of the component
     */
    String name() { return name; }

    /**
     * @return the book reference in the form [book name] p. [page number]
     */
    String bookReference() { return bookReference; }

    /**
     * The description of a component is rarely used.
     * @return a description of the component
     */
    String description() { return description; }

    /**
     * @return returns the monetary value of the component, or the amount that the component
     *         modifies the monetary value of the parent component.
     */
    Price cost() { return cost; }

    /**
     * This function assembles the full name and price of the treasure, taking into account all
     * sub-components.
     * @return a new treasure component with all the costs and names combined together.
     */
    TreasureComponent assembleTreasure() {
        StringBuilder fullName = new StringBuilder();
        Price costAccumulator  = new Price();
        TreasureComponent out  = new TreasureComponent();

        for (TreasureComponent component : components) {
            TreasureComponent assembled = component.assembleTreasure();
            fullName.append(assembled).append(" ");
            costAccumulator = costAccumulator.add(
                    new Price (assembled.cost().value(), component.cost().CF()));
        }

        // CF Value is the value of the component multiplied by the cost factor plus one
        // only the Cost Factor of the immediate children affect the CF value. This is
        // intentional and desirable.
        int CFValue = (int) (cost.value() * (costAccumulator.CF() + 1));
        // first the CF Value is calculated, then the value of the sub-components are added to that.
        // it is important the sub-component values are not added before the CFValue is calculated.
        out.setCost(new Price(CFValue + costAccumulator.value(), 0));

        out.setName(fullName.append(name).toString());

        return out;
    }

    /**
     * Converts TreasureComponent to a string.
     * @return the name of the TreasureComponent
     */
    @Override
    public String toString() { return name; }
}
