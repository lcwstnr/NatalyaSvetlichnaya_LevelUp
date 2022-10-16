package ru.levelp.at.homework2;

public class LuckyTicketCalculate {

    public static Boolean luckyTicketCalculate(String numbers) {
        if (numbers.length() != 6) {
            throw new RuntimeException("Количество цифр не равно 6");
        }
        String part1 = numbers.substring(0, 3);
        String part2 = numbers.substring(3, 6);
        int sum1 = sumOfString(part1);
        int sum2 = sumOfString(part2);
        return sum1 == sum2;
    }

    private static int sumOfString(String part) {
        String[] numbersOfPart = part.split("");
        int sum = 0;
        for (String num : numbersOfPart) {
            int number = Integer.parseInt(num);
            sum += number;
        }
        return sum;
    }
}
