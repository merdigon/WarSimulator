package com.company.Helper;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Position {
    private double x;
    private double y;

    public Position(double _x, double _y)
    {
        x = _x;
        y = _y;
    }

    public void setX(double _x)
    {
        x = _x;
    }

    public void setY(double _y)
    {
        y = _y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double countDistance(Position vect, boolean ifToward)
    {
        if(ifToward)
            return 2.0;
        return -2.0;
    }
}
