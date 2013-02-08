import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Yanis Biziuk
 */
public class MyGestureListener implements GestureDetector.GestureListener
{
    @Override
    public boolean touchDown(float v, float v2, int i, int i2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean tap(float v, float v2, int i, int i2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean longPress(float v, float v2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean fling(float v, float v2, int i) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean pan(float v, float v2, float v3, float v4) {
        //System.out.println("x: " + v + ", y: " + v2 + ", deltaX: " + v3 + ", deltaY: " + v4);

        Game.get().moveBG(v3, v4);

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance) {
        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
        return false;
    }
}
