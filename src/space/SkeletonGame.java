package space;

import space.MainScreen;

/**
 * @author Yanis Biziuk
 */
public class SkeletonGame extends skeleton.Game {

    public void create () {
        super.create();

        MainScreen ms = new MainScreen(this);
        setScreen(ms);
    }
}
