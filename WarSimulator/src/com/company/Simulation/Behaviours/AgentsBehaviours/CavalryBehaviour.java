package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.CoordHelper.Vector2;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Soldiers.Cavalry;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.AttackBehaviour;
import com.company.Simulation.CommandType;

/**
 * Created by Szymon on 2015-11-17.
 */
public class CavalryBehaviour extends SoldierBehaviour {

    Cavalry soldier;

    public CavalryBehaviour(Cavalry sold){
        soldier = sold;
    }

    @Override
    public Cavalry getSoldier() {
        return soldier;
    }

    @Override
    protected void attackFromCommand() {
        Coord enemyCoord = getSoldier().getCommand().getPossition();
        move((int)enemyCoord.getX(), (int)enemyCoord.getY());
    }

    @Override
    public void action(){
        if(!checkIfAlive()){
            getSoldier().killSoldier();
            return;
        }
        thinking();
        noticedEnemies.clear();
        noticedFriends.clear();
        if(getSoldier().getCommand().getCommType() == CommandType.HOLD_POSSITION){
            ifDecideNotToMove();
        }
    }

    @Override
    protected void attack(Soldier soldToAttack){
        ifDecideNotToMove();
        soldToAttack.addBehaviour(new AttackBehaviour(getSoldier(), soldToAttack));
    }

    private void ifDecideNotToMove(){
        soldier.setVelocity(500);
    }

    private void ifDecideToMove(){
        double vel = soldier.getVelocity() - soldier.getVelocityStep();
        if(vel < soldier.getMaxVelocity())
            soldier.setVelocity(soldier.getMaxVelocity());
        else
            soldier.setVelocity(vel);
    }

    @Override
    protected void additionalMovement(){
        ifDecideToMove();
    }

    @Override
    protected void additionalStop(){
        ifDecideNotToMove();
    }

    @Override
    protected void listenCommand()  {
        if(getSoldier().getCommand() != null && (getSoldier().getCommand().getCommType() == CommandType.MOVEMENT || getSoldier().getCommand().getCommType() == CommandType.BACK)){
            Coord enemyCoord = getSoldier().getCommand().getPossition();

            if(!move((int)enemyCoord.getX(),(int)enemyCoord.getY()))
                additionalStop();
            else
                additionalMovement();
        }
        else if(getSoldier().getCommand() != null && getSoldier().getCommand().getCommType() == CommandType.ATTACK){
            attackFromCommand();
        }
        else if(getSoldier().getCommand() != null && getSoldier().getCommand().getCommType() == CommandType.MERGE){
            runToFriends();
        }
    }

    @Override
    protected void runAway(){
        Coord coordToRunAway = getSoldier().getCoord().clone();
        coordToRunAway.applyVector(new Vector2(-1,0));
        if(coordToRunAway.getX()<=0)
            getSoldier().killSoldier();

        if(!move((int)coordToRunAway.getX(),(int)coordToRunAway.getY()))
            additionalStop();
        else
            additionalMovement();
    }

    @Override
    protected void runToFriends(){
        Coord friendsMid = SquadHelper.getMiddlePointOfSquad(getSoldier().getSquad());
        if(!move((int)friendsMid.getX(),(int)friendsMid.getY()))
            additionalStop();
        else
            additionalMovement();
    }
}
