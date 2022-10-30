package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class FastOrSlow extends GameObject {


    private boolean fast;
    private BrickerGameManager manager;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public FastOrSlow(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, boolean fast, BrickerGameManager manager) {
        super(topLeftCorner, dimensions, renderable);
        this.fast=fast;
        this.manager = manager;
    }

    /**
     * On collision, depends on the type makes the game faster or slower
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (BrickerGameManager.FASTORSLOW_COUNTER.value() % 2 == 1) {
            if (this.fast) {
                manager.windowController.setTimeScale(1.1f);
            }
            if (!this.fast) {
                manager.windowController.setTimeScale(0.9f);
            }
        }
        BrickerGameManager.FASTORSLOW_COUNTER.increment();
        super.onCollisionEnter(other, collision);
        manager.DeleteBigOrSmall(this);
    }

    /**
     * Check if the instance that collides is the paddle
     * @param other The other GameObject.
     * @return
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if(other instanceof Paddle){
            return true;
        }
        return false;
    }
}

