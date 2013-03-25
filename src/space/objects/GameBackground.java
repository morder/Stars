package space.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import skeleton.TextureObject;
import skeleton.WindowController;

/**
 * @author Yanis Biziuk
 */
public class GameBackground extends TextureObject {

    private int deltaX;
    private int deltaY;

    public GameBackground(WindowController controller, TextureObject before, int GameX, int GameY, Texture texture) {
        super(controller, before, GameX, GameY, texture);
    }

    @Override
    public void render(SpriteBatch mSpriteBatch) {
        mSpriteBatch.disableBlending();
        mSpriteBatch.draw(getTexture(), 0, 0, getController().getScreenPosInGameX(), getController().getScreenPosInGameY(), getController().getWindow().mScreenWidth, getController().getWindow().mScreenHeight);
        mSpriteBatch.enableBlending();
    }


    public boolean _pan(float fromX, float fromY, float deltaX, float deltaY){
        this.deltaX += deltaX;
        this.deltaY += deltaY;
        return true;
    }

    public void tick(long delta_time) {
        dragScreen();
    }

    private void dragScreen(){
        int mCurrentGamePosX = getController().getScreenPosInGameX();
        int mCurrentGamePosY = getController().getScreenPosInGameY();

        if (deltaX != 0){
            int delta = (int) (Math.abs(deltaX) * 0.5);
            if (deltaX > 0){
                mCurrentGamePosX -= delta;
                deltaX -= delta;
            } else {
                mCurrentGamePosX -= -delta;
                deltaX += delta;
            }
            if (mCurrentGamePosX < 0) mCurrentGamePosX = 0;
            if (mCurrentGamePosX > getController().getGameWidth() - getController().getWindow().mScreenWidth) mCurrentGamePosX = getController().getGameWidth() - getController().getWindow().mScreenWidth;
        }
        if (deltaY != 0){
            int delta = (int) (Math.abs(deltaY) * 0.5);
            if (deltaY > 0){
                mCurrentGamePosY -= delta;
                deltaY -= delta;
            } else {
                mCurrentGamePosY -= -delta;
                deltaY += delta;
            }
            if (mCurrentGamePosY < 0) mCurrentGamePosY = 0;
            if (mCurrentGamePosY > getController().getGameHeight() - getController().getWindow().mScreenHeight) mCurrentGamePosY = getController().getGameHeight() - getController().getWindow().mScreenHeight;
        }
        getController().setScreenPosInGameX(mCurrentGamePosX);
        getController().setScreenPosInGameY(mCurrentGamePosY);
    }
}
