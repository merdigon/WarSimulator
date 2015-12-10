package com.company.Simulation.Agents.Squads;

import com.company.Battle;
import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Cavalry;
import com.company.Simulation.Behaviours.AgentsBehaviours.CavalrySquadBehaviour;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-11-17.
 */
public class CavalrySquad extends Squad {

    public CavalrySquad(Teams team, Map terrain, Battle battle, Commander commander) {
        super(team, terrain, battle, commander);
        squadType = SquadType.Cavalry;
    }

    @Override
    public void executePhysic() {}

    @Override
    protected void setup(){
        addBehaviour(new CavalrySquadBehaviour(commander, this));
    }

    @Override
    public CavalrySquad setSquad(int startCoordX, int startCoordY, int howManyInX, int howManyInY) {
        Cavalry sld;
        for(int i=0;i<howManyInX;i++){
            for(int j=0;j<howManyInY;j++) {
                sld = new Cavalry(this);
                terrainMap.putSoldierOnPosition(sld, startCoordX+i, startCoordY+j);
                squadSoldiers.add(sld);
            }
        }
        return this;
    }
}
