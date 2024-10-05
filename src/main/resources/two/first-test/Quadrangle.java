package org.exmaple;

import org.example.IPolygon;

/** Represents a quadrangle
 * @author Arsenii Sotnikov
 */
public class Quadrangle extends Angle implements IPolygon {
    private final int a, b, c, d;

    /** Creates a quadrangle with the specified sides.
     * @param a The quadrangle’s first side.
     * @param b The quadrangle’s second side.
     * @param c The quadrangle’s third side.
     * @param d The quadrangle’s fourth side.
     */
    public Quadrangle(final int a, final int b, final int c, final int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Calculates the perimeter of a quadrangle.
     * @return Quadrangle's perimeter.
     */
    public int calculatePerimeter() {
        return a + b + c + d;
    }

    /**
     * Displays information about a quadrangle.
     * @return Line with information.
     */
    public String toString() {
        StringBuilder builder;

        builder = new StringBuilder("This is quadrangle {");
        builder.append("\n\tside1 : ").append(this.a);
        builder.append("\n\tside2 : ").append(this.b);
        builder.append("\n\tside3 : ").append(this.c);
        builder.append("\n\tside4 : ").append(this.d);
        builder.append("\n}");

        return builder.toString();
    }
}
