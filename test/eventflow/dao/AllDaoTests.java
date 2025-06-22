package eventflow.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite to run all DAO tests together.
 * This class groups all DAO test classes for convenient execution.
 */
@RunWith(Suite.class)
@SuiteClasses({
    UserDAOTest.class,
    EventDaoTest.class,
    TicketDaoTest.class
})
public class AllDaoTests {
    // This class remains empty, it is used only as a holder for the above annotations
}
