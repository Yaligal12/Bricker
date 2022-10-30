package bricker.brick_strategies;

import danogl.GameObject;

public interface CollisionStrategy {
    /**
     * Function that identifies what's happening on a collision
     * @param thisObj
     * @param otherObj
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
