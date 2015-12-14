package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Squads.ArcherSquad;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;

/**
 * Created by Szymon on 2015-11-09.
 */
public class ArcherySquadBehaviour extends SquadBehaviour {

    public ArcherySquadBehaviour(Commander commander, ArcherSquad squad){
        super(commander, squad);
    }

    @Override
    protected void ifAttack() {
        commForSoldiers = new Command(CommandType.ATTACK);
        if(commFromCommander.getPossition() == null) {
            Coord pos = SquadHelper.getMiddlePointOfSquad(commFromCommander.getSquad());
            if(pos != null)
                commForSoldiers.setPossition(pos);
        }
    }
}
