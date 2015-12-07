package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.CoordHelper.Vector2;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Behaviours.BasicBahaviours.CyclicBehaviour;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;

/**
 * Created by Szymon on 2015-11-09.
 */
public abstract class SquadBehaviour extends CyclicBehaviour {

    Command commFromCommander;
    Command commForSoldiers;
    Commander commander;
    Squad squad;
    Vector2 vectorToMove;

    public SquadBehaviour(Commander commander, Squad squad){
        this.commander = commander;
        this.squad = squad;
    }

    @Override
    protected void action() {
        commFromCommander = squad.getCommand();
        if(commFromCommander==null)
            return;
        thinking();
        giveCommand();
    }

    protected void thinking(){
        executeCommanderComm();
    }

    protected void executeCommanderComm(){
        switch(commFromCommander.getCommType()){
            case ATTACK: ifAttack();    break;
            case MOVEMENT: ifMovement();    break;
            case HOLD_POSSITION: ifHoldPossition(); break;
            case BACK:  ifBack();   break;
        }
    }

    protected void giveCommand(){
        if(commForSoldiers==null)
            return;

        for(Soldier sold: squad.getSoldiers()){
            if(commForSoldiers != null && vectorToMove != null && commForSoldiers.getPossition()!= null)
                //to jest b³êdne, powinno siê robiæ apply vector dla koordów ¿o³nierza
                commForSoldiers.getPossition().applyVector(vectorToMove);

            sold.setCommand(commForSoldiers);
        }
        vectorToMove = null;
    }

    //oblicza vektor do poruszenia siê na podstawie coordów do ruchu i œrodka swojego squadu
    protected void ifMovement() {
        Coord squadMed = SquadHelper.getMiddlePointOfSquad(squad);
        Coord coordToMove = commFromCommander.getPossition();

        Vector2 v2 = squadMed.giveVectorToCoord(coordToMove);

        commForSoldiers = new Command(CommandType.MOVEMENT);
        vectorToMove = v2;
    }

    //je¿eli hold_possition, to nic nie robi
    protected void ifHoldPossition() {
        commForSoldiers = new Command(CommandType.HOLD_POSSITION);
    }

    //to samo co mów, ale ¿o³nierz¹ przekazuje siê negatyw, czy vector przeciwny
    protected void ifBack() {
        Coord squadMed = SquadHelper.getMiddlePointOfSquad(squad);
        Coord enemyMed = SquadHelper.getMiddlePointOfSquad(commFromCommander.getSquad());

        Vector2 v2 = squadMed.giveVectorToCoord(enemyMed);
        v2.negative();

        commForSoldiers = new Command(CommandType.BACK);
        vectorToMove = v2;
    }

    //atak jest ró¿ny dla ka¿dego squadu
    protected abstract void ifAttack();
}
