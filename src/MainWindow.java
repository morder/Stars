import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import skeleton.Planet;
import skeleton.Screen;
import skeleton.Window;
import skeleton.WindowController;

/**
 * @author Yanis Biziuk
 */
public class MainWindow extends Window {
    private MainController mMainController;
    private MainController C;
    private Texture pixel;

    public MainWindow(Screen screen){
        super(screen);
    }

    public void init(){
        C = mMainController = new MainController();
        pixel = new Texture(Gdx.files.internal("pixel.png"));
        super.init();
    }

    public void close(){

    }

    Long current_second = System.currentTimeMillis();
    int _fps = 0;

    public float gameToScreenX(float X){
        return X - C.mCurrentScreenPosX;
    }

    public float gameToScreenY(float Y){
        // с преобразованием к нижнему левому углу
        return (C.mGameHeight - Y) - (C.mGameHeight - C.mCurrentScreenPosY - mScreenHeight);
    }

    public float screenToGameX(float X){
        return X + C.mCurrentScreenPosX;
    }

    public float screenToGameY(float Y){
        return Y + C.mCurrentScreenPosY;
    }

    protected void render () {
        mSpriteBatch.begin();

        mSpriteBatch.disableBlending();
        mSpriteBatch.draw(C.mBackground, 0, 0, C.mCurrentScreenPosX, C.mCurrentScreenPosY, C.mScreenWidth, C.mScreenHeight);
        mSpriteBatch.enableBlending();

        for(Planet planet: C.mPlanets){
            mSpriteBatch.draw(planet.mTexture, gameToScreenX(planet.mPosX), gameToScreenY(planet.mPosY), planet.mRadius * 2, planet.mRadius * 2);
            mSpriteBatch.draw(pixel, gameToScreenX(planet.mPosX), gameToScreenY(planet.mPosY));
        }



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
    }

    protected GestureDetector.GestureListener getInputProcessor(){
        return mMainController.mGesturesListener;
    }

    protected WindowController getController(){
        return mMainController;
    }
}
