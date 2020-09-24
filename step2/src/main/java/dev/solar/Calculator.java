package dev.solar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public int calculate(String str) {
        if (isBlank(str)) return 0;
        int[] integers = stringsToIntegers(splitString(str));
        isExistedNegative(integers);
        return sumOfIntegers(integers);
    }

    private void isExistedNegative(int[] integers) {
        for (int integer : integers) {
            if (integer < 0) {
                throw new NotAllowedValueException("음수값은 입력하실 수 없습니다.");
            }
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.equals("");
    }

    // splitString에서 구분자 추출과 split을 둘 다 하도록 수정함
    public String[] splitString(String str) {
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(str);
        if (m.find()) {
            String delimeter = m.group(1);
            return m.group(2).split(delimeter);
        }

        return str.split("[,:]");
    }

    public int[] stringsToIntegers(String[] strings) {
        int[] numbers = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            numbers[i] = Integer.parseInt(strings[i]);
        }
        return numbers;
    }

    public int sumOfIntegers(int[] integers) {
        int sum = 0;

        for (int integer : integers) {
            sum += integer;
        }
        return sum;
    }
}
