package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Gravity extends GameObject {

    private BrickerGameManager manager;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param manager
     */
    public Gravity(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager manager) {
        super(topLeftCorner, dimensions, renderable);
        this.manager = manager;
    }

    /**
     * On collision do something
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(this.manager.ball.getVelocity().y()>0){
            this.manager.ball.setVelocity(new Vector2(this.manager.ball.getVelocity().x(),this.manager.ball.getVelocity().y()*(-1)));
        }
        super.onCollisionEnter(other, collision);
        manager.DeleteGravity(this);
    }

    /**
     * Collide only with a paddle
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
