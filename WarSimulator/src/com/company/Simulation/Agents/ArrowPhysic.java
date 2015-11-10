package com.company.Simulation.Agents;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.ArrowHitBehaviour;
import com.company.Simulation.Behaviours.BasicBahaviours.WakerBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArrowPhysic extends Agent {

    //squad ³uczniczy albo sami ³ucznicy wywo³uj¹ setNextArrowAttack (wystrzelenie strza³y w powietrze)
    private static int arrowSpeed = 25; //ilosc milisekund na 1 kratke
    public ArrowPhysic(Map map){
        simMap = map;
    }

    int arrowFrequency=0;

    Map simMap;
    public void setNewArrowAttack(int sx, int sy, final int x, final int y){

        //dodaæ obliczanie czasu lotu strza³y

        int arrowTimeout = (int)(Math.sqrt(Math.pow(x - sx, 2) + Math.pow(y - sy, 2)) * arrowSpeed);

        if(arrowFrequency == 10) {
            simMap.Terrain[x][y].setArrowHit();
            arrowFrequency = 0;
        }
        else
            arrowFrequency++;


        addBehaviour(
                new WakerBehaviour(arrowTimeout) {
                    public void action() {
                        Soldier sld;
                        if ((sld = simMap.Terrain[x][y].getSoldier()) != null) {
                            sld.addBehaviour(new ArrowHitBehaviour(sld));
                        }
                    }
                }
        );
    }

    //dodaæ metody obliczaj¹ce lot strza³
}
