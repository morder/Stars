package skeleton;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Yanis Biziuk
 */

// класс который отвечает за ротацию Screen, рендеринг Screen и window, передачу им нажатий на экране т.п.
public abstract class Game implements ApplicationListener {
    Screen mCurrentScreen = null;
    OrthographicCamera camera;
    int SceneWidth;
    int SceneHeight;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float v, float v2, int i, int i2) {
                return mCurrentScreen.touchDown(v, v2, i, i2);
            }

            @Override
            public boolean tap(float v, float v2, int i, int i2) {
                return mCurrentScreen.tap(v, v2, i, i2);
            }

            @Override
            public boolean longPress(float v, float v2) {
                return mCurrentScreen.longPress(v, v2);
            }

            @Override
            public boolean fling(float v, float v2, int i) {
                return mCurrentScreen.fling(v, v2, i);
            }

            @Override
            public boolean pan(float v, float v2, float v3, float v4) {
                return mCurrentScreen.pan(v, v2, v3, v4);
            }

            @Override
            public boolean zoom(float v, float v2) {
                return mCurrentScreen.zoom(v, v2);
            }

            @Override
            public boolean pinch(Vector2 vector2, Vector2 vector22, Vector2 vector23, Vector2 vector24) {
                return mCurrentScreen.pinch(vector2, vector22, vector23, vector24);
            }
        }));

        SceneWidth = Gdx.graphics.getWidth();
        SceneHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SceneWidth, SceneHeight);
    }

    public void setScreen(Screen screen){
        if (mCurrentScreen != null && mCurrentScreen != screen){
            mCurrentScreen.close();
        }
        screen.init();
        mCurrentScreen = screen;
    }

    public void preRender(){

    }

    public void postRender(){

    }

    @Override
    public final void render() {
        Gdx.gl.glClearColor(0, 0, 1f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();

        preRender();

        if (mCurrentScreen != null) {
            mCurrentScreen.render();
        }

        postRender();
    }

    @Override
    public void resize(int i, int i2) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
