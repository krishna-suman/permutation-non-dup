import java.beans.PropertyEditorManager;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

import static javax.swing.UIManager.getInt;

public class FindValidCombi {
    private static String inputStr = "", operators = "", target = "";
    private static int targetValue;

    public static void main(String args[]) {
        TimeTaken tt = new TimeTaken();
        LocalTime lt1 = tt.getStartTime();
        System.out.println("enter inputString, target and operators");
        inputStr = "123";
        operators = "*+";
        target = "9";
        PermutationGenerator pg = new PermutationGenerator();
        Set<String> uniqueCombi = pg.generatePermutations(inputStr + operators);
        getValidCombi(uniqueCombi);
        Set<String> resultStr = findTargetCombi(uniqueCombi);
        echo("matching patterns for target:" + targetValue + " are");
        resultStr.stream().forEach(s -> {
            System.out.println(s);
        });
        LocalTime lt2 = tt.getEndTime();
        tt.getTimeElapsedInNano(lt1, lt2);
    }

    private static Set<String> findTargetCombi(Set<String> uniqueCombi) {
        Set<String> finalStrings = new HashSet<>();
        targetValue = Integer.parseInt(target);
        for (String str : uniqueCombi) {
            int combiValue = findCombiValue(str);
            if (targetValue == combiValue) {
                finalStrings.add(str);
            }
        }
        if (finalStrings.size() == 0) {
            echo("no matching strings.");
        }
        return finalStrings;
    }

    private static int findCombiValue(String str) {
        echo("inside findiCombiValue:" + str);
        char[] chars = str.toCharArray();
        int sum = 0;
        int num1 = 0, num2 = 0;
        String operator = "";
        for (int i = 0; i < chars.length; i++) {
            //echo(i + "th char is:" + chars[i]);
            String ch = findOperator(chars[i]);
            if (ch.equals("")) {
                if (num1 == 0) {
                    num1 = getIntFromChar(chars[i]);
                    //echo("num1:" + num1) ;
                } else if (num2 == 0) {
                    num2 = getIntFromChar(chars[i]);
                    //echo("num2:" + num2);
                }
            } else {
                operator = findOperator(chars[i]);
                //echo("operator:" + operator);
            }
            if (i > 0 && i % 2 == 0) {
                if (i == 2 && (num1 == 0 || num2 == 0 || operator.equals(""))) {
                    //echo("improper string: num1:" + num1 + ", num2:" + num2 + ", operator:" + operator + ", sum:" + sum);
                    break;
                } else if (i > 2 && (num1 == 0 || operator.equals(""))) {
                    //echo("improper string: num1:" + num1 + ", num2:" + num2 + ", operator:" + operator + ", sum:" + sum);
                    break;
                } else {
                    if (i == 2) {
                        sum = findResult(num1, num2, operator);
                        num1 = 0;
                        num2 = 0;
                        operator = "";
                    } else {
                        sum = findResult(sum, num1, operator);
                        num1 = 0;
                        num2 = 0;
                        operator = "";
                    }
                }
            }
        }
        echo("combi value is:" + sum);
        return sum;
    }

    private static int getIntFromChar(Character aChar) {
        int result = 0;
        if (null != aChar) {
            String newChar = String.valueOf(aChar);
            if (!newChar.isEmpty() || !newChar.isBlank()) {
                result = Integer.parseInt(newChar);
            }
        }
        return result;
    }

    private static int findResult(int num1, int num2, String operator) {
        int result = 0;
        //echo("findResult num1:" + num1 + ", num2:" + num2 + ", operator:" + operator + ", result:" + result);
        switch (operator) {
            case "minus":
                result = num1 - num2;
                break;
            case "plus":
                result = num1 + num2;
                break;
            case "multi":
                result = num1 * num2;
                break;
        }
        //echo("findResult num1:" + num1 + ", num2:" + num2 + ", operator:" + operator + ", result:" + result);
        return result;
    }

    private static void getValidCombi(Set<String> uniqueCombi) {
        List<String> combis = new ArrayList<>();
        //echo("before:" + uniqueCombi.toString());
        combis.addAll(uniqueCombi);
        uniqueCombi.clear();
        for (String com : combis) {
            boolean isAdd = false;
            if (com.length() % 2 == 1) {
                isAdd = true;
            }
            if (isStringHasConsequtiveNumbersOrSymbols(com)) {
                isAdd = false;
            }
            if (isStringNotStartOrEndwithOper(com, operators) && isAdd) {
                uniqueCombi.add(com);
            }
        }
        //echo("after:" + uniqueCombi.toString());
    }

    private static boolean isStringHasConsequtiveNumbersOrSymbols(String com) {
        boolean result = false;
        boolean result1 = com.matches(".*[1-9]{2,}.*");
        //Pattern.compile("(\\d{2})+").matcher(com).matches();
        echo("result1:" + result1);
        boolean result2 = com.matches(".*[^1-9]{2,}.*");
        echo("result2:" + result2);
        result = (result1 || result2);
        echo("result:" + result);
        return result;
    }

    private static boolean isStringNotStartOrEndwithOper(String com, String operators) {
        boolean result = true;
        char start = com.charAt(0);
        char end = com.charAt(com.length() - 1);
        String startStr = String.valueOf(start);
        String endStr = String.valueOf(end);
        if (operators.contains(startStr) || operators.contains(endStr)) {
            result = false;
        }
        return result;
    }

    public static String findOperator(char c) {
        //echo("inside findOperator:" + c);
        if (c == '*') {
            return "multi";
        } else if (c == '-') {
            return "minus";
        } else if (c == '+') {
            return "plus";
        } else {
            return "";
        }
    }

    public static void echo(String text) {
        System.out.println(text);
    }
}
