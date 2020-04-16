

public class Config {

    /**
     * Boolean value to enter debug mode
     */
    static final boolean DEBUG = true;

    /**
     * Possible symbols in the code
     */
    public static final char[] CODE_SYMBOLS = new char[] {'1', '2', '3', '4', '5', '6'};
    
    /**
     * Length of 
     */
    public static final int CODE_POSITIONS = 4;

    /**
     * The maximum number of guesses for a user to win.
     */
    public static final int MAX_GUESSES = 10;

    /**
     *  The length of the hits array
     */
    public static final int HITS_ARRAY_LENGTH = 2;

    /**
     * A "black hit" indicates a matching symbol in the same position in the
     * hidden code and guess.
     */
    public static final int BLACK_HITS_INDEX = 0;

    /**
     * A "white hit" indicates a matching symbol but different position in the 
     * hidden code and guess that is not already accounted for with other 
     * hits.
     */
    public static final int WHITE_HITS_INDEX = 1;

    /**
     * The symbol that indicates a black hit.
     */
    public static final char BLACK_HITS_SYMBOL = 'B';

    /**
     * The symbol that indicates a white hit.
     */
    public static final char WHITE_HITS_SYMBOL = 'W';
}
