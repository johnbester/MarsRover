package rover;

public class Rover {
    private int x; // Position (x)
    private int y; // Position (y)
    private int scentX = -1; // Scented location (x)
    private int scentY = -1; // Scented location (y)
    private boolean lost = false; // You could check x and y to determine lost, but this is just more logical
    private Heading heading; // How should you move forward?
    public Rover(int x, int y, char heading)
    {
        this.x = checkCoordinate(x);
        this.y = checkCoordinate(y);
        this.heading = checkHeading(heading);
    }

    /**
     * Validity ensured by spec, but caution is never wasted
     * @param coordinate
     * @return
     */
    private int checkCoordinate(int coordinate) {
        if (coordinate >= 0 && coordinate <= 50)
            return coordinate;
        throw new IllegalArgumentException("Invalid coordinate: " + coordinate);
    }

    /**
     * Convert a character heading to an enum - this makes it easy to use addition and subtraction
     * to turn the rover. Validity and case sensitivity ensured by spec, but caution is never wasted
     * @param heading
     * @return
     */
    private Heading checkHeading(char heading) {
        heading = Character.toUpperCase(heading);
        return switch (heading) {
            case 'N' -> Heading.N;
            case 'E' -> Heading.E;
            case 'S' -> Heading.S;
            case 'W' -> Heading.W;
            default -> throw new IllegalArgumentException("Invalid heading: " + heading);
        };
    }

    /**
     * Validity and case sensitivity ensured by spec, but caution is never wasted
     * @param instruction
     * @return
     */
    private char checkInstruction(char instruction) {
        instruction = Character.toUpperCase(instruction);
        return switch (instruction) {
            case 'L', 'R', 'F' -> instruction;
            default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
        };
    }

    /**
     * Before rover can go off a cliff, you need to update the last place it leaves its scent
     */
    private void placeScent() {
        this.scentX = this.x;
        this.scentY = this.y;
    }

    /**
     * Move one block forward
     * @param surface
     * @return
     */
    private boolean forward(Surface surface) {
        if (lost)
            return false;
        // Check whether other rovers were lost in this position
        if (!surface.safeToMove(this)) {
            return false;
        }
        placeScent();
        this.x += heading.dx;
        this.y += heading.dy;
        if (this.x < 0 || this.y < 0 || this.x > surface.x || this.y > surface.y) {
            this.lost = true;
            return false;
        }
        return true;
    }

    /**
     * Go can either be a turn or a move forward and is the only public function to move
     * @param instruction
     * @param surface
     * @return
     */
    public boolean go(char instruction, Surface surface) {
        instruction = checkInstruction(instruction);
        switch (instruction) {
            case 'L', 'R':
                this.heading = this.heading.turn(instruction);
                return true;
            case 'F':
                return forward(surface);
            default:
                throw new IllegalArgumentException("Invalid instruction: " + instruction); // Technically redundant, but causes compile error when left out and make preceding code more readable
        }
    }

    /**
     * First sign of trouble - on a scented square
     * @param lostRover
     * @return
     */
    public boolean inScentedSquare(Rover lostRover) {
        if (lostRover == null)
            return false;
        return this.x == lostRover.scentX && this.y == lostRover.scentY;
    }

    /**
     * If on a scented square and rover is moving in the same direction as a lost rover, you are headed for disaster
     * @param lostRover
     * @return
     */
    public boolean sameDirection(Rover lostRover) {
        if (lostRover == null)
            return false;
        return this.heading == lostRover.heading;
    }

    public String toString() {
        if (lost)
            return String.format("%d %d %s LOST", scentX, scentY, heading.toString()).trim();
        return String.format("%d %d %s", x, y, heading.toString()).trim();
    }

    public boolean isLost() {
        return lost;
    }

    public void clearScent() {
        this.scentX = -1;
        this.scentY = -1;
    }
}
