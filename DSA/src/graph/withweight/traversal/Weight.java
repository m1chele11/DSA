package graph.withweight.traversal;

/**
 * TIER I TODO
 * <p>
 * This class represents a distance between two nodes in a graph.
 * <p>
 * The weight is represented as a double number which, in Java, supports positive
 * and negative infinities, division by zero, etc. This allows us to easily represent
 * the distance between two nodes that are not connected by a path as positive
 * infinity.
 */
public class Weight implements Comparable<Weight>{
    private final double weight;

    private Weight () { this.weight = Double.POSITIVE_INFINITY; }
    private Weight (double weight) { this.weight = weight; }

    /**
     * The only public constructor takes an integer. The use
     * of doubles is hidden internally.
     */
    public Weight (int weight) { this.weight = weight; }
    public static final Weight INFINITY = new Weight();
    public static final Weight ZERO = new Weight(0);

    public int value () { return (int) this.weight; }

    /**
     * TIER I TODO
     * <p>
     * The following methods implement the basic arithmetic operations on weights.
     */
    public Weight add (Weight w) {

        return new Weight(this.weight + w.weight);
    }

    /**
     * TIER I TODO
     */
    public Weight subtract (Weight w) {
        return new Weight(this.weight - w.weight);
    }

    /**
     * TIER I TODO
     */
    public int compareTo (Weight other) {
        return Double.compare(this.weight, other.weight);
    }

    /**
     * TIER I TODO
     */
    public static Weight min (Weight w1, Weight w2) {
        return w1.weight < w2.weight ? w1 : w2;
    }

    public boolean equals (Object o) {
        if (o instanceof Weight w)
            return this.value() == w.value();
        return false;
    }

    public int hashCode () { return this.value(); }

    public String toString () {
        if (Double.isFinite(weight)) return String.valueOf(this.value());
        else return "*";
    }
}
