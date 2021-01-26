package com.example.randomtreasure;

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

    int id;
    TreasureComponentType type;
    String name;
    String bookReference;
    String description;
    LinkedList<TreasureComponent> components;

    /**
     * This method creates a default empty treasure component with an ID 0 and a type of EMPTY.
     */
    TreasureComponent(){
        id = 0;
        type = TreasureComponentType.EMPTY;
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

}
