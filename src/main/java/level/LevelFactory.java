package level;

public class LevelFactory {
    
    public Level getLevel(int stage) {

        if (stage < 14) {
            return new Level(stage, 5, false);
        }
        else if (stage < 19) {
            return new Level(13, stage - 8, false);
        }
        else {
            return new Level(13, 10, true);
        }
    }
}
