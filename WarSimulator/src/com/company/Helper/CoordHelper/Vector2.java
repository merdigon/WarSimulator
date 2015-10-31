package com.company.Helper.CoordHelper;

/**
 * Created by Szymon on 2015-10-28.
 */
public class Vector2 extends CoordHelper {

    public Vector2()
    {
        super();
    }

    public Vector2(double _x, double _y)
    {
        super(_x, _y);
    }

    public Coord changeCoord(Coord crd)
    {
        crd.setX(crd.getX() + x);
        crd.setY(crd.getY() + y);
        return crd;
    }

    public void negative()
    {
        x = (-1) * x;
        y = (-1) * y;
    }
}

