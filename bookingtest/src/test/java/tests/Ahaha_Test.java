package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Ahaha_Test {


    @Test
    public void test() {
	System.out.println("ahahaha");
    }
	
    @Test
    public void testass() {
	Assert.fail();
    	System.out.println("ahahaha");
    }
    
    @Test (dependsOnMethods = "testass")
    public void testassq() {

	System.out.println("ahahaha");
    }
}
