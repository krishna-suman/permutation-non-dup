import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermutationGenerator {

    public Set<String> generatePermutations(String inputStr) {

        echo("inside generatePermutations");
        Set<String> mutations = new HashSet<>();
        int swapPos = 0;
        List<String> mutants = new ArrayList<>();
        mutants.add(inputStr);
        while (swapPos < inputStr.length()) {
            mutants = swap(swapPos, mutants);
            echo("swap results for round:" + swapPos);
            mutants.stream().forEach(s -> {
                System.out.println(s);
            });
            swapPos++;
            if (swapPos == 0) break;
        }
        mutations.addAll(mutants);
        echo("inside generatePermutations: final result");
        mutations.stream().forEach(s -> {
            System.out.println(s);
        });
        return mutations;
    }

    private List<String> swap(int swapPos, List<String> mutants) {
        echo("inside swap");
        List<String> swapString = new ArrayList<>();
        echo("swapString list created:" + swapString.toString());
        for (String origStr : mutants) {
            swapString.add(origStr);
            int len = origStr.length();
            char swapChar = origStr.charAt(swapPos);
            for (int i = swapPos + 1; i < len; i++) {
                String swappedChar = swapChars(swapPos, i, origStr);
                swapString.add(swappedChar);
            }
        }
        return swapString;
    }

    private String swapChars(int swapPos, int swapWith, String origStr) {
        echo("inside swapChars");
        char[] newStr = new char[origStr.length()];
        for (int i = 0; i < origStr.length(); i++) {
            if (i == swapPos) {
                newStr[i] = origStr.charAt(swapWith);
                echo("i:" + i + ", swapPos:" + swapPos + ", swapWith:" + swapWith + ":" + String.copyValueOf(newStr));
            } else if (i == swapWith) {
                newStr[i] = origStr.charAt(swapPos);
                echo("i:" + i + ", swapPos:" + swapPos + ", swapWith:" + swapWith + ":" + String.copyValueOf(newStr));
            } else {
                newStr[i] = origStr.charAt(i);
                echo("i:" + i + ", swapPos:" + swapPos + ", swapWith:" + swapWith + ":" + String.copyValueOf(newStr));
            }
        }
        echo("new mutant: " + String.copyValueOf(newStr));
        return String.copyValueOf(newStr);
    }

    public static void echo(String text) {
        System.out.println(text);
    }
}
