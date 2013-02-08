

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Yanis Biziuk
 */
public class Game implements ApplicationListener{
    Texture dropImage;
    Texture bucketImage;

    private Texture background;
    private Texture planet;
    private float BackgroundX = 0;
    private float BackgroundY = 0;

    private ArrayList<Vector2> planets = new ArrayList<Vector2>();

    Sound dropSound;
    Music rainMusic;
    SpriteBatch batch;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    Vector3 touchPos = new Vector3();

    int SceneWidth;
    int SceneHeight;

    int GameWidth;
    int GameHeight;

    FPSLogger fps = new FPSLogger();

    public static Game game;
    public static Game get(){
        return game;
    }

    public void moveBG(float deltaX, float deltaY){
        BackgroundX -= deltaX;
        BackgroundY -= deltaY;

        if (BackgroundX < 0) BackgroundX = 0;
        if (BackgroundX > GameWidth - SceneWidth) BackgroundX = GameWidth - SceneWidth;

        if (BackgroundY < 0) BackgroundY = 0;
        if (BackgroundY > GameHeight - SceneHeight) BackgroundY = GameHeight - SceneHeight;
    }

    public void create () {

        game = this;

        SceneWidth = Gdx.graphics.getWidth();
        SceneHeight = Gdx.graphics.getHeight();

        Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener()));

        Texture.setEnforcePotImages(false);

        // load the images for the droplet and the bucket, 48x48 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        background = new Texture(Gdx.files.internal("1680x1050.jpg"));
        GameWidth = background.getWidth();
        GameHeight = background.getHeight();

        planet = new Texture(Gdx.files.internal("planet.png"));
        Random random = new Random();
        for(int i=0;i<10;i++){
            planets.add( new Vector2(random.nextInt(GameWidth - 100) + 50, random.nextInt(GameHeight - 100) + 50) );
        }

        // load the drop sound effect and the rain mBackground "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // start the playback of the mBackground music immediately
        rainMusic.setLooping(true);
        rainMusic.play();

        // create the mCamera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SceneWidth, SceneHeight);
        batch = new SpriteBatch();

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = SceneWidth / 2 - 48 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        bucket.width = 48;
        bucket.height = 48;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, SceneWidth - 48);
        raindrop.y = SceneHeight;
        raindrop.width = 48;
        raindrop.height = 48;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    private FrameBuffer m_fbo = null;
    private float m_fboScaler = 2f;
    private TextureRegion m_fboRegion = null;

    @Override
    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 1f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // tell the mCamera to update its matrices.
        camera.update();


        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if(true)      // enable or disable the supersampling
        {
            if(m_fbo == null)
            {
                // m_fboScaler increase or decrease the antialiasing quality
                m_fbo = new FrameBuffer(Pixmap.Format.RGB565, (int)(width * m_fboScaler), (int)(height * m_fboScaler), false);
                m_fboRegion = new TextureRegion(m_fbo.getColorBufferTexture());
                m_fboRegion.flip(false, true);
            }

            m_fbo.begin();
        }


        // tell the SpriteBatch to render in the
        // coordinate system specified by the mCamera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();

        batch.disableBlending();
        batch.draw(background, 0, 0, (int)BackgroundX, (int)BackgroundY, SceneWidth, SceneHeight);
        batch.enableBlending();

        for(Vector2 coord: planets){
            batch.draw(planet, coord.x - BackgroundX, (GameHeight - coord.y) - (GameHeight - BackgroundY - SceneHeight), 50, 50);
        }



        /*
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(mCamera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.line(0, 0, 200, 100);
        shapeRenderer.line(200, 100, 500, 300);
        shapeRenderer.end();


        Gdx.gl20.glLineWidth(5);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 1, 0.3f);
        shapeRenderer.line(100, 100, 300, 300);
        shapeRenderer.end();
        */



        Pixmap pixmap;
        Texture texture;
        pixmap = new Pixmap(800, 480, Pixmap.Format.RGBA8888); // Pixmap.Format.RGBA8888);



        pixmap.setColor(1.0f, 0.0f, 0.0f, 1.0f); // Red
        pixmap.drawLine(0, 0, 100, 100);

        pixmap.setColor(0.0f, 0.0f, 1.0f, 1.0f); // Blue
        pixmap.drawLine(100, 100, 200, 0);

        pixmap.setColor(0.0f, 1.0f, 0.0f, 1.0f); // Green
        pixmap.drawLine(100, 0, 100, 100);

        pixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f); // White
        pixmap.drawCircle(400, 300, 100);


        // Create a texture to contain the pixmap
        //texture = new Texture(1024, 1024, Pixmap.Format.RGBA8888); // Pixmap.Format.RGBA8888);
        texture = new Texture(pixmap);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.MipMapLinearLinear);
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        //texture.draw(pixmap, 0, 0);






        batch.draw(texture, 0, 0);

        //pixmap.dispose();




        batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle raindrop: raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();

        // process user input
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 48 / 2;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x >  - 48) bucket.x = SceneWidth - 48;

        // check if we need to create a new raindrop
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we play back
        // a sound effect as well.
        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 48 < 0) iter.remove();
            if(raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
            }
        }


        if(m_fbo != null)
        {
            m_fbo.end();

            batch.begin();
            batch.draw(m_fboRegion, 0, 0, width, height);
            batch.end();
        }




        double deltaTime = Gdx.graphics.getDeltaTime();
        //System.out.println("delta time:" + deltaTime );

        double limitTime = 1000f / 60 / 1000;
        if (deltaTime < limitTime){
            try {
                Thread.sleep((long) ((limitTime - deltaTime) * 1000) );
            } catch (InterruptedException ignored) {
            }
        }

        fps.log();
    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}