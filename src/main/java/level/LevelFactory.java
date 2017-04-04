package level;

public class LevelFactory {
    
    public Level getLevel(int stage, int score) {

        if (stage < 14) {
            return new Level(stage, 5, false, score);
        }
        else if (stage < 19) {
            return new Level(13, stage - 8, false, score);
        }
        else {
            return new Level(13, 10, true, score);
        }
    }
}
