package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private Sound collisionSound;
    private BrickerGameManager manager;
    private GameObjectCollection object;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionSound the sound of collision
     * @param manager the game manager
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound, BrickerGameManager manager) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.manager = manager;
        this.object = new GameObjectCollection();
    }

    /**
     * Updating the game
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(this.getCenter().y()<=0){
            this.setCenter(new Vector2(this.getCenter().x(),this.getCenter().y()+30));
        }
        if(this.getCenter().x()<=0){
            this.setCenter(new Vector2(this.getCenter().x()+30,this.getCenter().y()));
        }
        if(this.getCenter().x()>=BrickerGameManager.X_BOARD +10){
            this.setCenter(new Vector2(this.getCenter().x()-30,this.getCenter().y()));
        }
        if(this.getCenter().y()>BrickerGameManager.X_BOARD +10){
            manager.DeleteMockBall(this);
        }
    }

    /**
     * When a collision occurring do something
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
    }
}
