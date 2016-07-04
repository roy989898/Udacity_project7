package pom.poly.com.tabatatimer;

import android.app.Application;
import android.test.ApplicationTestCase;

import pom.poly.com.tabatatimer.Utility.Utility;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

   public void testVailForm(){
       assertTrue(Utility.validateForm("roy989898@gmail.com","erere34343"));
       assertFalse(Utility.validateForm("roy989898gmail.com","rotyyerty5"));
       assertFalse(Utility.validateForm("roy989898@gmail.com","120"));
   }


}