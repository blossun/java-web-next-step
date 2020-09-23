package dev.solar;

public class Calculator {

    public int calculate(String str) {
        if (isBlank(str)) return 0;
        return sumOfIntegers(stringsToIntegers(splitString(str)));
    }

    private boolean isBlank(String str) {
        return str == null || str.equals("");
    }

    public String[] splitString(String str) {
        String[] splitStr = str.split("[,:]");
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
