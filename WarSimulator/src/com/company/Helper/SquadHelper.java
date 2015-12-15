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
        if(squad!=null) {
            for (Soldier soldier : squad.getSoldiers()) {
                if (soldier.getStatus()) {
                    nr++;
                    if(soldier.getCoord() == null)
                    {}
                    xSum += soldier.getCoord().getX();
                    ySum += soldier.getCoord().getY();
                }
            }
        }
        if(nr==0)
            return null;
        return new Coord(xSum/nr, ySum/nr);/*
        int maxX = 0;
        int minX = 50000;
        int maxY = 0;
        int minY = 50000;
        for(Soldier soldier : squad.getSoldiers()){
            if(soldier.getStatus()) {
                Coord sold = soldier.getCoord();
                if(sold.getX()<minX)
                    minX = (int)sold.getX();
                if(sold.getX()>maxX)
                    maxX = (int)sold.getX();
                if(sold.getY()<minY)
                    minY = (int)sold.getY();
                if(sold.getY()>maxY)
                    maxY = (int)sold.getY();
            }
        }
        if(maxX==0 || maxY == 0 || minY == 50000 || minX == 50000)
            return null;
        return new Coord((minX = maxX)/2, (minY = maxY)/2);*/
    }

    public static int getAverageHPofSquad(Squad squad){
        int hp=0;
        for(Soldier soldier : squad.getSoldiers()){
            hp+=soldier.getHp();
        }
        if(squad.getSoldiers().size() == 0)
            return 0;
        return (hp/squad.getSoldiers().size());
    }
}
