package tech.app.resume_tools;

import java.io.*;
import java.util.*;

/**
 * Hello world!
 */
public final class App {

    private App() {
    }

    private static final String SEP_STR = " /-+=,&%^$#!.?<>()[]{}|@*`~:;";
    private static final Set<Character> SEPARATORS = new HashSet();

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     * 
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        for(int i = 0; i < SEP_STR.length(); i++) {
            SEPARATORS.add(SEP_STR.charAt(i));
        }
        // Special cases added to SEPARATORS to avoid any errors
        SEPARATORS.add('\'');
        SEPARATORS.add('\"');
        SEPARATORS.add('\\');
        SEPARATORS.add('\n');
        SEPARATORS.add('\t');
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     * 
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String str, int idx) {
        StringBuilder sb = new StringBuilder();
        char ch = str.charAt(idx);
        int newPos = idx;

        while(!SEPARATORS.contains(ch)) {
            sb.append(ch);
            newPos++;
            ch = str.charAt(newPos);
        }
            return sb.toString();
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        generateElements(SEP_STR, SEPARATORS);
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Enter a file: ");
        //String fName = sc.nextLine();

        
        String st = "jnasdnasdkjnasdkjnasd  asd 213 $$$%%% lep";
        int index = st.length();

        String newStr = nextWordOrSeparator(st, index);
        while(newStr.length() < index) {
            
        }
        System.out.println(nextWordOrSeparator("jnasdnasdkjnasdkjnasd  asd 213 $$$%%% lep", index));
        System.out.println(index);

        //try {
        //    File inFile = new File(fName);
        //    BufferedReader br = new BufferedReader(new FileReader(inFile));
        //    br.close();

        //} catch (Exception e) {
        //    System.err.println("Whoops");
        //}
    }
}
