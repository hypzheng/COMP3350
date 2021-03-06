package comp3350.pbbs.tests.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * ObjectTests
 * Group4
 * PBBS
 * <p>
 * This class will call all the tests for object layer
 */
public class ObjectTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Object tests");
        suite.addTestSuite(TestBankAccount.class);
        suite.addTestSuite(TestBudgetCategory.class);
        suite.addTestSuite(TestCard.class);
        suite.addTestSuite(TestTransaction.class);
        return suite;
    }

}
