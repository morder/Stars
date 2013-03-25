package space;

import space.objects.Planet;

import java.util.ArrayList;

/**
 * @author Yanis Biziuk
 */
public class Player {
    public int mCrystals = 0;
    public int mGas = 0;

    public ArrayList<Planet> mPlanets = new ArrayList<Planet>();
    public ArrayList<Object> mShips = new ArrayList<Object>();

    public void addPlanet(Planet planet){
        mPlanets.add(planet);
    }
}

