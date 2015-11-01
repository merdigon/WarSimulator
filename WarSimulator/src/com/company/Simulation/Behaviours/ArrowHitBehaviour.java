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
        //dodaæ kod, który okreœli co siê stanie, jak soldier oberwie strza³¹
        //!!!!! dodawaæ tylko te rzeczy, które nie zale¿¹ od jego decyzji
        //za decyzjê odpowiada behaviour SoldierBehaviour
        //tutaj dodaæ tylko utratê ¿ycia, ewentualnie œmieræ
        hitSoldier.changeHp(40);
    }
}
