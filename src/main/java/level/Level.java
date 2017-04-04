package level;
import elements.*;
import java.util.Random;
import java.lang.Math;

public class Level {

    private Brick[][] bricks;

    private double px;
    private double py;

    private double bx;
    private double by;
    private double bxv;
    private double byv;

    private int lives;
    private int score;

    private int ROWS;
    private static final int COLS = 15;
    private static final int SCRH = 800;
    private static final int SCRW = 800;    

    /*
     * @param maxlayers Number of brick layers to use (1-12, 13 is infinite)
     * @param height Number of brick columns
     * @param rand True for every brick is random; false for every row is random
     * @param score Any score from previous rounds
     */
    public Level(int maxlayers, int height, boolean rand, int score) {

        // Initialize paddle
        px = (SCRW - Paddle.W) / 2;
        py = SCRH - 50 - Paddle.H / 2;

        // Initialize ball
        bx = SCRW / 2;
        by = py - Ball.R;
        bxv = 0;
        byv = 0;

        // Initialize stats 
        lives = 3;
        this.score = score;

        // Initialize bricks
        ROWS = height;
        bricks = new Brick[ROWS][15];
        Random r = new Random();

        if (rand) {
            for (int y = 0; y < ROWS; y++) {
                for (int x = 0; x < COLS; x++) {
                    bricks[y][x] = new Brick(r.nextInt(maxlayers));
                }
            }
        }
        else {
            for (int y = 0; y < ROWS; y++) {
                int layers = r.nextInt(maxlayers);

                for (int x = 0; x < COLS; x++) {
                    bricks[y][x] = new Brick(layers);
                }
            }
        }
    }

    /*
     * User interaction. Checks that paddle is
     * in bounds before updating.
     */
    public void movePaddle(double x) {
        if (x < 0 || x + Paddle.W > SCRW) return;
        px = x; 
    }

    /*
     * Main game logic method. 
     * @return 0 for nothing special;
     *      1 for all bricks destroyed;
     *      2 for game over;
     *      3 for life lost.
     */
    public int step() {
        if (lives == 0) return 2;
        else if (score == ROWS * COLS) return 1;

        if (checkPaddle()) {
           return 0;  
        }
        else if (checkBricks()) {
            return 0; 
        }
        else if (!checkWalls()) {

            lives--; 

            // Initialize paddle
            px = (SCRW - Paddle.W) / 2;
            py = SCRH - 50 - Paddle.H / 2;

            // Initialize ball
            bx = SCRW / 2;
            by = py - Ball.R;
            bxv = 0;
            byv = 0;

            return 3;
        }

        return 0;
    }

    /*
     * Returns false if ball hit bottom; otherwise
     * handles wall collisions and returns true.
     */
    private boolean checkWalls() {
        double r = Ball.R;

        // Hit bottom
        if (bx + r > SCRH) {
            return false; 
        }
        
        // Hit sides
        if (bx + r > SCRW || bx - r < 0) {
            bxv = - bxv;
        }

        // Hit top
        if (by - r < 0) {
            byv = - byv; 
        }

        return true;
    }

    /*
     * Returns false if no collision; otherwise
     * handles paddle collision and returns true.
     */
    private boolean checkPaddle() {

       if (ballCollision(px, py, Paddle.H, Paddle.W)) {
       
            // Add some variation to ball movement
            Random r = new Random();
            byv += (r.nextGaussian() - 0.5) * byv;

            // Give ball extra speed
            double scale = (bx - px) / Paddle.W;
            if (bxv < 0) {
                bxv += 2 * Math.log(scale + 0.5);
            }
            else {
                bxv -= 2 * Math.log(1.5 - scale);
            }

            return true;
       }
       return false;
    }

    /*
     * Returns false if no collision; otherwise
     * handles all brick collisions and returns true.
     */
    private boolean checkBricks() {

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {

                if (bricks[y][x] == null) continue;

                double brx = 27.5 + x * (Brick.W + 5);
                double bry = 50 + y * (Brick.H + 10); 

                if (ballCollision(brx, bry, Brick.H, Brick.W)) {
                    
                    if (bricks[y][x].hit()) {
                        bricks[y][x] = null;
                        score++;
                    }

                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Helper method to check for circle-rectangle collisions.
     * Returns true if there is one.
     */
    private boolean ballCollision(double x, double y, double h, double w) {

        int r = Ball.R;

        // Check for too far
        if (bx - r > x + w || bx + r < x) return false;
        if (by - r > y + h || by - r < y) return false;

        // Check for too close
        if (bx - r < x + w || bx + r > x) {
            bxv = -bxv; 
            return true;
        }
        if (by - r < y + h || by + r > y) {
            byv = -byv; 
            return false;
        }

        double xdist = Math.abs(bx - (x + w) / 2);
        double ydist = Math.abs(by - (y + h) / 2);

        if (Math.pow(xdist - h/2, 2) + Math.pow(ydist - w/2, 2) < r * r) {
            bxv = -bxv; 
            byv = -byv;
            return true;
        }
        return false;
    }
}
