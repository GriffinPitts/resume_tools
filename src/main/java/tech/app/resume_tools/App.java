package tech.app.resume_tools;

import java.io.*;
import java.util.*;

import java.io.File; 
import java.io.IOException; 
import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.text.PDFTextStripper; 
import org.apache.pdfbox.text.PDFTextStripperByArea;


/**
 * ~~UNDER CONSTRUCTION DOES NOT WORK~~
 * Program designed to take user input of 2 files containing text, comparing
 * common words, their use counts, and possible errors. Additionally, code
 * could be implemented to provide intelligent feedback to the user. App will
 * likely be added to the website for remote access and a prettier GUI.
 * 
 * @Author Griffin Pitts
 */
public final class App {

    private App() {
    }

    private static final String SEP_STR = " /-+=,&%^$#!.??<>()[]{}|@*`~:;";
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
     * @param SEPARATORS
     *            the {@code Set} of separator characters
     * @return the first word or an empty string found in {@code text} starting
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
     *   entries(nextWordOrSeparator) is subset of separators and 
     *   returns empty string.
     * </pre>
     */
    private static String nextWordOrSeparator(String str, int idx) {
        String sb = "";
        if(idx <= str.length() - 1) {
            char ch = str.charAt(idx);
            int chValue = ch;
            if(isInRange(chValue)) {
                sb = Character.toString(ch);
                idx++;
                sb = sb + nextWordOrSeparator(str, idx);
            }
        }
        return sb;
    }
    
    private static List<String> siftFileArray(String path) {
        List<String> words = new ArrayList<>();
        File inFile = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
            String line = br.readLine();
            Boolean lineAtEOF = (line == null);
            int lineLen = 0;
            while(Boolean.FALSE.equals(lineAtEOF)) {
                lineLen = line.length();
                int idx = 0;
                while(idx <= lineLen) {
                    String word = nextWordOrSeparator(line, idx);
                    if(!word.isBlank()) {
                        words.add(word);
                        idx += word.length();
                    } else {
                        idx++;
                    }
                }
                line = br.readLine();
                lineAtEOF = (line == null);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

    private static boolean isInRange(int num) {
        boolean result = false;
        if((num >=48 && num <=57)) {
            result = true;
        } else if(num >= 65 && num <= 90) {
            result = true;
        } else if (num >= 97 && num <= 122) {
            result = true;
        } else if (num == 39) {
            result = true;
        }

        return result;
    }

    // PC: A:\repos\resume_tools\data\griffin_pitts_resume_1.pdf
    // MAC: /Users/gpitts79/Documents/repos/resume_tools/data/griffin_pitts_resume_1.pdf
    private static List<String> siftPDFFileArray(String path) {
        List<String> words = new ArrayList<>();
        try ( PDDocument document = PDDocument.load(new File(path))) {
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                int idx = 0;
                int textLen = text.length();
                while(idx <= textLen) {
                    String word = nextWordOrSeparator(text, idx);
                    if(!word.isBlank()) {
                        words.add(word);
                        idx += word.length();
                    } else {
                        idx++;
                    }
                }   
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

    /**
     * Sorting arraylist using the built-in collections sorting method.
     * This method assumes all objects are comparable, and sorts the 
     * resulting array in natural order. The natural order is __, and
     * the sorting algorithm used is a version of MergeSort called 
     * Timsort, and ensures stable sorting. This is achieved by adding
     * already sorted "runs" of data to separate stacks, then merging
     * these stacks together once all data has been processed. These
     * runs have a minimum size, and runs < min_size are filled using 
     * insertion sort for efficiency. 
     * 
     * In this implementation, the default comparator (<) is used.
     * 
     * References can be found here:
     * https://en.wikipedia.org/wiki/Timsort
     * 
     * Run time best case: O(n)
     * Run time worst case: O(nlogn)
     * Average: O(nlogn)
     * 
     * @param list
     *            An un-ordered collection of Strings to be sorted
     */           
    private static void stdSortList(List<String> list) {
        Collections.sort(list);
    }


    /**
     * Implementing block sort first 'cause Cameron picked it from the list of 
     * sorting algos on wikipedia.
     * 
     * 
     * @param list
     */
    private static void blockSortArray(List<String> list) {
        // doing research brb
    }




    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        generateElements(SEP_STR, SEPARATORS);
        Scanner sc = new Scanner(System.in);
        /**
        System.out.print("Enter a file: ");
        String fName = sc.nextLine();
    
        List<String> list = new ArrayList<>();
        
        if(fName.contains(".pdf")) {
            list = siftPDFFileArray(fName);
        } else {
            list = siftFileArray(fName);
        }
        stdSortList(list);
        System.out.println(list.toString())
        */

        sc.close();
  
    }
}
