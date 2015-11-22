package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.CoordHelper.Vector2;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Soldiers.Warrior;
import com.company.Simulation.Agents.Squads.WarriorSquad;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2015-11-09.
 */
public class WarriorSquadBehaviour extends SquadBehaviour {



    public WarriorSquadBehaviour(Commander commander, WarriorSquad squad){
        super(commander, squad);
    }

    @Override
    protected void ifAttack() {
        Coord squadMed = SquadHelper.getMiddlePointOfSquad(squad);
        Coord enemyMed = SquadHelper.getMiddlePointOfSquad(commFromCommander.getSquad());

        Vector2 v2 = squadMed.giveVectorToCoord(enemyMed);

        commForSoldiers = new Command(CommandType.ATTACK);
        vectorToMove = v2;
    }
}
