package com.company.Simulation.Behaviours;

import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.BasicBahaviours.OneShotBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class AttackBehaviour extends OneShotBehaviour {
    Soldier attacker;
    Soldier defender;

    public AttackBehaviour(Soldier attacker, Soldier defender)
    {
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void action() {
        //co jezeli zolnierz zostanie trafiony
        defender.changeHp(40);
    }
}
