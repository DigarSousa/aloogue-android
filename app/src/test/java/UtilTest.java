import org.junit.Assert;

import org.junit.Test;

import alugueis.alugueis.util.Util;

public class UtilTest {

    @Test
    public void emailValidator() {
        Assert.assertTrue(Util.isValidEmail("a@a.com"));
        Assert.assertTrue(Util.isValidEmail("a@a.com.bla"));
        Assert.assertFalse(Util.isValidEmail("a.com"));
        Assert.assertFalse(Util.isValidEmail("a@a"));

    }
}
