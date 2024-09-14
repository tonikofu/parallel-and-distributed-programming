package org.example;

import org.example.NonExistentTriangleException;
import org.example.IPolygon;

/** Represents a triangle
 * @author Arsenii Sotnikov
 */
public class Triangle implements IPolygon {
    private final int a, b, c;

    /** Creates a triangle with the specified sides.
     * @param a - The triangle’s first side.
     * @param b - The triangle’s second side.
     * @param c - The triangle’s third side.
     * @throws NonExistentTriangleException The exception is about a nonexistent triangle.
     */
    public Triangle(final int a, final int b, final int c) throws NonExistentTriangleException {
        this.a = a;
        this.b = b;
        this.c = c;

        boolean triangleCondition = (a + b > c && b + c > a && a + c > b);

        if (!triangleCondition) {
            throw new NonExistentTriangleException("There is no triangle with such sides.");
        }
    }

    /**
     * Calculates the perimeter of a triangle.
     * @return Triangle's perimeter.
     */
    public int calculatePerimeter() {
        return a + b + c;
    }

    /**
     * Displays information about a triangle.
     * @return Line with information.
     */
    public String toString() {
        StringBuilder builder;

        builder = new StringBuilder("This is triangle {");
        builder.append("\n\tside1 : ").append(this.a);
        builder.append("\n\tside2 : ").append(this.b);
        builder.append("\n\tside3 : ").append(this.c);
        builder.append("\n}");

        return builder.toString();
    }
}
