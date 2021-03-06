package comp3350.pbbs.objects;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Card
 * Group4
 * PBBS
 * <p>
 * This class defines a card with information it includes
 * 2 kinds of cards: credit and debit
 */
public class Card implements Serializable {
	private String cardName;    // name of a credit card
	private String cardNum;     // number of a credit card
	private String holderName;  // user full name of a credit card
	private int expireMonth;    // the month a credit card is expired, 2-digits (MM)
	private int expireYear;     // the year a credit card is expired, 4-digits (YYYY)
	private int payDate;        // the day user needs to ready for payment, 2-digits (DD)
	private boolean active;

	/**
	 * constants: constraints to a credit card
	 */
	private static final String REGEX = "^[a-zA-Z \\-.']*$";    // the format of a name

	/**
	 * constructor: includes full info of a credit card
	 *
	 * @param cardName name of a credit card
	 * @param num      number of a credit card
	 * @param usr      user full name of a credit card
	 * @param expM     the month a credit card is expired, 2-digits (MM)
	 * @param expY     the year a credit card is expired, 4-digits (YYYY)
	 * @param pay      the day user needs to ready for payment, 2-digits (DD)
	 */
	public Card(String cardName, String num, String usr, int expM, int expY, int pay) {
		errorMsg(num, usr, expM, expY, pay);
		this.cardName = (cardName == null || cardName.isEmpty()) ? "No Name" : cardName;
		cardNum = num;
		holderName = usr;
		expireMonth = expM;
		expireYear = expY;
		payDate = pay;
		this.active = true;
	}

	/**
	 * Constructor: includes full info of a debit card
	 *
	 * @param cardName    name of a credit card
	 * @param cardNum     number of a credit card
	 * @param holderName  user full name of a credit card
	 * @param expireMonth the month a credit card is expired, 2-digits (MM), can be 0 in case of the card have no expiry date
	 * @param expireYear  the year a credit card is expired, 4-digits (YYYY), can be 0 in case of the card have no expiry date
	 */
	public Card(String cardName, String cardNum, String holderName, int expireMonth, int expireYear) {
		this.cardName = cardName.isEmpty() ? "No Name" : cardName;
		if (cardNum == null || cardNum.isEmpty())
			throw new IllegalArgumentException("A Credit Card requires a valid number.");
		this.cardNum = cardNum;
		if (!isValidName(holderName))
			throw new IllegalArgumentException("A Credit Card requires a valid holder name.");
		this.holderName = holderName;
		if (expireMonth != 0 && expireYear != 0 && !isValidExpiration(expireMonth, expireYear))
			throw new IllegalArgumentException("A Credit Card requires a valid expire date.");
		else {
			this.expireMonth = expireMonth;
			this.expireYear = expireYear;
		}
		this.cardName = cardName.isEmpty() ? "No Name" : cardName;
		this.cardNum = cardNum;
		this.holderName = holderName;
		payDate = 0;
		this.active = true;
	}

	/**
	 * method: show error message when the credit card info is invalid
	 *
	 * @param num  number of a credit card
	 * @param usr  user full name of a credit card
	 * @param expM the month a credit card is expired, 2-digits (MM)
	 * @param expY the year a credit card is expired, 4-digits (YYYY)
	 * @param pay  the day user needs to ready for payment, 2-digits (DD)
	 */
	public static void errorMsg(String num, String usr, int expM, int expY, int pay) {
		if (num == null || num.isEmpty())
			throw new IllegalArgumentException("A Credit Card requires a valid number.");
		if (!isValidName(usr))
			throw new IllegalArgumentException("A Credit Card requires a valid holder name.");
		if (!isValidExpiration(expM, expY))
			throw new IllegalArgumentException("A Credit Card requires a valid expire date.");
		if (!isValidPayDate(pay))
			throw new IllegalArgumentException("A Credit Card requires a valid payment date.");
	}

	/**
	 * method: check if the a credit card holder's full name is valid
	 *
	 * @param str the credit card holder name
	 * @return true if the holder name meet the requirement of the format
	 */
	public static boolean isValidName(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		} else {
			return str.matches(REGEX);
		}
	}

	/**
	 * method: check if the input expire date is valid
	 *
	 * @param m the month
	 * @param y the year
	 * @return true if the expire month & year are
	 * 1) month and year are real-world existed, and
	 * 2) after the current month of current year
	 */
	public static boolean isValidExpiration(int m, int y) {
		boolean result;
		Calendar calender = Calendar.getInstance();
		int currMonth = calender.get(Calendar.MONTH) + 1;
		int currYear = calender.get(Calendar.YEAR);
		if (m < 1 || m > 12 || y < currYear || y > 2999) {
			result = false;
		} else {
			result = y != currYear || m >= currMonth;
		}
		return result;
	}

	/**
	 * method: check if the input pay date is valid
	 *
	 * @param n the day
	 * @return true if the day is real-world existed
	 */
	public static boolean isValidPayDate(int n) {
		return n >= 1 && n <= 31;
	}

	/**
	 * method: compare if two credit cards are same
	 *
	 * @param o another credit card
	 * @return true if both credit cards have the same card number
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
		    return true;
        }
		if (o == null || getClass() != o.getClass()) {
		    return false;
        }
		Card state = (Card) o;

		return this.cardNum.equals(state.cardNum);
	}

	/**
	 * method: display the credit card info when it is requested
	 *
	 * @return a string represents this object and its fields
	 */
	@NotNull
	public String toString() {
		String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

		// the string "next month" needs to be replaced to real month later
		String ret = cardName + (getCardNum().length() > 4 ?
				(" •••• " + getCardNum().substring(getCardNum().length() - 4)) : " " + getCardNum());
		if (getExpireMonth() != 0 && getExpireYear() != 0) {
			ret += "\nValid until " + month[getExpireMonth() - 1] + " " + getExpireYear();
		}
		ret += "\n" + getHolderName();
		if (!active) {
			ret += "\nInactive";
		}
		if (!isDebit()) {
			ret += "\nExpected payment on " + getPayDate() + " next month";
		}
		return ret;
	}

	public String toStringShort() {
		return getCardName() + " •••• " + getCardNum().substring(getCardNum().length() - 4);
	}

	/**
	 * methods: getters for instance fields
	 *
	 * @return values of fields
	 */
	public String getCardNum() {
		return cardNum;
	}

	public String getCardName() {
		return cardName;
	}

	public int getPayDate() {
		return payDate;
	}

	public int getExpireMonth() {
		return expireMonth;
	}

	public int getExpireYear() {
		return expireYear;
	}

	public String getHolderName() {
		return holderName;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean b) {
		this.active = b;
	}

	public boolean isDebit() {
		return this.payDate == 0;
	}
}
