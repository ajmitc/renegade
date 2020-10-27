package renegade.game.board;

import java.awt.*;

public class Hexagon extends Polygon {
    private int x, y;
    private final int radius;
    private final Point center;
    private final int wOffset;

    public Hexagon(int x, int y, int px, int py, int radius) {
        this(x, y, new Point(px, py), radius);
    }

    public Hexagon(int x, int y, Point center, int radius) {
        this.x = x;
        this.y = y;
        this.center = center;
        this.radius = radius;
        this.wOffset = (int) Math.cos(Math.toRadians(30)) * radius;

        for (int i = 0; i < 6; i++) {
            int xval = (int) (center.x + radius
                    * Math.cos(i * 2 * Math.PI / 6D));
            int yval = (int) (center.y + radius
                    * Math.sin(i * 2 * Math.PI / 6D));
            addPoint(xval, yval);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public static int getWidthOffset(int hexRadius) {
        return (int) (Math.sin(Math.toRadians(30)) * hexRadius);
    }

    public static int getHeightOffset(int hexRadius) {
        return (int) (Math.cos(Math.toRadians(30)) * hexRadius);
    }

    /*
    public enum Corner {
        UpperLeft, UpperRight, Right, LowerRight, LowerLeft, Left
    }

    public enum Direction {
        NorthEast, East, SouthEast, SouthWest, West, NorthWest
    }

    public enum BoundingCorner {
        TopLeft, TopRight, BottomLeft, BottomRight
    }

    protected Point2D center;
    protected int size;
    protected double height, width;
    protected double hOffset, wOffset;
    protected Rectangle2D boundingBox;
    protected HashMap<Corner, Point2D> corners;
    protected HashMap<BoundingCorner, Point2D> boundingCorners;

    public Hexagon(double centerX, double centerY, int size) {
        this(new Point2D.Double(centerX, centerY), size);
    }

    public Hexagon(Point2D center, int size) {
        super();
        this.center = center;
        this.size = size;

        /**
         * MATH:
         *  With the hexagon points={TOP, UPPER-RIGHT, LOWER-RIGHT, BOTTOM, LOWER-LEFT, UPPER-RIGHT}
         *  size = length of each actual segment of the hexagon
         *  width = bounding rectangle width
         *  height = bounding rectangle height
         *  each inner angle is 120 degrees
         *  outside angles are 30-60-90 triangles with 30 near TOP and BOTTOM and 60 near sides
         *  hOffset = height difference between 'size' edge and bounding rectangle corners
         *  wOffset = width difference between TOP/BOTTOM points and bounding rectangle corners
         *
         *       _______
         *      /       \
         *     /    .    \
         *     \         /
         *      \       /
         *       -------
         *

        double thirtyDegrees = Math.toRadians(30);
        hOffset = Math.sin(thirtyDegrees) * size;
        wOffset = Math.cos(thirtyDegrees) * size;

        height = (2 * hOffset);
        width = (2 * wOffset) + size;

        double left = center.getX() - (width / 2);
        double right = center.getX() + (width / 2);
        double top = center.getY() - (height / 2);
        double bottom = center.getY() + (height / 2);
        boundingBox = new Rectangle2D.Double(left, top, width, height);

        boundingCorners = new HashMap<>();
        boundingCorners.put(BoundingCorner.TopRight, new Point2D.Double(right, top));
        boundingCorners.put(BoundingCorner.TopLeft, new Point2D.Double(left, top));
        boundingCorners.put(BoundingCorner.BottomRight, new Point2D.Double(right, bottom));
        boundingCorners.put(BoundingCorner.BottomLeft, new Point2D.Double(left, bottom));

        corners = new HashMap<>();
        corners.put(Corner.UpperLeft, new Point2D.Double(left, (top + hOffset)));
        corners.put(Corner.UpperRight, new Point2D.Double(right, (top + hOffset)));
        corners.put(Corner.Right, new Point2D.Double(right, center.getY()));
        corners.put(Corner.LowerRight, new Point2D.Double(right, (bottom - hOffset)));
        corners.put(Corner.LowerLeft, new Point2D.Double(left, (bottom - hOffset)));
        corners.put(Corner.Left, new Point2D.Double(left, center.getY()));

        for (Corner corner : Corner.values()) {
            Point2D p2d = corners.get(corner);
            addPoint((int) p2d.getX(), (int) p2d.getY());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Hexagon: ");
        sb.append(String.format(
                "[Size: %d]",
                size
        ));
        sb.append(String.format(
                "[Height: %2.1f, hOffset: %2.1f]",
                height, hOffset
        ));
        sb.append(String.format(
                "[Width: %2.1f, wOffset: %2.1f]",
                width, wOffset
        ));
        sb.append(String.format(
                "[Center: %2.1fx%2.1f]",
                center.getX(), center.getY()
        ));

        sb.append("[Corners: ");
        for (Corner corner : Corner.values()) {
            Point2D p2d = corners.get(corner);
            sb.append(String.format(
                    "[%s: %2.1fx%2.1f]",
                    corner, p2d.getX(), p2d.getY()
            ));
        }
        sb.append("]");

        sb.append("[Bounds: ");
        for (BoundingCorner corner : BoundingCorner.values()) {
            Point2D p2d = boundingCorners.get(corner);
            sb.append(String.format(
                    "[%s: %2.1fx%2.1f]",
                    corner, p2d.getX(), p2d.getY()
            ));
        }
        sb.append("]");


        sb.append(String.format(
                "[BoundingBox: %2.1fx%2.1f to %2.1fx%2.1f]",
                boundingBox.getMinX(),
                boundingBox.getMinY(),
                boundingBox.getMaxX(),
                boundingBox.getMaxY()
        ));

        sb.append("]");
        return sb.toString();
    }

    public Point2D getCenter() {
        return center;
    }

    public int getSize() {
        return size;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return boundingBox;
    }

    public double getHeightOffset() {
        return hOffset;
    }

    public double getWidthOffset() {
        return wOffset;
    }

    //public HashMap<Corner,Point2D> getCorners(){return corners;}
    //public HashMap<BoundingCorner,Point2D> getBoundingCorners(){return boundingCorners;}

    public Hexagon resize(int newSize) {
        return new Hexagon(center, newSize);
    }

    @Override
    public Object clone() {
        return new Hexagon(center, size);
    }

    public Point2D getHexPoint(Corner corner) {
        return corners.get(corner);
    }

    public Point2D getBoundPoint(BoundingCorner corner) {
        return boundingCorners.get(corner);
    }

    public void translate(double deltaX, double deltaY) {
        translate((int) deltaX, (int) deltaY);
    }

    @Override
    public void translate(int deltaX, int deltaY) {
        super.translate(deltaX, deltaY);
        boundingBox = super.getBounds2D();

        double top = boundingBox.getMinY();
        double left = boundingBox.getMinX();
        double bottom = boundingBox.getMaxY();
        double right = boundingBox.getMaxX();

        double centerX = boundingBox.getCenterX();
        double centerY = boundingBox.getCenterY();
        center = new Point2D.Double(centerX, centerY);

        boundingCorners.put(BoundingCorner.TopRight, new Point2D.Double(right, top));
        boundingCorners.put(BoundingCorner.TopLeft, new Point2D.Double(left, top));
        boundingCorners.put(BoundingCorner.BottomRight, new Point2D.Double(right, bottom));
        boundingCorners.put(BoundingCorner.BottomLeft, new Point2D.Double(left, bottom));

        corners.put(Corner.UpperLeft, new Point2D.Double(left, (top + hOffset)));
        corners.put(Corner.UpperRight, new Point2D.Double(right, (top + hOffset)));
        corners.put(Corner.Right, new Point2D.Double(right, centerY));
        corners.put(Corner.LowerRight, new Point2D.Double(right, (bottom - hOffset)));
        corners.put(Corner.LowerLeft, new Point2D.Double(left, (bottom - hOffset)));
        corners.put(Corner.Left, new Point2D.Double(left, centerY));
    }

    public void align(Rectangle2D bounds, Direction direction) {
        // these are defined here INSTEAD of in the switch, or it won't compile
        Point2D newTopRight, newTopLeft, newBottomRight, newBottomLeft;
        Point2D oldTopRight, oldTopLeft, oldBottomRight, oldBottomLeft;

        switch (direction) {
            case NorthEast:
                newTopRight = new Point2D.Double(bounds.getMaxX(), bounds.getMinY());
                oldTopRight = boundingCorners.get(BoundingCorner.TopRight);
                translate(
                        newTopRight.getX() - oldTopRight.getX(),      // deltaX
                        newTopRight.getY() - oldTopRight.getY()        // deltaY
                );
                break;
            case East:
                newTopRight = new Point2D.Double(bounds.getMaxX(), bounds.getMinY());
                oldTopRight = boundingCorners.get(BoundingCorner.TopRight);
                translate(
                        newTopRight.getX() - oldTopRight.getX(),      // deltaX
                        0                          // deltaY
                );
                break;
            case SouthEast:
                newBottomRight = new Point2D.Double(bounds.getMaxX(), bounds.getMaxY());
                oldBottomRight = boundingCorners.get(BoundingCorner.BottomRight);
                translate(
                        newBottomRight.getX() - oldBottomRight.getX(),    // deltaX
                        newBottomRight.getY() - oldBottomRight.getY()    // deltaY
                );
                break;
            case SouthWest:
                newBottomLeft = new Point2D.Double(bounds.getMinX(), bounds.getMaxY());
                oldBottomLeft = boundingCorners.get(BoundingCorner.BottomLeft);
                translate(
                        newBottomLeft.getX() - oldBottomLeft.getX(),    // deltaX
                        newBottomLeft.getY() - oldBottomLeft.getY()      // deltaY
                );
                break;
            case West:
                newTopLeft = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
                oldTopLeft = boundingCorners.get(BoundingCorner.TopLeft);
                translate(
                        newTopLeft.getX() - oldTopLeft.getX(),        // deltaX
                        0                          // deltaY
                );
                break;
            case NorthWest:
                newTopLeft = new Point2D.Double(bounds.getMinX(), bounds.getMinY());
                oldTopLeft = boundingCorners.get(BoundingCorner.TopLeft);
                translate(
                        newTopLeft.getX() - oldTopLeft.getX(),        // deltaX
                        newTopLeft.getY() - oldTopLeft.getY()        // deltaY
                );
                break;
        }
    }

    public void attach(Hexagon toTranslate, Direction direction) {
        double horSize = size + hOffset;
        /**
         * To start with, we need to know toTranslate's position RELATIVE to ours
         *
        Point2D topLeft = getBoundPoint(BoundingCorner.TopLeft);
        Point2D toTranslateTopLeft = toTranslate.getBoundPoint(BoundingCorner.TopLeft);
        double deltaX = topLeft.getX() - toTranslateTopLeft.getX();
        double deltaY = topLeft.getY() - toTranslateTopLeft.getY();
        // that should be enough to line them up exactly... now offset it...

        switch (direction) {
            case NorthEast:
                deltaX += wOffset;
                deltaY -= horSize;
                break;
            case East:
                deltaX += width;
                break;
            case SouthEast:
                deltaX += wOffset;
                deltaY += horSize;
                break;
            case SouthWest:
                deltaX -= wOffset;
                deltaY += horSize;
                break;
            case West:
                deltaX -= width;
                break;
            case NorthWest:
                deltaX -= wOffset;
                deltaY -= horSize;
                break;
        }
        toTranslate.translate(deltaX, deltaY);
    }
    */
}
