package com.company.Helper.CoordHelper;

import com.company.Enviroment.Map;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Coord extends CoordHelper {

    public Coord(double _x, double _y)
    {
        super(_x, _y);
    }

    @Override
    public void setX(double _x)
    {
        if(_x < 0)
            x = 0;
        else
            x = _x;
    }

    @Override
    public void setY(double _y)
    {
        if(_y < 0)
            y = 0;
        else
            y = _y;
    }


    public double countDistance(Coord coord)
    {
        return Math.sqrt(Math.pow(x - coord.getX(), 2) + Math.pow(y - coord.getY(), 2));
    }

    public Vector2 giveVectorToCoord(Coord coord)
    {
        return new Vector2(coord.getX() - x, coord.getY() - y);
    }

    public void applyVector(Vector2 vect)
    {
        setX(x + vect.getX());
        setY(y + vect.getY());
    }

    public Vector2 countNextMove(Coord targetCoord, double speed) //dodaæ mapê do uwzglêdniania
    {
        Vector2 vect = giveVectorToCoord(targetCoord);
        double a = speed/countDistance(targetCoord);
        vect.setX(vect.getX()*a);
        vect.setY(vect.getY()*a);
        return vect;
    }
}
