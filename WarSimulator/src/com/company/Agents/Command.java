package com.company.Agents;

import com.company.Helper.CoordHelper.Vector2;

/**
 * Created by Szymon on 2015-10-27.
 */
public class Command {

    Vector2 possition;
    CommandType command;

    public Command(){

    }

    public void setVector2(double x, double y)
    {
        possition = new Vector2(x,y);
    }


}
