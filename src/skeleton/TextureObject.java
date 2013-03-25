package skeleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Yanis Biziuk
 */
public abstract class TextureObject implements GestureDetector.GestureListener {
    private Texture mTexture;
    private WindowController mController;
    protected int mGlobalX;
    protected int mGlobalY;
    private int mTexWidth;
    private int mTexHeight;
    private boolean mVisible = true;

    public enum ORDER {
        before, after
    }

    public TextureObject(WindowController controller, TextureObject before, int GlobalX, int GlobalY, Texture texture){
        controller.getWindow().addObjectToStack(this, before);
        mController = controller;
        mGlobalX = GlobalX;
        mGlobalY = GlobalY;
        mTexture = texture;
        mTexWidth = texture.getWidth();
        mTexHeight = texture.getHeight();
    }

    public synchronized boolean isVisible(){
        return mVisible;
    }

    public synchronized void show(){
        mVisible = true;
    }

    public synchronized void hide(){
        mVisible = false;
    }

    public Texture getTexture(){
        return mTexture;
    }

    public WindowController getController(){
        return mController;
    }

    public boolean dotInside(float x, float y){
        float convertedX = screenToGameX(x);
        float convertedY = screenToGameY(y);
        if (convertedX > mGlobalX && convertedX < mGlobalX + mTexWidth){
            if (convertedY < mGlobalY && convertedY > mGlobalY - mTexHeight){ // картинка рисуется вверх и вправо
                return true;
            }
        }
        return false;
    }

    public float getScreenX(){
        return mGlobalX - mController.getScreenPosInGameX();
    }

    public float getScreenY(){
        // с преобразованием к нижнему левому углу
        //return (mGameHeight - Y) - (mGameHeight - mCurrentGamePosY - mScreenHeight);
        return - mGlobalY + mController.getScreenPosInGameY() + mController.mWindow.mScreenHeight;
    }

    protected float screenToGameX(float X){
        return X + mController.getScreenPosInGameX();
    }

    protected float screenToGameY(float Y){
        return Y + mController.getScreenPosInGameY();
    }

    HashMap<ORDER, HashMap<String, LinkedList<callbackMove>>> mCallbacks = new HashMap<ORDER, HashMap<String, LinkedList<callbackMove>>>(); // order -> type -> list

    public void registerMoves(String type, ORDER order, callbackMove callback){
        if (!mCallbacks.containsKey(order)){
            LinkedList<callbackMove> callbacks = new LinkedList<callbackMove>();
            HashMap<String, LinkedList<callbackMove>> map = new HashMap<String, LinkedList<callbackMove>>();
            map.put(type, callbacks);
            mCallbacks.put(order, map);
        }
        if (!mCallbacks.get(order).containsKey(type)){
            LinkedList<callbackMove> controllers = new LinkedList<callbackMove>();
            mCallbacks.get(order).put(type, controllers);
        }
        mCallbacks.get(order).get(type).add(callback);
    }

    public void unregisterMoves(String type, callbackMove callback, ORDER order){
        if (!mCallbacks.containsKey(order)){
            return;
        }
        if (!mCallbacks.get(order).containsKey(type)){
            return;
        }
        mCallbacks.get(order).get(type).remove(callback);
    }

    private void notifyListeners(String type, ORDER order, boolean result){
        if (!mCallbacks.containsKey(order)){
            return;
        }
        if (!mCallbacks.get(order).containsKey(type)){
            return;
        }
        for(callbackMove listener: mCallbacks.get(order).get(type)){
            listener.event(this, type, result);
        }
    }

    /* #################################### */
    public final boolean touchDown(float v, float v1, int i, int i1){
        notifyListeners("touchDown", ORDER.before, false);
        boolean res = _touchDown(v, v1, i, i1);
        notifyListeners("touchDown", ORDER.after, res);
        return res;
    }

    public boolean _touchDown(float v, float v1, int i, int i1){
        return false;
    }

    public final boolean tap(float x, float y, int i, int i2){
        notifyListeners("tap", ORDER.before, false);
        boolean res = _tap(x, y, i, i2);
        notifyListeners("tap", ORDER.after, res);
        return res;
    }

    public boolean _tap(float x, float y, int i, int i2){
        return false;
    }

    public final boolean longPress(float v, float v1){
        notifyListeners("longPress", ORDER.before, false);
        boolean res = _longPress(v, v1);
        notifyListeners("longPress", ORDER.after, res);
        return res;
    }

    public boolean _longPress(float v, float v1){
        return false;
    }

    public final boolean fling(float v, float v1, int i){
        notifyListeners("fling", ORDER.before, false);
        boolean res = _fling(v, v1, i);
        notifyListeners("fling", ORDER.after, res);
        return res;
    }

    public boolean _fling(float v, float v1, int i){
        return false;
    }

    public final boolean pan(float fromX, float fromY, float deltaX, float deltaY){
        notifyListeners("pan", ORDER.before, false);
        boolean res = _pan(fromX, fromY, deltaX, deltaY);
        notifyListeners("pan", ORDER.after, res);
        return res;
    }

    public boolean _pan(float fromX, float fromY, float deltaX, float deltaY){
        return false;
    }

    public final boolean zoom(float v, float v1){
        notifyListeners("zoom", ORDER.before, false);
        boolean res = _zoom(v, v1);
        notifyListeners("zoom", ORDER.after, res);
        return res;
    }

    public boolean _zoom(float v, float v1){
        return false;
    }

    public final boolean pinch(com.badlogic.gdx.math.Vector2 vector2, com.badlogic.gdx.math.Vector2 vector3, com.badlogic.gdx.math.Vector2 vector4, com.badlogic.gdx.math.Vector2 vector5){
        notifyListeners("pinch", ORDER.before, false);
        boolean res = _pinch(vector2, vector3, vector4, vector5);
        notifyListeners("pinch", ORDER.after, res);
        return res;
    }

    public boolean _pinch(com.badlogic.gdx.math.Vector2 vector2, com.badlogic.gdx.math.Vector2 vector3, com.badlogic.gdx.math.Vector2 vector4, com.badlogic.gdx.math.Vector2 vector5){
        return false;
    }
    /* #################################### */

    public abstract void render(SpriteBatch mSpriteBatch);

    public void tick(long delta_time) {

    }



    public interface callbackMove{
        public void event(Object object, String type, boolean result);
    }

}
