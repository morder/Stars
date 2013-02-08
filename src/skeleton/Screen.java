package skeleton;

import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Yanis Biziuk
 */

// класс который отвечает за отрисовку экрана (всего окна приложения), он же отрисовывает window(s)
public abstract class Screen{
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

    public final ArrayList<GestureDetector.GestureListener> getGesturesProcessors(){
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
    }

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
