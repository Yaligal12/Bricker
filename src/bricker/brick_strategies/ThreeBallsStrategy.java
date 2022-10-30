package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.util.Vector2;

public class ThreeBallsStrategy extends RemoveBrickStrategy{


    private GameObjectCollection object;
    private BrickerGameManager manager;
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * Constructor
     * @param object
     * @param manager
     * @param imageReader
     * @param soundReader
     */
    public ThreeBallsStrategy(GameObjectCollection object , BrickerGameManager manager, ImageReader imageReader, SoundReader soundReader) {
        super(object);
        this.manager = manager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * On collision do
     * @param thisObj
     * @param otherObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        CreateBalls(thisObj);
        super.onCollision(thisObj, otherObj);
    }


    /**
     * Creates three ball objects
     * @param thisObj
     */
    private void CreateBalls(GameObject thisObj){
        for (int i = 0; i < 3; i++) {
            manager.CreateMockBall(imageReader,soundReader,new Vector2(thisObj.getCenter().x(),thisObj.getCenter().y()+10));
        }
    }
}
