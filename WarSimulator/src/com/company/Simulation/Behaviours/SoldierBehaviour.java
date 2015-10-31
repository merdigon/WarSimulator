package com.company.Simulation.Behaviours;

/**
 * Created by Szymon on 2015-10-31.
 */
import com.company.Simulation.Agents.Soldiers.Soldier;
import jade.core.behaviours.CyclicBehaviour;

public class SoldierBehaviour extends CyclicBehaviour{

    //najwa�niejsza klasa dla Soldier, kt�ra okre�la jego decyzj�, kt�re podejmuje oraz symuluje jego psychik�
    //rozwa�y� dodanie sprawdzania czy umar� itp jako inne, ale te� cykliczne behaviour
    Soldier soldier;

    @Override
    public void action() {


    }

    private void move(){

    }

    private void attack(Soldier soldToAttack)
    {
        soldToAttack.addBehaviour(new AttackBehaviour(soldier, soldToAttack));
    }
}
