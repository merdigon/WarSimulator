package com.company.Simulation.Behaviours.AgentsBehaviours;


import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.CoordHelper.Vector2;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;

/**
 * Created by Szymon on 2015-11-17.
 */
public class CavalrySquadBehaviour extends SquadBehaviour{

    public CavalrySquadBehaviour(Commander commander, Squad squad) {
        super(commander, squad);
    }

    @Override
    protected void ifAttack() {
        Coord squadMed = SquadHelper.getMiddlePointOfSquad(squad);
        Coord enemyMed = SquadHelper.getMiddlePointOfSquad(commFromCommander.getSquad());

        if(enemyMed == null)
        {
            int a=9;
        }
        Vector2 v2 = squadMed.giveVectorToCoord(enemyMed);

        commForSoldiers = new Command(CommandType.ATTACK);
        vectorToMove = v2;
    }
}
