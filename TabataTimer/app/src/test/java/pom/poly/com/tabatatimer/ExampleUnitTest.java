package pom.poly.com.tabatatimer;

import org.junit.Test;

import pom.poly.com.tabatatimer.Utility.Utility;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEmail() throws Exception {
        assertTrue(Utility.isAEmail("roy989898@gmail.com"));
        assertFalse(Utility.isAEmail("23423f423423423@@gmail@.com"));
        assertFalse(Utility.isAEmail("sadasda5646f53"));
    }
    @Test
    public void testPassword() throws Exception {
        /*>6 number or letter*/
        assertFalse(Utility.isaValidPassw("1"));
        assertFalse(Utility.isaValidPassw("21"));
        assertFalse(Utility.isaValidPassw("123"));
        assertFalse(Utility.isaValidPassw("1234"));
        assertFalse(Utility.isaValidPassw("12345"));
        assertTrue(Utility.isaValidPassw("123456"));
        assertFalse(Utility.isaValidPassw("a"));
        assertFalse(Utility.isaValidPassw("ab"));
        assertFalse(Utility.isaValidPassw("ab1"));
        assertFalse(Utility.isaValidPassw("ab3"));
        assertFalse(Utility.isaValidPassw("abc45"));
        assertTrue(Utility.isaValidPassw("royyAhoo6a20"));
        assertFalse(Utility.isaValidPassw("roy989898@"));


    }

    @Test
    public void testVaildForm() throws Exception {
        /*>6 number or letter*/
        assertTrue(Utility.validateForm("roy989898@gmail.com","erere34343"));
        assertFalse(Utility.validateForm("roy989898gmail.com","rotyyerty5"));
        assertFalse(Utility.validateForm("roy989898@gmail.com","120"));


    }

}