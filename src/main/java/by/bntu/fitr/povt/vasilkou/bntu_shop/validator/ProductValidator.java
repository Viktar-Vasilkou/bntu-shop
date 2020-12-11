package by.bntu.fitr.povt.vasilkou.bntu_shop.validator;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class ProductValidator {

    public static boolean checkProductNameLength(String name) {
        boolean isCorrect = true;
        int length = name.length();

        if (length <= 2 || length > 30) {
            isCorrect = false;
        }

        return isCorrect;
    }

    public static boolean checkAnyString(String name) {
        boolean isCorrect = true;

        if (StringUtils.isEmpty(name) || name.trim().isEmpty()) {
            isCorrect = false;
        }

        return isCorrect;
    }

    public static boolean checkDescriptionLength(int descLength) {
        boolean isCorrect = true;
        int length = descLength;

        if (length < 10 || length > 250) {
            isCorrect = false;
        }

        return isCorrect;
    }

    public static boolean checkCost(BigDecimal decimal) {
        boolean isCorrect = true;

        if (decimal.doubleValue() < 0D || decimal.doubleValue() > 1_000_000) {
            isCorrect = false;
        }

        return isCorrect;
    }

    public static boolean checkLogin(String login) {
        boolean isCorrect = true;
        int length = login.length();

        if (length < 4 || length > 15) {
            isCorrect = false;
        }

        return isCorrect;
    }

    public static boolean checkPassword(String login) {
        boolean isCorrect = true;
        int length = login.length();

        if (length < 8 || length > 20) {
            isCorrect = false;
        }

        return isCorrect;
    }

    public static boolean checkAmount(int amount) {
        boolean isCorrect = true;

        if (amount < 0 || amount > 10_000) {
            isCorrect = false;
        }

        return isCorrect;
    }
}
