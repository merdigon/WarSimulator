package com.company.Simulation.Agents.Squads;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Soldiers.Warrior;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-31.
 */
public class WarriorSquad extends Squad {

    public WarriorSquad(Teams team, Map terrain) {
        super(team, terrain);
    }

    @Override
    public void setSquad(int startCoordX, int startCoordY, int howManyInX, int howManyInY){
        Warrior sld;
        for(int i=0;i<howManyInX;i++){
            for(int j=0;j<howManyInY;j++) {
                sld = new Warrior(this);
                terrainMap.putSoldierOnPosition(sld, startCoordX+i, startCoordY+j);
                squadSoldiers.add(sld);
            }
        }
    }
}
