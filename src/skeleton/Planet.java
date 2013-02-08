package skeleton;

import com.badlogic.gdx.graphics.Texture;

/**
 * @author Yanis Biziuk
 */
public class Planet {
    public int mPosX;
    public int mPosY;
    public int mRadius;
    public Texture mTexture;

    public String mName = "temp";
    public int mCrystals = 1000;
    public int mGas = 200;

    public int mCrustalLevel = 1;
    public int mFactoryLevel = 0;
    public int mDefenseLevel = 0;

    public int mShips; // TODO
    public int mOwner; // TODO

    public Planet(int posX, int posY, int radius, Texture texture){
        mPosX = posX;
        mPosY = posY;
        mRadius = radius;
        mTexture = texture;
    }

}
