package rover;

/**
 * Should be self-explanatory - where is rover headed and how is coordinates supposed to change
 */
public enum Heading {
    N(0,1), E(1,0), S(0,-1), W(-1, 0);
    public final int dx;
    public final int dy;
    Heading(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Maybe there is a more elegant to write this with other enums for larger solutions, but for this, it should be good enough
     * @param instruction
     * @return
     */
    public Heading turn(char instruction) {
        switch (instruction) {
            case 'L':
                return switch (this) {
                    case N -> W;
                    case E -> N;
                    case S -> E;
                    case W -> S;
                };
            case 'R':
                return switch (this) {
                    case N -> E;
                    case E -> S;
                    case S -> W;
                    case W -> N;
                };
            default: // Moving forward does not change the heading
                return this;
        }
    }
}
