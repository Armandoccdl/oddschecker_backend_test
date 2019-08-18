package com.oddschecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static final String SEPARATOR = ";";

    /**
     * For each input line, search the collection of
     * fragments to locate the pair with the maximal overlap match then
     * merge those two fragments. This operation will decrease the total
     * number of fragments by one. Repeat until there is only one fragment
     * remaining in the collection. This is the defragmented line / reassembled document.
     * <p>
     * If there is more than one pair of maximally overlapping fragments in any
     * iteration then just merge one of them. So long as you merge one maximally
     * overlapping pair per iteration the test inputs are guaranteed to result in good and deterministic output.
     * When comparing for overlaps, compare case sensitively.
     *
     * @param line
     * @return
     */
    public static String reassemble(final String line) {
        String cleanText;
        List<String> elements = new ArrayList<>(Arrays.asList(line.split(SEPARATOR)));

        if (!elements.isEmpty()) {
            // get and remove first element to start.
            final String[] mainText = {elements.stream().findFirst().get()};
            elements.remove(mainText[0]);

            // cycle through rest of the strings and check for overlap.
            final int[] bestOverlap = {0};
            final String[] overlappingText = {""};

            int originalSize = elements.size();
            int i = 0;

            while (!elements.isEmpty() && i < originalSize) {

                elements.forEach(fragment -> {
                    int howMuchOverlap;

                    // We calculate the overlap number comparing the fragment and the main text
                    howMuchOverlap = calculateOverlapNumber(fragment, mainText[0]);
                    if (howMuchOverlap > bestOverlap[0]) {
                        bestOverlap[0] = howMuchOverlap;
                        overlappingText[0] = fragment;
                    }
                    // We check if the main text contains the fragment
                    if (mainText[0].contains(fragment)) {
                        bestOverlap[0] = fragment.length();
                        overlappingText[0] = fragment;
                    }
                    // If the fragment contains the main text that means the fragment should be the main text
                    else if (fragment.contains(mainText[0])) {
                        mainText[0] = fragment;
                        bestOverlap[0] = mainText[0].length();
                        overlappingText[0] = fragment;
                    }
                });
                // The main text is updated and removed form the list
                mainText[0] = combineTexts(mainText[0], overlappingText[0], bestOverlap[0]);
                elements.remove(overlappingText[0]);

                // We update the properties to get ready for another iteration
                bestOverlap[0] = 0;
                overlappingText[0] = "";
                i++;
            }
            cleanText = mainText[0];
        } else {
            cleanText = "";
        }
        return cleanText;
    }


    /**
     * We compare the two strings and get the highest overlap number.
     *
     * @param fragment1 string 1
     * @param fragment2 string 2
     * @return highestOverlap, the highest overlap number.
     */
    private static int calculateOverlapNumber(final String fragment1, final String fragment2) {

        int overlap1 = 0;
        int overlap2 = 0;

        for (int i = fragment2.length(); i > 0; i--) {
            if (fragment1.regionMatches(fragment1.length() - i, fragment2, 0, i)) {
                overlap1 = i;
                break;
            }
        }
        for (int i = fragment1.length(); i > 0; i--) {
            if (fragment2.regionMatches(fragment2.length() - i, fragment1, 0, i)) {
                overlap2 = i;
                break;
            }
        }
        return Math.max(overlap1, overlap2);
    }

    /**
     * We check if both texts has overlap, and if they do, we combine them.
     *
     * @param mainText
     * @param overlappingText
     * @param bestOverlap
     * @return The combination of both texts.
     */
    private static String combineTexts(String mainText, String overlappingText, int bestOverlap) {
        String combineText;

        // We check if the end of the main text is the same as the begging of overlapping text
        if (mainText.substring((mainText.length() - bestOverlap))
                .equals(overlappingText.substring(0, bestOverlap))) {
            // end of 1 matches start of 2
            overlappingText = overlappingText.substring(bestOverlap);

            combineText = mainText + overlappingText;
        }
        // We check if the end of overlapping text is the same as the begging of main text
        else if (overlappingText.substring((overlappingText.length() - bestOverlap))
                .equals(mainText.substring(0, bestOverlap))) {
            mainText = mainText.substring(bestOverlap);

            combineText = overlappingText + mainText;
        } else {
            // In the case that there is no overlap we return the main text
            combineText = mainText;
        }
        return combineText;
    }
}
