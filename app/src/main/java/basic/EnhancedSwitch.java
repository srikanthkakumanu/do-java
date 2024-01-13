package basic;

/**
 * Traditional switch statement enhanced since Java 14 with following additions.
 * 1. switch expression:-
 *      A switch expression is essentially that returns a value. One way to return
 *      a value of a switch expression is by using a yield statement.
 * 2. yield statement:-
 *      - Yield terminates the switch immediately just like
 *          break statement but difference is that yield can return a value.
 *      - In switch expression when yield is used, break statement is not allowed.
 *      - Yield is a context-sensitive keyword which means that outside the switch
 *          statement yield is just an identifier which has no meaning.
 * 3. support for list of case constants:-
 *      - From Java 14, traditional Case stacking can be enhanced
 *          by writing all constants with commas in single case. i.e. case constant list.
 * 4. The case with an arrow (case 'X' -> ):-
 *      - Differences -- (case 'X' :) vs. (case 'X' ->)
 *          - arrow case does not fall through to next case.
 *              Thus, no need of break statement.
 *          - The arrow case provides a "shorthand" way to supply a
 *              value when used in a switch expression.
 *          - target of an arrow case can be:
 *              - case constant -> expression;
 *              - case constant -> { block of expression } - when using
 *                  a block, you must use yield to supply a value to a switch
 *                  expression.
 *              - case constant -> throw an exception.
 *      - We can avoid yield or break statements.
 *      - We cannot mix arrow cases with traditional colon (:) cases in the
 *          same switch. We must choose one or the other.
 *
 */
public class EnhancedSwitch {
    public static void main(String[] args) {
        // traditionalSwitch(); // demonstrates traditional switch case stacking
        // enhancedSwitchCaseConstantList(); // enhanced switch case constant list
        // switchExprAndYield();
        // switchWithArrow();
        // switchWithArrowAndBlockExpr();
        vowelExample(); // vowel using all latest features
    }

    /**
     * 4. The case with an arrow (case 'X' -> ):-
     * - Differences -- (case 'X' :) vs. (case 'X' ->)
     *      - arrow case does not fall through to next case. Thus,
     *          no need of break statement and yield statement.
     *      - The arrow case provides a shorthand way to supply a
     *          value when used in a switch expression.
     *      - target of an arrow case can be:
     *          - case constant -> expression;
     *          - case constant -> { block of expression } - when using
     *              a block, you must use yield to supply a value to a switch
     *              expression.
     *          - case constant -> throw an exception.
     * - We cannot mix arrow cases with traditional colon (:) cases
     *      in the same switch. We must choose one or the other.
     */
    private static void switchWithArrow() {
        int eventCode = 6010;
        int priorityLevel = switch (eventCode) {
            case 1000, 1205, 8900 -> 1;
            case 2000, 6010, 9128 -> 2;
            case 1002, 7023, 9300 -> 3;
            default -> 0;
        };
        System.out.printf("Priority Level for event code: %d is %d\n", eventCode, priorityLevel);
    }

    /**
     * Swith case statement with arrow and block expression.
     */
    private static void switchWithArrowAndBlockExpr() {
        int eventCode = 9300;
        boolean stop;
        int priorityLevel = switch (eventCode) {
            case 1000, 1205, 8900 -> {
                stop = false;
                System.out.println("Alert!");
                yield 1;
            }
            case 2000, 6010, 9128 -> {
                stop = false;
                System.out.println("Warning!");
                yield 2;
            }
            case 1002, 7023, 9300 -> {
                stop = true;
                System.out.println("Danger");
                yield 3;
            }
            default -> {
                stop = false;
                System.out.println("Normal.");
                yield 0;
            }
        };
        System.out.printf("Priority Level for event code: %d is %d\n", eventCode, priorityLevel);
        if(stop) System.out.println("Stop Required!!");
    }

    private static void vowelExample() {
        char c = 'Y';
        boolean yIsVowel = true;

        boolean isVowel = switch (c) {
            case 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U' -> true;
            case 'y', 'Y' -> {
                if(yIsVowel)
                    yield true;
                else
                    yield false;
            }
            default -> false;
        };
        if(isVowel) System.out.println(c + " is vowel.");
    }
    /**
     * 1 & 2 Switch Expression and Yield statement
     * ------------------------------------
     * Switch Expression:- Switch expression is essentially that returns a value.
     * One way to return a value of a switch expression is by using a yield statement.
     * yield statement:-
     *      1. Yield terminates the switch immediately just like
     *      break statement but difference is that yield can return a value.
     *      2. In switch expression when yield is used, break statement is not allowed.
     *      3. Yield is a context-sensitive keyword which means that outside the switch
     *      statement yield is just an identifier which has no meaning.
     */
    static private void switchExprAndYield() {
        int eventCode = 6010;

        int priorityLevel = switch (eventCode) {
            case 1000, 1205, 8900:
                yield 1;
            case 2000, 6010, 9128:
                yield 2;
            case 1002, 7023, 9300:
                yield 3;
            default:
                yield 0;
        };
        System.out.printf("Priority Level for event code: %d is %d\n", eventCode, priorityLevel);
    }
    /**
     * 3. Support for list of case constants
     * -------------------------------------
     * From Java 14, traditional Case stacking can be enhanced
     * by writing all constants with commas in single case. i.e. case constant list.
     */
    private static void enhancedSwitchCaseConstantList() {
        int priorityLevel;

        int eventCode = 6010;
        switch (eventCode) {
            case 1000, 1205, 8900:
                priorityLevel = 1;
                break;
            case 2000, 6010, 9128:
                priorityLevel = 2;
                break;
            case 1002, 7023, 9300:
                priorityLevel = 3;
                break;
            default:
                priorityLevel = 0;
        }
        System.out.printf("Priority Level for event code: %d is %d\n", eventCode, priorityLevel);
    }
    /**
     * In traditional switch, when two constants were both handled by same code sequence -
     * then case stacking technique is used like shown below.
     */
    private static void traditionalSwitch() {
        int priorityLevel;

        int eventCode = 6010;
        // traditional switch - case stacking
        // The case stacking enable all three case statements to use the same code sequence to set priorityLevel to 1
        switch (eventCode) {
            // this is called case stacking.
            case 1000:
            case 1205:
            case 8900:
                priorityLevel = 1;
                break;
            case 2000:
            case 6010:
            case 9128:
                priorityLevel = 2;
                break;
            case 1002:
            case 7023:
            case 9300:
                priorityLevel = 3;
                break;
            default:
                priorityLevel = 0;
        }
        System.out.printf("Priority Level for event code: %d is %d\n", eventCode, priorityLevel);
    }
}
