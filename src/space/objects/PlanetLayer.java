package space.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import skeleton.TextureObject;
import skeleton.WindowController;

/**
 * @author Yanis Biziuk
 */
public class PlanetLayer extends TextureObject {

    public PlanetLayer(WindowController controller, TextureObject before, int GlobalX, int GlobalY, Texture texture) {
        super(controller, before, GlobalX, GlobalY, texture);
    }

    @Override
    public void render(SpriteBatch mSpriteBatch) {
        mSpriteBatch.draw(getTexture(), getScreenX(), getScreenY());
    }
}
