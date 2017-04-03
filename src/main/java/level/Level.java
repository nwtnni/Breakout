package level;
import elements.Brick;
import java.util.Random;

public class Level {

    private Brick[][] bricks;
    private static final int WIDTH = 15;
    
    public Level(int maxlayers, int height, boolean rand) {
        
        bricks = new Brick[height][15];
        Random r = new Random();

        if (rand) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    bricks[y][x] = new Brick(r.nextInt(maxlayers));
                }
            }
        }
        else {
            for (int y = 0; y < height; y++) {
                int layers = r.nextInt(maxlayers);

                for (int x = 0; x < WIDTH; x++) {
                    bricks[y][x] = new Brick(layers);
                }
            }
        }
    }

    public Brick[][] getBricks() {
        return bricks; 
    }
}
