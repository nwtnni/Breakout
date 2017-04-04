package level;

public class LevelFactory {
    
    // Level generation
    public Level getLevel(int stage, int score) {
        if (stage == 1) {
            return new Level(4, 5, false, score);
        }
        else if (stage == 2) {
            return new Level(8, 5, false, score);
        }
        else if (stage == 3) {
            return new Level(12, 5, false, score);
        }
        else {
            return new Level(12, 10, true, score);
        }
    }
}
