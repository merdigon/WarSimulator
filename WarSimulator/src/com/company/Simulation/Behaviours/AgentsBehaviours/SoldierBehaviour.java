package com.company.Simulation.Behaviours.AgentsBehaviours;

/**
 * Created by Szymon on 2015-10-31.
 */
import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.CoordHelper.Vector2;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Soldiers.Cavalry;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Agents.Squads.SquadType;
import com.company.Simulation.Behaviours.AttackBehaviour;
import com.company.Simulation.Behaviours.BasicBahaviours.CyclicBehaviour;
import com.company.Simulation.Command;
import com.company.Simulation.CommandType;

import java.util.ArrayList;

public abstract class SoldierBehaviour extends CyclicBehaviour {

    //najwa�niejsza klasa dla Soldier, kt�ra okre�la jego decyzj�, kt�re podejmuje oraz symuluje jego psychik�
    //rozwa�y� dodanie sprawdzania czy umar� itp jako inne, ale te� cykliczne behaviour
    ArrayList<Soldier> noticedEnemies = new ArrayList<Soldier>();
    ArrayList<Soldier> noticedFriends = new ArrayList<Soldier>();

    public SoldierBehaviour(){
    }

    public abstract Soldier getSoldier();

    //checked
    @Override
    public void action() {
        if (!checkIfAlive()) {
            getSoldier().killSoldier();
            return;
        }
        Command comm = getSoldier().getCommand();
        if (comm != null) {
            thinking();
        }
        noticedEnemies.clear();
        noticedFriends.clear();
    }

    protected void thinking(){
        if(getSoldier().getIfRunAway())
            runAway();
        else {
            lookForAgents();
            if (!executeInstincts()) {
                listenCommand();
            }
        }
    }

    //checked
    protected boolean checkIfAlive(){
        if(getSoldier().getHp()<0)
            return false;
        return true;
    }


    protected boolean executeInstincts(){
        countMoral();
        if(checkAndAttack())
            return true;
        else {/*
            if(getSoldier().getMoral()<(30 - getSoldier().getBrave()*3))
            {
                runFromEnemies();
                getSoldier().setMoral(getSoldier().getMoral()+2);
                return true;
            }
            if(getSoldier().getMoral()<(50 - getSoldier().getBrave()*3))
            {
                runToFriends();
                getSoldier().setMoral(getSoldier().getMoral()+2);
                return true;
            }*/
            return false;
        }
    }

    protected void countMoral(){
        int brave = getSoldier().getBrave();
        int hp = getSoldier().getHp();
        double p2w = (noticedEnemies.size()==0 ? noticedFriends.size()+1 : (noticedFriends.size()+1)/noticedEnemies.size());
        boolean wound = getSoldier().getWasAttacked();
        double moral = getSoldier().getMoral();

        if(hp < 15 + 5*brave) {
            moral -= 30;
        }

        if(wound){
            moral -= (60 - 5*brave);
        }
/*
        if(p2w <= 1.5 && p2w > 1){
            if(moral<50)
                moral+=0.2;
        }
        else if(p2w>1.5 && p2w<2){
            if(moral<99)
                moral+=0.2;
        }
        else if(p2w>2){
            if(moral<99)
                moral+=0.5;
        }

        if(moral>16) {
            if (brave == 0 && p2w <= 0.5)
                moral -= 0.2;
            else if (brave == 1 && p2w <= 0.33)
                moral -= 0.2;
            else if (brave == 2 && p2w <= 0.25)
                moral -= 0.2;
            else if (brave == 3 && p2w <= 0.2)
                moral -= 0.2;
        }*/

        getSoldier().resetWasAttacked();
        getSoldier().setMoral(moral);
        if(moral<15){
            getSoldier().setIfRunAway();
        }
    }

    protected void runToFriends(){
        Coord friendsMid = SquadHelper.getMiddlePointOfSquad(getSoldier().getSquad());
        move((int)friendsMid.getX(),(int)friendsMid.getY());
    }

    protected void runFromEnemies(){
        if(noticedEnemies.size()>0) {
            Coord friendsMid = SquadHelper.getMiddlePointOfSquad(noticedEnemies.get(0).getSquad());
            Vector2 vec = getSoldier().getCoord().giveVectorToCoord(friendsMid);
            vec.negative();
            Coord coordtoMove = getSoldier().getCoord();
            coordtoMove.applyVector(vec);
            move((int)coordtoMove.getX(),(int)coordtoMove.getY());
        }
        else
            runToFriends();
    }

    protected void additionalMovement(){}

    protected void additionalStop() {}

    //checked
    protected boolean move(int xRelative, int yRelative){
        //additionalMovement();
        int oldRelX = xRelative;
        int oldRelY = yRelative;
/*
        if(Math.abs(getSoldier().getCoord().getX() - xRelative) <= 2) {
            xRelative = 0;
            yRelative = (int) ((yRelative - getSoldier().getCoord().getY()) / Math.abs(yRelative - getSoldier().getCoord().getY()));
        }
        else if(Math.abs(getSoldier().getCoord().getY()-yRelative) <= 2) {
            xRelative = (int) ((xRelative - getSoldier().getCoord().getX()) / Math.abs(xRelative - getSoldier().getCoord().getX()));
            yRelative = 0;
        }
        else {*/
            xRelative = (int) ((xRelative - getSoldier().getCoord().getX()) / Math.abs(xRelative - getSoldier().getCoord().getX()));
            yRelative = (int) ((yRelative - getSoldier().getCoord().getY()) / Math.abs(yRelative - getSoldier().getCoord().getY()));
        //}

        if(xRelative>1 || xRelative<-1 || yRelative<-1 || yRelative>1)
            xRelative++;

        Coord soldCoord = getSoldier().getCoord();
        if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + xRelative, (int)soldCoord.getY() + yRelative)) {
            int newXRelative;
            int newYRelative;
            if (xRelative == 0) {
                newXRelative = 1;
                newYRelative = yRelative;
                if (!getSoldier().getSquad().terrainMap.moveSoldier((int) soldCoord.getX(), (int) soldCoord.getY(), (int) soldCoord.getX() + newXRelative, (int) soldCoord.getY() + newYRelative)) {
                    newXRelative = -1;
                    if (!getSoldier().getSquad().terrainMap.moveSoldier((int) soldCoord.getX(), (int) soldCoord.getY(), (int) soldCoord.getX() + newXRelative, (int) soldCoord.getY() + newYRelative)) {
                        return false;
                    }
                }
            } else if (yRelative == 0) {
                newYRelative = 1;
                newXRelative = xRelative;
                if (!getSoldier().getSquad().terrainMap.moveSoldier((int) soldCoord.getX(), (int) soldCoord.getY(), (int) soldCoord.getX() + newXRelative, (int) soldCoord.getY() + newYRelative)) {
                    newYRelative = -1;
                    if (!getSoldier().getSquad().terrainMap.moveSoldier((int) soldCoord.getX(), (int) soldCoord.getY(), (int) soldCoord.getX() + newXRelative, (int) soldCoord.getY() + newYRelative)) {
                        return false;
                    }
                }
            } else {
                if(Math.abs(oldRelX - soldCoord.getX())>Math.abs(oldRelY - soldCoord.getY())) {
                    newYRelative = 0;
                    newXRelative = xRelative;
                }
                else{
                    newXRelative = 0;
                    newYRelative = yRelative;
                }
                if (!getSoldier().getSquad().terrainMap.moveSoldier((int) soldCoord.getX(), (int) soldCoord.getY(), (int) soldCoord.getX() + newXRelative, (int) soldCoord.getY() + newYRelative)) {
                    if(Math.abs(oldRelX - soldCoord.getX())>Math.abs(oldRelY - soldCoord.getY())) {
                        newXRelative = 0;
                        newYRelative = yRelative;
                    }
                    else{
                        newYRelative = 0;
                        newXRelative = xRelative;
                    }
                    if (!getSoldier().getSquad().terrainMap.moveSoldier((int) soldCoord.getX(), (int) soldCoord.getY(), (int) soldCoord.getX() + newXRelative, (int) soldCoord.getY() + newYRelative)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //checked
    protected void attack(Soldier soldToAttack){
        soldToAttack.addBehaviour(new AttackBehaviour(getSoldier(), soldToAttack));
    }

    //checked
    protected void lookForAgents(){
        Coord tmpCoord = getSoldier().getCoord().clone();
        Soldier tmpsold;
        tmpCoord.setX(tmpCoord.getX()-3);
        tmpCoord.setY(tmpCoord.getY()-3);
        for(int i=0; i<7;i++)
        {
            for(int j=0; j<7; j++)
            {
                if(i!=3 || j!=3)
                {
                    if((int)tmpCoord.getX()+i < getSoldier().getSquad().terrainMap.X && (int)tmpCoord.getY()+j < getSoldier().getSquad().terrainMap.Y) {
                        if ((tmpsold = getSoldier().getSquad().terrainMap.getSoldierOnPosition((int) tmpCoord.getX() + i, (int) tmpCoord.getY() + j)) != null) {
                            if (tmpsold.getSquad().team == getSoldier().getSquad().team)
                                noticedFriends.add(tmpsold);
                            else
                                noticedEnemies.add(tmpsold);
                        }
                    }
                }
            }
        }
    }

    protected void listenCommand()  {
        if(getSoldier().getCommand() != null && (getSoldier().getCommand().getCommType() == CommandType.MOVEMENT || getSoldier().getCommand().getCommType() == CommandType.BACK)){
            Coord enemyCoord = getSoldier().getCommand().getPossition();

                    move((int)enemyCoord.getX(),(int)enemyCoord.getY());
        }
        else if(getSoldier().getCommand() != null && getSoldier().getCommand().getCommType() == CommandType.ATTACK){
            attackFromCommand();
        }
        else if(getSoldier().getCommand() != null && getSoldier().getCommand().getCommType() == CommandType.MERGE){
            runToFriends();
        }
    }

    //checked
    protected boolean checkAndAttack(){
        if(getSoldier().getSquad().squadType == SquadType.Cavalry && ((Cavalry)getSoldier()).getVelocity()<400)
            return false;
        for(Soldier soldEnemy : noticedEnemies){
            if(Math.abs(getSoldier().getCoord().getX() - soldEnemy.getCoord().getX()) <= 1 && Math.abs(getSoldier().getCoord().getY() - soldEnemy.getCoord().getY()) <= 1){
                attack(soldEnemy);
                return true;
            }
        }
        return false;
    }

    protected abstract void attackFromCommand();

    /*
    protected boolean runToEnemy(){
        if(!noticedEnemies.isEmpty()){
            Coord enemyCoord = noticedEnemies.get(0).getCoord();
            if(Math.abs(getSoldier().getCoord().getX()-enemyCoord.getX()) <= 2){
                move(0, (int)((enemyCoord.getY() - getSoldier().getCoord().getY())/Math.abs(enemyCoord.getY() - getSoldier().getCoord().getY())));
                return true;
            }
            if(Math.abs(getSoldier().getCoord().getY()-enemyCoord.getY()) <= 2){
                move((int)((enemyCoord.getX() - getSoldier().getCoord().getX())/Math.abs(enemyCoord.getX() - getSoldier().getCoord().getX())), 0);
                return true;
            }
            move((int)((enemyCoord.getX() - getSoldier().getCoord().getX())/Math.abs(enemyCoord.getX() - getSoldier().getCoord().getX())),
                    (int)((enemyCoord.getY() - getSoldier().getCoord().getY())/Math.abs(enemyCoord.getY() - getSoldier().getCoord().getY())));
            return true;
        }
        return false;
    }*/

    //checked


    protected void runAway(){
        Coord coordToRunAway = getSoldier().getCoord().clone();
        coordToRunAway.applyVector(new Vector2(-1,0));
        if(coordToRunAway.getX()<=0)
            getSoldier().killSoldier();

                move((int)coordToRunAway.getX(),(int)coordToRunAway.getY());
    }
}
