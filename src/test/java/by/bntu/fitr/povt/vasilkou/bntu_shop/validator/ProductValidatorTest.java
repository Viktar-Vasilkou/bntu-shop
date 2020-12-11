package by.bntu.fitr.povt.vasilkou.bntu_shop.validator;

import org.assertj.core.internal.BigDecimals;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.validation.Validator;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductValidatorTest {

//     ---------------------------------------------------------------------------
    @ParameterizedTest
    @ValueSource(strings = {"Джинсы", "Супер байка"})
    public void checkProductNameLength_tryWithCorrectData_true(String string) {
        assertTrue(ProductValidator.checkProductNameLength(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Тов", "SuperMegaHyperStringWithLengt"})
    public void checkProductNameLength_tryWithUnCorrectBorderedData_true(String string) {
        assertTrue(ProductValidator.checkProductNameLength(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"То", "SuperMegaHyperStringWithLength1"})
    public void checkProductNameLength_tryWithUnCorrectBorderedData_false(String string) {
        assertFalse(ProductValidator.checkProductNameLength(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "SuperMegaHyperStringWithLengthMoreThen30"})
    public void checkProductNameLength_tryWithUnCorrectData_false(String string) {
        assertFalse(ProductValidator.checkProductNameLength(string));
    }
//     ---------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {"Джинсы", "Супер байка", "Бейсболка"})
    public void checkAnyString_TryCheckCorrectString_true(String str) {
        assertTrue(ProductValidator.checkAnyString(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", " б "})
    public void checkAnyString_TryCheckCorrectBorderedString_true(String str) {
        assertTrue(ProductValidator.checkAnyString(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"                           "})
    public void checkAnyString_TryCheckUnCorrectString_false(String str) {
        assertFalse(ProductValidator.checkAnyString(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void checkAnyString_TryCheckUnCorrectBorderedString_false(String str) {
        assertFalse(ProductValidator.checkAnyString(str));
    }
//     ---------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(ints = {25, 150})
    public void checkDescriptionLength_tryCheckCorrectDescription_true(int length) {
        assertTrue(ProductValidator.checkDescriptionLength(length));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 250})
    public void checkDescriptionLength_tryCheckCorrectBorderedDescription_true(int length) {
        assertTrue(ProductValidator.checkDescriptionLength(length));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 300})
    public void checkDescriptionLength_tryCheckUnCorrectDescription_false(int length) {
        assertFalse(ProductValidator.checkDescriptionLength(length));
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 251})
    public void checkDescriptionLength_tryCheckUnCorrectBorderedDescription_false(int length) {
        assertFalse(ProductValidator.checkDescriptionLength(length));
    }

    // ---------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(doubles = {10, 250})
    public void checkCost_tryWithCorrectCost_true(double cost) {
        assertTrue(ProductValidator.checkCost(BigDecimal.valueOf(cost)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 1_000_000})
    public void checkCost_tryWithCorrectBorderedCost_true(double cost) {
        assertTrue(ProductValidator.checkCost(BigDecimal.valueOf(cost)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-125, 2_000_000})
    public void checkCost_tryWithUnCorrectCost_false(double cost) {
        assertFalse(ProductValidator.checkCost(BigDecimal.valueOf(cost)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 1_000_001})
    public void checkCost_tryWithUnCorrectBorderedCost_false(double cost) {
        assertFalse(ProductValidator.checkCost(BigDecimal.valueOf(cost)));
    }

    // ---------------------------------------------------------------------------
    @ParameterizedTest
    @ValueSource(strings = {"login", "root"})
    public void checkLogin_tryWithCorrectLogin_true(String str) {
        assertTrue(ProductValidator.checkLogin(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"logo", "superLoginLen15"})
    public void checkLogin_tryWithCorrectBorderedLogin_true(String str) {
        assertTrue(ProductValidator.checkLogin(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "superLoginLenMoreThen15"})
    public void checkLogin_tryWithUnCorrectLogin_false(String str) {
        assertFalse(ProductValidator.checkLogin(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "superLoginLens16"})
    public void checkLogin_tryWithUnCorrectBorderedLogin_false(String str) {
        assertFalse(ProductValidator.checkLogin(str));
    }

    // ---------------------------------------------------------------------------
    @ParameterizedTest
    @ValueSource(strings = {"Password1234", "S1u2ppppppppppp3e4r5"})
    public void checkPassword_tryWithCorrectPassword_true(String str) {
        assertTrue(ProductValidator.checkPassword(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Length=8", "PasswordWithLength20"})
    public void checkPassword_tryWithCorrectBorderedPassword_true(String str) {
        assertTrue(ProductValidator.checkPassword(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"root", "PasswordWithLengthMoreThen20+++++++"})
    public void checkPassword_tryWithUnCorrectPassword_false(String str) {
        assertFalse(ProductValidator.checkPassword(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Length7", "PasswordWithLength20+"})
    public void checkPassword_tryWithUnCorrectBorderedPassword_false(String str) {
        assertFalse(ProductValidator.checkPassword(str));
    }

    // ---------------------------------------------------------------------------
    @ParameterizedTest
    @ValueSource(ints = {40, 1_000})
    public void checkAmount_tryWithCorrectAmount_true(int amount) {
        assertTrue(ProductValidator.checkAmount(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10_000})
    public void checkAmount_tryWithCorrectBorderedAmount_true(int amount) {
        assertTrue(ProductValidator.checkAmount(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 1_000_000})
    public void checkAmount_tryWithUnCorrectAmount_false(int amount) {
        assertFalse(ProductValidator.checkAmount(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 10_001})
    public void checkAmount_tryWithUnCorrectCorrectAmount_false(int amount) {
        assertFalse(ProductValidator.checkAmount(amount));
    }
    // ---------------------------------------------------------------------------
}