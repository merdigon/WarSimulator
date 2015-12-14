package com.company.Simulation;

import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Squads.Squad;

/**
 * Created by Szymon on 2015-10-27.
 */
public class Command {

    Coord possition = null;
    CommandType command;
    Squad squad;
    boolean wasListened = false;

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

    public Squad getSquad() { return squad; }

    public void setSquad(Squad squad) { this.squad = squad; }

    public void setWasListened(boolean wl) {wasListened = wl;}

    public boolean getWasListened() {return wasListened;}
}
