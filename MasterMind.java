/////////////////////////////////////////// FILE  HEADER /////////////////////////////////////////////
//
// Title: MasterMind
// Files: MasterMind.java, TestMasterMind.java, Config.java
// This File: MasterMind.java
// 
// Name: Benjamin Tarmann
// Email: btarmann@wisc.edu
//
///////////////////////////////////////// 100 COLUMNS WIDE /////////////////////////////////////////

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * This class contains the code to play the game Master Mind, a game in which the user must guess a
 * generated code and is given hints about how close their guess is to the hidden code to help them
 * determine the hidden code. These methods are public since they are used to test this class and to
 * refer to constants in Config.java.
 * 
 * @author Benjamin Tarmann
 */
public class MasterMind {

    /**
     * Prompts the user for a value by displaying prompt. 
     *
     * After prompting the user, the method will consume an entire line of input while reading an
     * int. Leading whitespace is ignored. If the value read is between min and max (inclusive),
     * that value is returned. Otherwise, output "Expected value between 0 and 10." where 0 and 10
     * are the values in the min and max parameters, respectively. Invalid input may be non-integer
     * in which case the same error message is displayed and the user is prompted again.
     *
     * @param input  The Scanner instance to read from System.in.
     * @param prompt Output to the user.
     * @param min    The minimum acceptable int value (inclusive).
     * @param min    The maximum acceptable int value (inclusive).
     * @return Returns the value read from the user.
     */
    public static int promptInt(Scanner input, String prompt, int min, int max) {
        // prompts the user to input an int
        System.out.print(prompt);

        // checks whether the input is a valid int or not
        int userInt;
        Boolean validInput = false;
        while (validInput == false) {
            if (input.hasNextInt()) {
                userInt = input.nextInt();
                if (userInt < min || userInt > max) {
                    System.out.println("Expected value between " + min + " and " + max + ".");
                    System.out.print(prompt);
                } else {
                    return userInt;
                }
            } else { // prompts the user to input another int if original is not valid
                input.nextLine();
                System.out.println("Expected value between " + min + " and " + max + ".");
                System.out.print(prompt);
            }
        }
        return 0; // only returns 0 if loop is exited, which it should not
    }

    /**
     * Returns the index within arr of the first occurrence of the specified character.
     * If arr is null or 0 length then return -1
     * 
     * @param arr  The array to look through.
     * @param ch   The character to look for.
     * @return The index within the array of the first occurrence of the specified
     *         character or -1 if the character is not found in the array.
     */
    public static int indexOf(char[] arr, char ch) {
        if (arr == null || arr.length == 0) {
            return -1; // -1 returned if the array is empty or has a length of 0
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ch) {
                return i; // first index of the symbol is returned
            }
        }
        return -1; // -1 is also returned if the array does not contain that symbol
    }

    /**
     * Generates the hidden code to be guessed by the user. The hidden code
     * returned is an array of characters with length numPositions.
     * The characters in the array are randomly chosen, in order starting at index 0,
     * from the symbols array.
     * 
     * rand.nextInt( symbols.length) is used to determine the index in the symbols array of each 
     * character in the code.
     * 
     * Example: 
     * if numPositions is 3 and symbols is the array {'A','B','C'}
     * the returned array will have a length of 3 and may contain any selection of 
     * the available symbols such as {'B','C','B'} or {'C','A','B'}.
     * 
     * @param rand A random number generator.
     * @param numPositions  The number of symbols in the code.
     * @param symbols  The symbols to choose from.
     * @return An array of length numPositions of randomly chosen symbols.
     */
    public static char[] generateHiddenCode(Random rand, int numPositions, char[] symbols) {
        char[] hiddenCode = new char[numPositions];

        // for loop generates code and stores it in an array
        for (int i = 0; i < hiddenCode.length; i++) {
            hiddenCode[i] = symbols[rand.nextInt(symbols.length)];
        }

        return hiddenCode;
    }

    /**
     * Checks whether the code is the correct length and only contains valid symbols.
     * Uses the indexOf method to check whether each character in the input is in the 
     * symbols array. If code or symbols are null then false is returned.
     *   
     * @param numPositions  The required number of symbols in the code.
     * @param symbols  The allowed symbols in the code.
     * @param code  The code that is being checked.
     * @return true if the code is the correct length and has only valid symbols otherwise
     * returns false.
     */
    public static boolean isValidCode(int numPositions, char[] symbols, char[] code) {
        boolean validCode = true; // only changes to false if code is found to be invalid

        if (code.length != numPositions) { // checks code is the appropriate length
            validCode = false;
            return validCode;
        }

        for (int i = 0; i < code.length; i++) { // checks if the code contains valid characters
            if (indexOf(symbols, code[i]) == -1) {
                validCode = false;
                return validCode;
            }
        }

        return validCode; // will always return true if reached
    }

    /**
     * Prompts the user for a string value by displaying prompt.
     *
     * After prompting the user, the method will read an entire line of input and remove
     * leading and trailing whitespace. If the line equals the single character '?'
     * then return null. If the line is a valid code (determine with isValidCode) return
     * the code, otherwise print "Invalid code." and prompt again.
     *
     * @param input The Scanner instance to read from System.in
     * @param prompt The user prompt.
     * @param numPositions The number of code positions.
     * @param symbols The valid symbols.
     * @return Returns null or a valid code.
     */
    public static char[] promptForGuess(Scanner input, String prompt, int numPositions,
        char[] symbols) {
        System.out.print(prompt); // prompts the user to enter a guess
        String userGuess = input.nextLine();
        userGuess = userGuess.trim();
        char[] userGuessArray = new char[userGuess.length()];

        // null is returned if the user indicates that they want help
        if (userGuess.equals("?")) {
            return null;
        }

        // stores the guess in an array
        for (int i = 0; i < userGuessArray.length; i++) {
            userGuessArray[i] = userGuess.charAt(i);
        }

        // determines if the guess is valid and prompts the user again if it is not
        while (isValidCode(numPositions, symbols, userGuessArray) == false) {
            System.out.println("Invalid code.");
            System.out.print(prompt);
            userGuess = input.nextLine();
            userGuess = userGuess.trim();

            // again determines if the user wants help after they already enter an invalid code
            if (userGuess.equals("?")) {
                return null;
            }

            // stores the new guess in an array to be checked again
            userGuessArray = new char[userGuess.length()];
            for (int i = 0; i < userGuessArray.length; i++) {
                userGuessArray[i] = userGuess.charAt(i);
            }
        }

        return userGuessArray;
    }

    /**
     * Returns the sum of "black hits" and "white hits" between the hiddenCode 
     * and guess.  A "black hit" indicates a matching symbol in the same position in the
     * hiddenCode and guess.  A "white hit" indicates a matching symbol but different
     * position in the hiddenCode and guess that is not already accounted for with other 
     * hits.
     * 
     * Algorithm to determine the total number of hits:
     * 
     * Count the number of each symbol in the hiddenCode, and separately count the
     * number of each symbol in the guess. For each symbol, determine the minimum of the
     * count of that symbol in the hiddenCode and the count of that symbol found in the guess.  
     * The total number of hits, black and white, is the sum of these minimums for 
     * all the symbols.
     * 
     * Algorithm based on Donald Knuth, 1976, The Computer As Master Mind, 
     * J. Recreational Mathematics, Vol. 9(1)
     *  
     * @param hiddenCode The code hidden from the user.
     * @param guess  The user's guess of the code.
     * @param symbols  The possible symbols in the hiddenCode and guess.
     * @return The total number of hits.
     */
    public static int countAllHits(char[] hiddenCode, char[] guess, char[] symbols) {
        int[] numHiddenCodeSymbols = new int[symbols.length];
        int[] numGuessSymbols = new int[symbols.length];
        int numHits = 0;

        // counts the number of each symbol in the guess
        for (int i = 0; i < guess.length; i++) {
            numGuessSymbols[indexOf(symbols, guess[i])]++;
        }

        // counts the number of each symbol in the hidden code
        for (int i = 0; i < hiddenCode.length; i++) {
            numHiddenCodeSymbols[indexOf(symbols, hiddenCode[i])]++;
        }

        // counts total number of hits
        for (int i = 0; i < symbols.length; i++) {
            int min;
            if (numHiddenCodeSymbols[i] >= numGuessSymbols[i]) {
                min = numGuessSymbols[i];
            } else {
                min = numHiddenCodeSymbols[i];
            }
            numHits = numHits + min;
        }

        return numHits;
    }

    /**
     * Returns the number of each kind of hit the guess has with the code. 
     * The results are an array of length Config.HITS_ARRAY_LENGTH. 
     * The count of the number of symbols in the guess that correspond in position 
     * and symbol with the hidden code are recorded in the Config.BLACK_HITS_INDEX 
     * position within the result array. The number of symbols that match between
     * the guess and the hidden code but are in different positions and not otherwise
     * counted are recorded in the Config.WHITE_HITS_INDEX within the result array.
     * 
     * Algorithm:
     * Count the number of black hits - the number of positions in the guess and hidden code
     * that have the same symbol.
     * Count the total number of hits using countAllHits and subtract to find the number
     * of white hits. White hits are symbols that match between guess and hiddenCode that
     * are not in the same position and not already accounted for with other hits.
     * 
     * @param hiddenCode  The code the user is trying to guess.
     * @param guess  The user's guess.
     * @param symbols  The possible symbols in the hiddenCode and guess.
     * @return The array containing the number of "black hits" and "white hits".
     */
    public static int[] determineHits(char[] hiddenCode, char[] guess, char[] symbols) {
        int[] numHits = new int[Config.HITS_ARRAY_LENGTH];

        // determines the number of black hits
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == hiddenCode[i]) {
                numHits[Config.BLACK_HITS_INDEX]++;
            }
        }

        // determines the number of white hits by subtracting the number of black hits from the
        // total
        numHits[Config.WHITE_HITS_INDEX] =
            countAllHits(hiddenCode, guess, symbols) - numHits[Config.BLACK_HITS_INDEX];

        return numHits;
    }

    /**
     * Prints out the game board showing the guesses and the corresponding hits.
     * See output examples. 
     * Game board example:
     *  6) [4, 5, 2, 4] BBBB
     *  5) [4, 4, 2, 5] BBWW
     *  4) [4, 4, 2, 4] BBB
     *  3) [1, 3, 3, 3] 
     *  2) [2, 3, 3, 3] W
     *  1) [1, 1, 2, 2] B
     * 
     * Only rows with non-null guesses are shown. The number on the left is the guess, 
     * so the guesses are shown from last to first.
     * Looking at one line in detail:
     *  5) [4, 4, 2, 5] BBWW
     * 				      ^^  2 white hits, the 2nd 4 and 5 (we don't know which until solved)
     *                  ^^ 2 black hits, the 1st 4 and 2 (we don't know which until solved)
     *     ^^^^^^^^^^^^ the guess output using Arrays.toString()
     *  ^^ the guess number
     * The symbols B and W are the characters from Config.BLACK_HIT_SYMBOL and
     * Config.WHITE_HIT_SYMBOL. All the black hits will be shown before the white hits.
     * 
     * @param guesses  The array of guesses. Each row is a guess or null (meaning no guess
     * yet).
     * @param hits  The array of hits. Each row is the hits from determineHits for 
     * the corresponding guess in the parallel guesses array, or null.
     */
    public static void printBoard(char[][] guesses, int[][] hits) {
        for (int i = guesses.length - 1; i >= 0; i--) {

            // while loop is there to determine the correct number of rows are being printed
            boolean exitWhileLoop = false;
            while (guesses[i][0] != '\0' && !exitWhileLoop) {
                // prints the guess number and the beginning bracket of the code
                System.out.print(" " + (i + 1) + ") [");

                // prints all guess symbols except for the last one, each separated by a comma
                for (int j = 0; j < guesses[i].length - 1; j++) {
                    System.out.print(guesses[i][j] + ", ");
                }

                // prints the last guess symbol and the closing bracket of the code
                System.out.print(guesses[i][guesses[i].length - 1] + "] ");

                // prints the black hits
                for (int j = 0; j < hits[i][Config.BLACK_HITS_INDEX]; j++) {
                    System.out.print(Config.BLACK_HITS_SYMBOL);
                }

                // prints the white hits
                for (int j = 0; j < hits[i][Config.WHITE_HITS_INDEX]; j++) {
                    System.out.print(Config.WHITE_HITS_SYMBOL);
                }

                System.out.println("");
                exitWhileLoop = true;
            }
        }
    }

    /**
     * Determine the next code in sequence given the ordered symbols and
     * a code.
     * Most significant positions are the left most, just like for a number 
     * such as 1234, where 1 is the most significant digit.
     * 
     * Algorithm:
     * Loop from least significant position to the most significant
     *     Find the index of the symbol
     *     if least significant position
     *         if last symbol then 
     *             set to first symbol and carry
     *         else set next symbol
     *     else 
     *         if carry and last symbol
     *             set to first symbol and keep carry set
     *         else if carry and not last symbol
     *             set next symbol, clear carry
     *         else 
     *             no carry, so keep symbol
     *         end if
     *     end if
     * End loop
     * If carry then prepend an additional symbol. Since, in decimal, leading 0's 
     * are assumed then we assume the same for any symbols. So, we would prepend
     * the 2nd symbol, in decimal a 1.   
     *     
     * @param code   A code with the symbols.
     * @param symbols  The symbols to use for the code.
     * @return  The next code in the sequence based on the order of the symbols.
     */
    public static char[] nextCode(char[] code, char[] symbols) {
        int symbolIndex;
        boolean carry = false;
        char[] nextCode = new char[code.length];

        // initially sets the next code equal to the old code to be later modified
        for (int i = 0; i < code.length; i++) {
            nextCode[i] = code[i];
        }

        // generates the next code and determines if carrying a symbol is necessary
        for (int i = nextCode.length - 1; i >= 0; i--) {
            symbolIndex = indexOf(symbols, nextCode[i]);

            if (i == nextCode.length - 1) { // only true for least significant position
                if (symbolIndex == symbols.length - 1) {
                    nextCode[i] = symbols[0];
                    carry = true;
                } else {
                    nextCode[i] = symbols[symbolIndex + 1];
                }
            } else { // for all other positions
                if (carry && symbolIndex == symbols.length - 1) {
                    nextCode[i] = symbols[0];
                    carry = true;
                } else if (carry && symbolIndex != symbols.length - 1) {
                    nextCode[i] = symbols[symbolIndex + 1];
                    carry = false;
                } else {
                    nextCode[i] = nextCode[i];
                }
            }
        }

        // if carry is still true, then the second symbol needs to appear before the rest of
        // the next code
        if (carry) {
            char[] prependedCode = new char[nextCode.length + 1];
            for (int i = 0; i < prependedCode.length; i++) {
                if (i == 0) {
                    prependedCode[i] = symbols[1];
                } else {
                    prependedCode[i] = nextCode[i - 1];
                }
            }
            return prependedCode;
        }

        return nextCode; // only returns if carry is false after next code is generated
    }

    /**
     * List all the possibilities (permutations) of codes for the specified number of 
     * positions and the provided codes.
     * 
     * Algorithm:
     * Create an array the length being the number of possibilities (permutations). 
     *     For example, 3 symbols in each of 4 positions means there are 3*3*3*3 or 3^4 
     *     which equals 81 permutations.
     * Determine the "first" code (all positions having the same first symbol).
     * For each permutation call nextCode to generate the next code from the current.
     * 
     * If numPositions is less than or equal to 0 or symbols is 0 length or null
     * then return null.
     * 
     * @param numPositions The number of positions in a code.
     * @param symbols The possible symbols used in a code.
     * @return An array of all the possible codes that can be generated from the 
     * symbols for the numPositions.
     */
    public static char[][] enumeratePossibilities(int numPositions, char[] symbols) {
        // checks if numPositions and the length of symbols are valid
        if (numPositions <= 0 || symbols.length == 0) {
            return null;
        }

        int numPossibilities;
        numPossibilities = (int) Math.pow(symbols.length, numPositions);
        char[][] possibleCodes = new char[numPossibilities][numPositions];

        // creates the first code
        for (int i = 0; i < numPositions; i++) {
            possibleCodes[0][i] = symbols[0];
        }

        // generates each subsequent code
        for (int i = 1; i < numPossibilities; i++) {
            possibleCodes[i] = nextCode(possibleCodes[i - 1], symbols);
        }

        return possibleCodes;
    }

    /**
     * Updates the remaining possibilities array and returns the number
     * of possibilities.
     * The hiddenCodeHits parameter contains the black and white hits when the guess is compared 
     * to the code. The possibilities parameter contains all the possible remaining candidates
     * for the hidden code. Determine the hits for each non-null guess when compared to each 
     * possibility and only keep the possibilities that match the result parameter hits.
     * Remove a possibility from the array of possibilities by setting it to null.
     * 
     * @param guess  The current guess
     * @param hiddenCodeHits  The hits when guess is compared to hiddenCode.
     * @param possibilities The remaining codes that contain the hidden code.
     * @param symbols The potential symbols in the codes.
     * @return The number of remaining possibilities.
     */
    public static int updatePossibilities(char[] guess, int[] hiddenCodeHits,
        char[][] possibilities, char[] symbols) {
        int numPossibilities = 0;

        // updates the possible codes based on what the user guesses
        for (int i = 0; i < possibilities.length; i++) {
            if (!(possibilities[i] == null)) {
                if (Arrays.equals(determineHits(possibilities[i], guess, symbols),
                    hiddenCodeHits)) {
                    numPossibilities++;
                } else {
                    possibilities[i] = null;
                }
            }
        }

        return numPossibilities;
    }

    /**
     * Select the first remaining code (lowest index) from possibilities.
     * If no codes are left then return null.
     * 
     * @param possibilities The array of remaining possible codes.
     * @return A code from the array.
     */
    public static char[] computerGuess(char[][] possibilities) {
        // finds the first possible code that is not null and returns it
        for (int i = 0; i < possibilities.length; i++) {
            if (!(possibilities[i] == null)) {
                return possibilities[i];
            }
        }

        return null; // null is returned only if there are no codes left
    }
    
    /**
     * The MasterMind main method that contains the welcome and the main game
     * loop. 
     * 
     * Algorithm:
     * Use appropriate constants from Config.
     * Determine seed or not (call promptInt with Integer.MIN_VALUE, Integer.MAX_VALUE) 
     * Display welcome message. 
     * Generate the hidden code (call generateHiddenCode) 
     * Create 2D arrays for guesses and corresponding hits. Initially every row is null
     *     until guesses are made and hits are determined for a guess. 
     * Enumerate all the possibilities (call enumeratePossibilities)
     * Loop
     *     Prompt for guess (call promptForGuess)
     *     If guess is null then call computerGuess
     *     Determine how many black and white hits (call determineHits)
     *     (call printBoard)
     *     Output number of remaining possibilities
     * End loop when won or lost  
     * Output won or lost message.
     * 
     * @param args  unused
     */
    public static void main(String[] args) {
        // prompt the user for an int for the seed
        int userSeed;
        String seedPrompt = "Enter seed (0 for unrepeatable): ";
        Scanner input = new Scanner(System.in);
        userSeed = promptInt(input, seedPrompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Random rand = new Random(userSeed);

        // welcome messages
        System.out.println("Welcome to Master Mind!");
        System.out.print("I have a " + Config.CODE_POSITIONS + " symbol code using [");
        for (int i = 0; i < Config.CODE_SYMBOLS.length - 1; i++) {
            System.out.print(Config.CODE_SYMBOLS[i] + ", ");
        }
        System.out.println(Config.CODE_SYMBOLS[Config.CODE_SYMBOLS.length - 1] + "].");
        System.out.println("Can you guess my code within " + Config.MAX_GUESSES + " guesses?");

        // generates the hidden code
        char[] hiddenCode = new char[Config.CODE_POSITIONS];
        hiddenCode = generateHiddenCode(rand, Config.CODE_POSITIONS, Config.CODE_SYMBOLS);

        // determines and stores all possible codes
        char[][] possibleCodes = enumeratePossibilities(Config.CODE_POSITIONS, Config.CODE_SYMBOLS);

        // plays the game
        char[][] guesses = new char[Config.MAX_GUESSES][Config.CODE_POSITIONS];
        int[][] hits = new int[Config.MAX_GUESSES][Config.HITS_ARRAY_LENGTH];
        int numGuesses = 0;
        int numHints = 0;
        int numPossibilities;
        boolean wonGame = false;
        String guessPrompt = "Enter guess (? for help): ";
        input.nextLine(); // moves input so that the next thing read will be the guess
        while (wonGame == false && numGuesses < Config.MAX_GUESSES) {
            // prompts user for a guess
            guesses[numGuesses] =
                promptForGuess(input, guessPrompt, Config.CODE_POSITIONS, Config.CODE_SYMBOLS);

            // for if the user inputs that they want a hint (?) (changed in milestone 3)

            // generates a computer guess if the user requests help
            if (guesses[numGuesses] == null) {
                guesses[numGuesses] = computerGuess(possibleCodes);
                numHints++;
            }

            // determines the number of hits for a guess
            hits[numGuesses] = determineHits(hiddenCode, guesses[numGuesses], Config.CODE_SYMBOLS);

            // prints the game board
            printBoard(guesses, hits);

            // determines and prints the number of possible codes left
            numPossibilities = updatePossibilities(guesses[numGuesses], hits[numGuesses],
                possibleCodes, Config.CODE_SYMBOLS);
            System.out.println("remaining possibilities: " + numPossibilities);

            // determines if the user has won the game yet or not
            if (hits[numGuesses][Config.BLACK_HITS_INDEX] == Config.CODE_POSITIONS) {
                wonGame = true;
            } else {
                wonGame = false;
            }

            numGuesses++;
        } // loop exits when the game is won or when the number of guesses reaches the max

        input.close(); // no more input is needed

        // displays different messages for if the user wins or loses the game
        if (wonGame == true) {
            System.out.println("Congratulations! You guessed code with only " + numGuesses
                + " guesses and " + numHints + " hints!"); // winning message
        } else {
            System.out.print("You lost, the code was: [");
            for (int i = 0; i < hiddenCode.length - 1; i++) { // loop to print the hidden code
                System.out.print(hiddenCode[i] + ", ");
            }
            System.out.print(hiddenCode[hiddenCode.length - 1] + "]"); // losing message
        }
    }
}
