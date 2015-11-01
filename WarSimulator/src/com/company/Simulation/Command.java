package com.company.Simulation;

import com.company.Helper.CoordHelper.Coord;

/**
 * Created by Szymon on 2015-10-27.
 */
public class Command {

    Coord possition;
    CommandType command;

    public Command(CommandType cmdtype){
        command = cmdtype;
    }

    public void setCoordToMove(double x, double y)
    {
        possition = new Coord(x,y);
    }

    public Coord getPossition() { return possition; }

    public void setPossition(Coord possition) { this.possition = possition; }

    public CommandType getCommType() {return command;}
}
