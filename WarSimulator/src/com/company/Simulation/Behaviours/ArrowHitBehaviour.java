package com.company.Simulation.Behaviours;

import com.company.Simulation.Agents.Soldiers.Soldier;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArrowHitBehaviour extends OneShotBehaviour {

    Soldier hitSoldier;

    public ArrowHitBehaviour(Soldier _hitSoldier)
    {
        super();
        hitSoldier = _hitSoldier;
    }

    @Override
    public void action() {
        //doda� kod, kt�ry okre�li co si� stanie, jak soldier oberwie strza��
        //!!!!! dodawa� tylko te rzeczy, kt�re nie zale�� od jego decyzji
        //za decyzj� odpowiada behaviour SoldierBehaviour
        //tutaj doda� tylko utrat� �ycia, ewentualnie �mier�
        hitSoldier.changeHp(40);
    }
}
