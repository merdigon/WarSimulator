package com.company.Simulation.Agents.Squads;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.ArrowPhysic;
import com.company.Simulation.Agents.Soldiers.Archer;
import com.company.Simulation.Teams;

import java.util.ArrayList;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArcherSquad extends Squad{

    public ArrowPhysic arrowPhysic = new ArrowPhysic();

    public ArcherSquad(Teams team, Map map){
        super(team, map);
    }

    @Override
    public void setSquad(int startCoordX, int startCoordY, int howManyInX, int howManyInY){
        Archer sld;
        for(int i=0;i<howManyInX;i++){
            for(int j=0;j<howManyInY;j++) {
                sld = new Archer(this);
                terrainMap.putSoldierOnPosition(sld, startCoordX+i, startCoordY+j);
                squadSoldiers.add(sld);
            }
        }
    }
}