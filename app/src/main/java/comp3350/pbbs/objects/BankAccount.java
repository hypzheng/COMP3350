package comp3350.pbbs.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * BankAccount
 * Group4
 * PBBS
 * <p>
 * This class defines the object bankAccount.
 */
public class BankAccount implements Serializable {
	private String accountName;
	private String accountNumber;
	private Card linkedCard;    // the card this account links to

	public BankAccount(String accountName, String accountNumber, Card linkedCard) {
		if (accountNumber == null) {
			throw new IllegalArgumentException("account number cannot be null");
		}

		if (accountNumber.trim().isEmpty()) {
			throw new IllegalArgumentException("account number cannot be empty");
		}

		if (linkedCard == null) {
			throw new IllegalArgumentException("A bank account must be linked to a debit card");
		}

		this.accountName = (accountName == null || accountName.isEmpty()) ? "No Name" : accountName;
		this.accountNumber = accountNumber;
		this.linkedCard = linkedCard;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public Card getLinkedCard() {
		return linkedCard;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BankAccount that = (BankAccount) o;
		return accountNumber.equals(that.accountNumber) &&
				linkedCard.equals(that.linkedCard);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, linkedCard);
	}

	@Override
	public String toString() {
		return this.accountName + " " + (accountNumber.length() <= 4 ? this.accountNumber : "•••• " + this.accountNumber.substring(this.accountNumber.length() - 4));
	}
}
