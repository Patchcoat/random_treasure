package com.example.randomtreasure;

/**
 * @since 2021-1-26
 */
public class Price {

    /**
     * Value, in cents. A value of 100 is $1.00. Values are added together, then added to the
     * parent component's value.
     */
    private int value;
    /**
     * Cost Factor, or CF. Cost Factors are added together and then then price is multiplied by them.
     */
    private double CF;

    /**
     * Creates a default price with a value and CF of 0
     */
    Price() { value = 0; CF = 0; }

    /**
     * Creates a price with a value and CF set by the user
     * @param value the value of the price
     * @param CF    the cost factor of the price
     */
    Price(int value, double CF) { this.value = value; this.CF = CF; }

    /**
     * @param value the value to set the price to
     */
    void setValue(int value) { this.value = value; }

    /**
     * @param CF the cost factor to set
     */
    void setCF(double CF) { this.CF = CF; }

    /**
     * @return the value in cents of the price
     */
    int value() { return value; }

    /**
     * @return the cost factor of the price
     */
    double CF() { return CF; }

    /**
     * Adds together the price an Cost Factor of two price objects.
     * @param other the other Price object
     * @return the two Price objects added together
     */
    Price add(Price other) {
        if (null == other) return null;
        return new Price(value + other.value, CF + other.CF);
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", CF=" + CF +
                '}';
    }
}
