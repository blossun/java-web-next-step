package dev.solar;

public class Calculator {

    public int calculate(String str) {
        if (isBlank(str)) return 0;
        String customDelimiter = extractDelimiter(str);
        if (customDelimiter != null) {
            str = str.substring(str.indexOf("\n") + 1);
            System.out.println("str :" + str);
        }
        Integer[] integers = stringsToIntegers(splitString(str, customDelimiter));
        isExistedNegative(integers);
        return sumOfIntegers(integers);
    }

    private void isExistedNegative(Integer[] integers) {
        for (Integer integer : integers) {
            if (integer < 0) {
                throw new NotAllowedValueException("음수값은 입력하실 수 없습니다.");
            }
        }
    }

    // Todo : 구분자 추출 테스트 케이스 실패 해결
    public String extractDelimiter(String str) {
        try {
            return str.substring(str.indexOf("//") + 2, str.indexOf("\n"));
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.equals("");
    }

    public String[] splitString(String str, String delimiter) {
        if (delimiter == null) {
            delimiter = "[,:]";
        }
        String[] splitStr = str.split(delimiter);
        return splitStr;
    }

    public Integer[] stringsToIntegers(String[] strings) {
        Integer[] integers = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            integers[i] = Integer.parseInt(strings[i]);
        }
        return integers;
    }

    public int sumOfIntegers(Integer[] integers) {
        int sum = 0;

        for (Integer integer : integers) {
            sum += integer;
        }
        return sum;
    }
}
