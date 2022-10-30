package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class RemoveBrickStrategy implements CollisionStrategy {
    private GameObjectCollection object;

    /**
     * Constructor
     * @param object
     */
    public RemoveBrickStrategy(GameObjectCollection object) {
        this.object = object;
    }


    /**
     * When a collision occurring do something
     * @param thisObj
     * @param otherObj
     */
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.object.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        this.object.removeGameObject(thisObj);
        if (thisObj instanceof Brick && ((Brick) thisObj).exist == true) {
            BrickerGameManager.BRICK_COUNTER.decrement();
            ((Brick) thisObj).exist = false;
        }
    }
}
