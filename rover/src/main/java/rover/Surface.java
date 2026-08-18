package rover;

import java.util.ArrayList;
import java.util.List;

public class Surface {
    public final int x;
    public final int y;
    private final List<Rover> lost = new ArrayList<>();
    public Surface(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Call this when a rover has gone off grid
     * @param rover
     */
    public void addLostRover(Rover rover) {
        this.lost.add(rover);
    }

    /**
     * Determine whether it is safe to move forward
     * @param movingRover
     * @return
     */
    public boolean safeToMove(Rover movingRover) {
        if (this.lost.isEmpty())
            return true;
        for (Rover lostRover : this.lost) {
            // If not on a scented square, move on to the next lost rover
            if (!movingRover.inScentedSquare(lostRover))
                continue;
            // If going in a different direction, it is considered safe
            if (movingRover.sameDirection(lostRover))
                return false;
        }
        return true;
    }
}
