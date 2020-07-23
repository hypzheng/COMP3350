package comp3350.pbbs.tests.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.DataAccess;

/**
 * StubDatabase
 * Group4
 * PBBS
 * <p>
 * This class defines the persistence layer (stub database).
 */
public class StubDatabase implements DataAccess {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private String dbName;                          //name of the database, not used in iteration 1
    private String dbType = "stub";
    private ArrayList<BudgetCategory> budgets;      //ArrayList for budgets
    private ArrayList<CreditCard> creditCards;      //ArrayList for credit cards
    private ArrayList<Transaction> transactions;    //ArrayList for transactions
    private String username;                        //"Hi, {username}!"

    /**
     * This method is the constructor of the database stub
     *
     * @param name name of the database
     */
    public StubDatabase(String name) {
        this.dbName = name;
    }

    public StubDatabase() {
        this(Main.dbName);
    }

    /**
     * This method is used for populating fake data into the stub database
     */
    public void open(String dbPath) {
        BudgetCategory rent, groceries, utilities, phoneBill;   //various types of BudgetCategories
        CreditCard card1, card2;                                //variables for multiple cards
        Transaction t1, t2, t3, t4;                             //variables for multiple transactions

        budgets = new ArrayList<>();
        rent = new BudgetCategory("Rent/Mortgage", 500);
        budgets.add(rent);
        groceries = new BudgetCategory("Groceries", 100);
        budgets.add(groceries);
        utilities = new BudgetCategory("Utilities", 80);
        budgets.add(utilities);
        phoneBill = new BudgetCategory("Phone Bill", 75);
        budgets.add(phoneBill);

        creditCards = new ArrayList<>();
        card1 = new CreditCard("Visa", "1000100010001000", "Jimmy", 12, 2021, 18);
        creditCards.add(card1);
        card2 = new CreditCard("Mastercard", "1002100310041005", "Jimmy", 11, 2021, 15);
        creditCards.add(card2);

        //local date variable
        Date date = new Date();
        transactions = new ArrayList<>();
        t1 = new Transaction(Services.calcDate(date, -5), 50, "Bought Chickens", card1, groceries);
        transactions.add(t1);
        t2 = new Transaction(Services.calcDate(date, -8), 450, "Rent Paid", card2, rent);
        transactions.add(t2);
        t3 = new Transaction(Services.calcDate(date, 2), 40, "Hydro bill paid", card2, utilities);
        transactions.add(t3);
        t4 = new Transaction(Services.calcDate(date, 3), 75, "Phone Bill paid", card2, phoneBill);
        transactions.add(t4);

        username = null;    //initializing the username with Null, it is going to call the mane from user input
        System.out.println("Opened " + dbType + " database " + dbName);
    }

    public void close() {
        System.out.println("Closed " + dbType + " database " + dbName);
    }

    /**
     * This method will add all the budgets to a budget list
     *
     * @return true if added successfully.
     */
    public boolean addBudgetCategories(List<BudgetCategory> budgetList) {
        return budgetList.addAll(budgets);
    }

    /**
     * This method will find if a budget exist or not
     *
     * @return the BudgetCategory object
     */
    public BudgetCategory findBudgetCategory(BudgetCategory currentBudget) {
        BudgetCategory budgetCategory = null;
        int index = budgets.indexOf(currentBudget);
        if (index >= 0) {
            budgetCategory = budgets.get(index);
        }
        return budgetCategory;
    }

    /**
     * This method will insert a new budget category with the budgets ArrayList.
     */
    public boolean insertBudgetCategory(BudgetCategory newBudget) {
        return budgets.add(newBudget);

    }

    /**
     * Getter method to get the budgets.
     *
     * @return budgets ArrayList.
     */
    public ArrayList<BudgetCategory> getBudgets() {
        return budgets;
    }

    /**
     * This method will be used to update a Budget.
     *
     * @return updated budgetCategory
     */
    public BudgetCategory updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget) {
        int index = budgets.indexOf(currentBudget);
        BudgetCategory result = null;
        if (index >= 0) {
            budgets.set(index, newBudget);
            result = budgets.get(index);
        }
        return result;
    }

    /**
     * This method will remove a budget category.
     *
     * @return newly deleted budgetCategory
     */
    public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudget) {
        int index = budgets.indexOf(currentBudget);
        BudgetCategory result = null;
        if (index >= 0) {
            result = budgets.remove(index);
        }
        return result;
    }

    @Override
    public int getBudgetsSize() {
        return budgets.size();
    }

    /**
     * This method will add all the cards to a card list.
     *
     * @return true if added successfully.
     */
    @SuppressWarnings("unused")  // will be used at some point in the future
    public boolean addAllCreditCards(List<CreditCard> cardList) {
        return cardList.addAll(creditCards);
    }

    /**
     * This method will find if a card exist or not.
     *
     * @return the card object.
     */
    public boolean findCreditCard(CreditCard currCard) {
        return creditCards.indexOf(currCard) >= 0;
    }

    /**
     * This method will insert a new card with the ArrayList.
     */
    public void insertCreditCard(CreditCard newCard) {
        if (creditCards.indexOf(newCard) <= 0) {
            creditCards.add(newCard);
        }
    }

    /**
     * Getter method to get the credit cards.
     *
     * @return creditCards ArrayList.
     */
    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    /**
     * This method will be used to update a credit card.
     *
     * @return true if updated correctly.
     */
    public boolean updateCreditCard(CreditCard currCard, CreditCard newCard) {
        int index = creditCards.indexOf(currCard);
        if (index >= 0) {
            creditCards.set(index, newCard);
            return true;
        }
        return false;
    }

    /**
     * This method will remove a credit card.
     */
    public void deleteCreditCard(CreditCard currCard) {
        if (creditCards.indexOf(currCard) >= 0) {
            creditCards.remove(currCard);
        }
    }

    @Override
    public int getCardsSize() {
        return creditCards.size();
    }

    /**
     * This method will add all the transactions to a transaction list.
     *
     * @return true if added successfully.
     */
    public boolean addTransactions(List<Transaction> transactionsList) {
        return transactionsList.addAll(transactions);
    }

    /**
     * This method will find if a transaction exist or not.
     *
     * @return the transaction object.
     */
    @SuppressWarnings("unused")  // will be used at some point in the future
    public Transaction findTransaction(Transaction currentTransaction) {
        Transaction transaction = null;
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0) {
            transaction = transactions.get(index);
        }
        return transaction;
    }

    /**
     * This method will insert a new transaction with the ArrayList.
     *
     * @return true if inserted the transaction properly.
     */
    public boolean insertTransaction(Transaction newTransaction) {
        return transactions.add(newTransaction);
    }

    /**
     * Getter method to get the transactions.
     *
     * @return transactions ArrayList.
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * This method will be used to update a transaction.
     *
     * @return true if updated successfully.
     */
    public boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction) {
        boolean toReturn = false;
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0) {
            toReturn = transactions.set(index, newTransaction) != null;
        }
        return toReturn;
    }

    /**
     * This method will be used to remove a transaction.
     *
     * @return true if deleted successfully
     */
    public boolean deleteTransaction(Transaction currentTransaction) {
        boolean toReturn = false;
        int index = transactions.indexOf(currentTransaction);
        if (index >= 0) {
            toReturn = transactions.remove(index) != null;
        }
        return toReturn;
    }

    @Override
    public int getTransactionsSize() {
        return transactions.size();
    }

    /**
     * Getter for username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username, used when renaming
     * the username could be anything single line.
     * this is ensured on presentation side
     *
     * @param newUsername String representation of the user's name
     */
    public void setUsername(String newUsername) {
        if (newUsername == null) {
            throw new NullPointerException("Expecting a String!");
        }
        this.username = newUsername;
    }
}