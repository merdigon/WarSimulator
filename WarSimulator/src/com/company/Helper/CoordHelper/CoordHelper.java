package com.company.Helper.CoordHelper;

/**
 * Created by Szymon on 2015-10-28.
 */
public abstract class CoordHelper {
    protected double x;
    protected double y;

    public CoordHelper()
    {}

    public CoordHelper(double _x, double _y)
    {
        x = _x;
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

    public void  setY(double _y){
        y = _y;
    }

    public void setX(double _x){
        x = _x;
    }

    public void replaceXY(CoordHelper coordH)
    {
        setX(coordH.getX());
        setY(coordH.getY());
    }
}
