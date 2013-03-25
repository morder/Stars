package space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import skeleton.Screen;
import skeleton.Window;
import skeleton.WindowController;
import space.MainController;

/**
 * @author Yanis Biziuk
 */
public class MainWindow extends Window {
    private MainController mMainController;
    private MainController C;

    private Texture checker;
    private Texture pixel;

    private Texture mPlanetLayer;

    private BitmapFont font20;
    private BitmapFont font16;

    public MainWindow(Screen screen){
        super(screen);
    }

    public void init(){
        C = mMainController = new MainController(this);

        pixel = new Texture(Gdx.files.internal("pixel.png"));
        checker = new Texture(Gdx.files.internal("checker.png"));

        mPlanetLayer = new Texture(Gdx.files.internal("layer.png"));

        font20 = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        font16 = new BitmapFont(Gdx.files.internal("font16.fnt"), Gdx.files.internal("font16.png"), false);

        font20.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font16.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        super.init();
    }

    public void close(){

    }

    Long current_second = System.currentTimeMillis();
    int _fps = 0;


    /*protected void render () {
        mSpriteBatch.begin();

        mSpriteBatch.disableBlending();
        mSpriteBatch.draw(C.mBackground, 0, 0, C.mCurrentGamePosX, C.mCurrentGamePosY, mScreenWidth, C.mScreenHeight);
        mSpriteBatch.enableBlending();

        if (C.mSelectedPlanet != null){
            mSpriteBatch.draw(mPlanetLayer, C.mSelectedPlanet.getScreenX() - 127, C.mSelectedPlanet.getScreenY() - 126);
        }

        for(Planet planet: C.mPlanets){
            mSpriteBatch.draw(planet.getTexture(), planet.getScreenX(), planet.getScreenY(), planet.mRadius * 2, planet.mRadius * 2);

            if (C.mPlayerHuman.mPlanets.contains(planet)){
                mSpriteBatch.draw(pixel, planet.getScreenX() + 25-3, planet.getScreenY() + 25-3);
            }

            //mSpriteBatch.draw(checker, planet.getScreenX() - 40, planet.getScreenY() + 48);
            //mSpriteBatch.draw(checker, planet.getScreenX() - 40, planet.getScreenY() - 20);
            //mSpriteBatch.draw(checker, planet.getScreenX() + 65, planet.getScreenY() + 48);

            font20.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            font20.draw(mSpriteBatch, planet.mCrystalLevel + "", planet.getScreenX() - 15, planet.getScreenY());
            font20.setColor(0.8f, 0.8f, 1.0f, 1.0f);
            font20.draw(mSpriteBatch, planet.getCrystals() + "", planet.getScreenX() + 15, planet.getScreenY());
            font20.setColor(0.8f, 1.0f, 0.8f, 1.0f);
            font20.draw(mSpriteBatch, planet.getGas() + "", planet.getScreenX() + 15, planet.getScreenY() - 20);

            font20.setColor(1.0f, 0.8f, 1.0f, 1.0f);
            font20.draw(mSpriteBatch, planet.mDefenseLevel + "", planet.getScreenX() - 15, planet.getScreenY() + 70);

            font20.setColor(1.0f, 1.0f, 0.8f, 1.0f);
            font20.draw(mSpriteBatch, planet.mFactoryLevel + "", planet.getScreenX() + 50, planet.getScreenY() + 70);
        }


        font20.setColor(0.6f, 0.6f, 1.0f, 1.0f);
        font20.draw(mSpriteBatch, C.mPlayerHuman.mCrystals + "", 350, 470);
        font20.setColor(0.6f, 1.0f, 0.6f, 1.0f);
        font20.draw(mSpriteBatch, C.mPlayerHuman.mGas + "", 450, 470);


        mSpriteBatch.end();

        // fps
        long new_time = System.currentTimeMillis();
        if (new_time / 1000 == current_second){
            _fps++;
        } else {
            if (_fps < 60){
                System.out.println("FPS render: " + _fps);
            }
            current_second = new_time / 1000;
            _fps = 1;
        }
    }*/

    /*protected GestureDetector.GestureListener getInputProcessor(){
        return mMainController.mGesturesListener;
    }*/

    protected WindowController getController(){
        return mMainController;
    }
}
