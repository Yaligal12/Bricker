package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

public class GravityStategy extends RemoveBrickStrategy{

    private BrickerGameManager manager;
    private GameObjectCollection object;
    private ImageReader imageReader;

    /**
     * Constructor
     * @param object
     * @param manager
     * @param imageReader
     */
    public GravityStategy(GameObjectCollection object, BrickerGameManager manager, ImageReader imageReader) {
        super(object);
        this.manager = manager;
        this.imageReader = imageReader;
    }

    /**
     * On collision do something
     * @param thisObj
     * @param otherObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        Vector2 vec= thisObj.getCenter();
        super.onCollision(thisObj, otherObj);
        manager.CreateGravity(imageReader, vec);
    }
}
