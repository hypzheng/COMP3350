package comp3350.pbbs.tests.business;

import junit.framework.TestCase;

import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.business.AccessBankAccount;
import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.tests.persistence.StubDatabase;

/**
 * TestTransaction
 * Group4
 * PBBS
 * <p>
 * This class defines a test suite for AccessBankAccount.
 */
public class TestAccessBankAccount extends TestCase {
	private Card card;
	private BankAccount bankAccount;
	private AccessBankAccount accessBankAccount;

	public void setUp() {
		DataAccessController.createDataAccess(new StubDatabase("PopulateTest"));
		card = new Card("TD Access Debit", "5135794680086666", "John", 5, 2022);
		bankAccount = new BankAccount("My TD", "1357964", card);
		accessBankAccount = new AccessBankAccount();
		accessBankAccount.insertBankAccount(bankAccount);    // add "bankAccount" into DB
	}

	public void testFindBankAccount() {
		Card dC = new Card("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
		BankAccount bA = new BankAccount("My RBC", "4682579", dC);
		assertTrue(accessBankAccount.findBankAccount(bankAccount));
		assertFalse(accessBankAccount.findBankAccount(bA));
	}

	public void testInsertBankAccount() {
		Card dC = new Card("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
		BankAccount bA = new BankAccount("My RBC", "4682579", dC);
		assertTrue(accessBankAccount.insertBankAccount(bA));
		assertFalse(accessBankAccount.insertBankAccount(bA));
	}

	public void testUpdateBankAccount() {
		Card dC = new Card("CIBC Advantage Debit", "4506445712345678", "Tommy", 3, 2024);
		BankAccount bA = new BankAccount("My RBC", "4682579", dC);
		assertFalse(accessBankAccount.updateBankAccount(bA, bankAccount));
		assertFalse(accessBankAccount.updateBankAccount(bankAccount, bA));
	}

	public void tearDown() {
		DataAccessController.closeDataAccess();
	}
}
