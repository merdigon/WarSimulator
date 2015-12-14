package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
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
            killSoldier();
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
}
