package skeleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yanis Biziuk
 */

// класс который отвечает за отрисовку некоторого элемента сцены, это либо полный экран, либо меню, либо некий элемент интерфейса
public abstract class Window{
    // реализация MVC
    // поток для расчета
    // метод для отрисовки окна

    class TickThread extends Thread{
        long mLastTime = 0L;
        int FPS = 60;

        Long current_second = System.currentTimeMillis();
        int _fps = 0;

        public void run(){
            long delta_time;
            long current_time;
            long new_time;
            while (true){
                current_time = System.currentTimeMillis();
                if (mLastTime == 0){
                    delta_time = 0;
                } else {
                    delta_time = current_time - mLastTime;
                }

                mWindowController.tick(delta_time); // 992 millis in 1 sec

                mLastTime = current_time;
                new_time = System.currentTimeMillis();
                long sleep = (1000 * 1000) / FPS - ((new_time - current_time) * 1000);
                if (sleep > 0){
                    try {
                        Thread.sleep(sleep / 1000, (int) (sleep - (sleep / 1000)));
                    } catch (InterruptedException e) {
                        break;
                    }
                }


                // fps
                if (new_time / 1000 == current_second){
                    _fps++;
                } else {
                    System.out.println("FPS: " + _fps);
                    current_second = new_time / 1000;
                    _fps = 1;
                }
            }
        }
    }

    protected Screen mParentScreen;
    protected SpriteBatch mSpriteBatch = new SpriteBatch();
    //protected GestureDetector.GestureListener mGesturesListener;
    protected WindowController mWindowController;
    protected TickThread mTickThread;

    protected OrthographicCamera mCamera;
    public int mScreenWidth;
    public int mScreenHeight;

    private final LinkedList<TextureObject> mObjects = new LinkedList<TextureObject>();

    public Window(Screen screen){
        mParentScreen = screen;

        mScreenWidth = Gdx.graphics.getWidth(); // TODO: refactor
        mScreenHeight = Gdx.graphics.getHeight();

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, mScreenWidth, mScreenHeight);

        mSpriteBatch.setProjectionMatrix(mCamera.combined);
    }

    public Screen getScreen(){
        return mParentScreen;
    }

    public void removeObjectFromStack(TextureObject object){
        synchronized (mObjects){
            mObjects.remove(object);
        }
    }

    public void removeObjectFromStack(List<? extends TextureObject> objects){
        synchronized (mObjects){
            mObjects.removeAll(objects);
        }
    }

    public void addObjectToStack(TextureObject object, TextureObject before){
        synchronized (mObjects){
            if (before != null){
                int index = mObjects.indexOf(before);
                mObjects.add(index, object);
            } else {
                mObjects.addLast(object);
            }
        }
    }

    public void addObjectToStack(List<? extends TextureObject> objects, TextureObject before){
        synchronized (mObjects){
            if (before != null){
                int index = mObjects.indexOf(before);
                for(TextureObject object: objects){
                    mObjects.add(index, object);
                }
            } else {
                mObjects.addAll(objects);
            }

        }
    }

    public LinkedList<TextureObject> getListenObjects(){
        return (LinkedList<TextureObject>) mObjects.clone();
    }

    protected final void render () {
        mSpriteBatch.begin();

        synchronized (mObjects){
            for (TextureObject obj: mObjects){
                if (obj.isVisible()){
                    obj.render(mSpriteBatch);
                }
            }
        }

        mSpriteBatch.end();
    }

    /*protected GestureDetector.GestureListener getInputProcessor(){
        return null;
    }*/

    protected WindowController getController(){
        return null;
    }

    public void init(){
        mWindowController = getController();
        //mGesturesListener = getInputProcessor();

        if (mWindowController != null){ // create thread, and execute tick's
            mTickThread = new TickThread();
            mTickThread.start();
        }
    }

    public void close(){

    }


}

