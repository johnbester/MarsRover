# MarsRover
Red Badger Coding Challenge

#### Language: I chose Java, simply because I have the most experience in it and I like working in IntelliJ idea. 
#### The project is structured as a Maven project and "mvn install" should build and execute the default test case.
#### If not run as a JUnit test case, the app reads input from stdin and writes output to stdout
#### A scanner is used to read the input, so input is blocked if not according to spec
    In a real world scenario, I prefer having code more robust and being ready for edge cases.

#### Last known coordinates
    From the spec if it not clear what coordinates should be printed. From the sample output it seems to be the scented 
    square, so that is how I implemented it.

#### Drop off prevention
    From the spec a few possible algorithms can be implemented to prevent future rovers to drop off the edge:
        1. A future rover lands on a scented square - no further checks.
        2, A future rover lands on a scented square and has the same direction as the one that dropped off.
        3. A scented line is conceived - for example, there is scent in a square in y=5 and last known direction is north,
            this means any future rover on a square anywhere with y=5 will drop off if facing north.
        4. Option 3 can be extended for multiple borders
    I decided to implement option 2, because I think it encapsulates what the task intended. In a real world
    scenario, I would like more clarity on how edge cases should be handled because this is where unintended
    bugs kreep in.

#### Input case sensitivity
    For the purpose of this excercise, this is not important because the spec does describe which 
    that all inputs are in upper case. It is however something I like to keep in mind for edge case
    scenarios. It can easily be toggled with a global constant to tell the compiler what the desired
    functionality should be. It only becomes an issue with extreme high data volumnes where changing
    case adds significant processing power, or were an incorrect case is expected to fail.

#### Value edge cases
    I did add some checks to validate values. The spec does specify what can be expected, but taking
    care of these edge cases in code does end up with code that can be more easily debugged.
