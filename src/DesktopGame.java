/**
 * @author Yanis Biziuk
 */

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
    public static void main (String[] args) {
        new LwjglApplication(new SkeletonGame(), "Game", 800, 480, true);
    }
}