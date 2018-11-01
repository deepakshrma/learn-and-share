package com.dbs.ui.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Locale;

public class NumberUtilsTest {

    @Test
    public void test_GetBalanceValueFormat() {

        BigDecimal decimal1 = new BigDecimal("1000");
        BigDecimal decimal2 = new BigDecimal("500.23");
        BigDecimal decimal3 = new BigDecimal("9345335");

        Assert.assertEquals("USD 1,000.00", NumberUtils.formatDecimalWithCurrency(decimal1,"USD"));
        Assert.assertEquals("USD 500.23", NumberUtils.formatDecimalWithCurrency(decimal2,"USD"));
        Assert.assertEquals("TWD 9,345,335.00", NumberUtils.formatDecimalWithCurrency(decimal3,"TWD"));

    }
}
