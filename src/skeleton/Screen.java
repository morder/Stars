package skeleton;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.sun.java.swing.plaf.windows.resources.windows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Yanis Biziuk
 */

// класс который отвечает за отрисовку экрана (всего окна приложения), он же отрисовывает window(s)
public abstract class Screen implements GestureDetector.GestureListener{
    Game mParentGame;
    HashMap<LAYER, ArrayList<Window>> mWindows = new HashMap<LAYER, ArrayList<Window>>(); // priority

    enum POSITION {
        TOP,
        BOTTOM
    }

    enum LAYER {
        TOP,
        DEFAULT,
        BOTTOM
    }

    @Override
    public boolean touchDown(float v, float v2, int i, int i2) {
        boolean ret = false;
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            LinkedList<TextureObject> listeners = window.getListenObjects();
            while (!listeners.isEmpty()){
                TextureObject listener = listeners.removeLast();
                if (listener.isVisible()){
                    ret = listener.touchDown(v, v2, i, i2);
                    if (ret) break;
                }
            }
            if (ret) break;
        }
        return ret;
    }

    @Override
    public boolean tap(float v, float v2, int i, int i2) {
        boolean ret = false;
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            LinkedList<TextureObject> listeners = window.getListenObjects();
            while (!listeners.isEmpty()){
                TextureObject listener = listeners.removeLast();
                if (listener.isVisible()){
                    ret = listener.tap(v, v2, i, i2);
                    if (ret) break;
                }
            }
            if (ret) break;
        }
        return ret;
    }

    @Override
    public boolean longPress(float v, float v2) {
        boolean ret = false;
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            LinkedList<TextureObject> listeners = window.getListenObjects();
            while (!listeners.isEmpty()){
                TextureObject listener = listeners.removeLast();
                if (listener.isVisible()){
                    ret = listener.longPress(v, v2);
                    if (ret) break;
                }
            }
            if (ret) break;
        }
        return ret;
    }

    @Override
    public boolean fling(float v, float v2, int i) {
        boolean ret = false;
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            LinkedList<TextureObject> listeners = window.getListenObjects();
            while (!listeners.isEmpty()){
                TextureObject listener = listeners.removeLast();
                if (listener.isVisible()){
                    ret = listener.fling(v, v2, i);
                    if (ret) break;
                }
            }
            if (ret) break;
        }
        return ret;
    }

    @Override
    public boolean pan(float v, float v2, float v3, float v4) {
        boolean ret = false;
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            LinkedList<TextureObject> listeners = window.getListenObjects();
            while (!listeners.isEmpty()){
                TextureObject listener = listeners.removeLast();
                if (listener.isVisible()){
                    ret = listener.pan(v, v2, v3, v4);
                    if (ret) break;
                }
            }
            if (ret) break;
        }
        return ret;
    }

    @Override
    public boolean zoom(float v, float v2) {
        boolean ret = false;
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            LinkedList<TextureObject> listeners = window.getListenObjects();
            while (!listeners.isEmpty()){
                TextureObject listener = listeners.removeLast();
                if (listener.isVisible()){
                    ret = listener.zoom(v, v2);
                    if (ret) break;
                }
            }
            if (ret) break;
        }
        return ret;
    }

    @Override
    public boolean pinch(Vector2 vector2, Vector2 vector22, Vector2 vector23, Vector2 vector24) {
        boolean ret = false;
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            LinkedList<TextureObject> listeners = window.getListenObjects();
            while (!listeners.isEmpty()){
                TextureObject listener = listeners.removeLast();
                if (listener.isVisible()){
                    ret = listener.pinch(vector2, vector22, vector23, vector24);
                    if (ret) break;
                }
            }
            if (ret) break;
        }
        return ret;
    }

    public Screen(Game parent){
        mParentGame = parent;
        mWindows.put(LAYER.TOP, new ArrayList<Window>());
        mWindows.put(LAYER.DEFAULT, new ArrayList<Window>());
        mWindows.put(LAYER.BOTTOM, new ArrayList<Window>());
    }

    public Game getGame(){
        return mParentGame;
    }

    public void init(){
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            window.init();
        }
    }

    public void close(){
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            window.close();
        }
    }

    /*public final ArrayList<GestureDetector.GestureListener> getGesturesProcessors(){
        ArrayList<GestureDetector.GestureListener> processors = new ArrayList<GestureDetector.GestureListener>();
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            GestureDetector.GestureListener processor = window.getInputProcessor();
            if (processor != null){
                processors.add(processor);
            }
        }
        return processors;
    }*/

    public final void render () {
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.addAll(mWindows.get(LAYER.BOTTOM));
        windows.addAll(mWindows.get(LAYER.DEFAULT));
        windows.addAll(mWindows.get(LAYER.TOP));
        for(Window window: windows){
            window.render();
        }
    }

    public final void addWindow(Window window){
        addWindow(window, LAYER.DEFAULT, POSITION.TOP);
    }

    public final void addWindow(Window window, LAYER layer){
        addWindow(window, layer, POSITION.TOP);
    }

    public final void addWindow(Window window, LAYER layer, POSITION position){
        if (position.equals(POSITION.TOP)){
            mWindows.get(layer).add(window);
        } else
        if (position.equals(POSITION.BOTTOM)){
            mWindows.get(layer).add(0, window);
        }
    }

    public final void changeWindowPriority(Window window, LAYER layer){
        changeWindowPriority(window, layer, POSITION.TOP);
    }

    public final void changeWindowPriority(Window window, LAYER layer, POSITION position){
        for(LAYER _l: LAYER.values()){
            if (mWindows.get(_l).contains(window)){
                mWindows.get(_l).remove(window);
            }
        }
        addWindow(window, layer, position);
    }
}
