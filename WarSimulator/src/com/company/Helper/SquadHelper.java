package com.company.Helper;

import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.Squad;

/**
 * Created by Szymon on 2015-11-09.
 */
public class SquadHelper {
    public static Coord getMiddlePointOfSquad(Squad squad){
        int nr = 0;
        int xSum = 0;
        int ySum = 0;
        for(Soldier soldier : squad.getSoldiers()){
            if(soldier.getStatus()){
                nr++;
                xSum += soldier.getCoord().getX();
                ySum += soldier.getCoord().getY();
            }
        }
        if(nr==0)
            return null;
        return new Coord(xSum/nr, ySum/nr);
    }
}
