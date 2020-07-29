package comp3350.pbbs.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.Cards.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * AccessICard
 * Group4
 * PBBS
 *
 * This class defines the access layer where deliver cards info to the database
 */
public class AccessCard
{
    private StubDatabase db;    // create an object of the database

    /**
     * constructor: enabling access to the database
     */
    public AccessCard() {
        db = Services.getDataAccess(Main.dbName);
    }

    /**
     * method: find a card exist or not in the database
     *
     * @param toFind a card needs to be found from the database
     * @return true if this card has been added into the database
     */
    public boolean findCard(Card toFind)
    {
        return db.findCard(toFind);
    }

    /**
     * method: add a card into the database
     *
     * @param newCard a card needs to be added into the database
     * @return true if this card does not exist in the database
     */
    public boolean insertCard(Card newCard) {
        return db.insertCard(newCard);
    }

    /**
     * method: delete a debit card from the database
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param toDelete a debit card needs to be deleted from the database
     * @return true if this debit card does exist in the database
     */
    public boolean deleteCard(Card toDelete) {
        return db.deleteCard(toDelete);
    }

    /**
     * method: update a card existed in the database
     *
     * NOT IMPLEMENTED in presentation for iteration1.
     *
     * @param toUpdate an old card needs to be replaced
     * @param newCard  a new card will replace the other one
     * @return true if the old card does exist in the database
     */
    public boolean updateCard(Card toUpdate, Card newCard) {
        if (toUpdate.getClass().equals(newCard.getClass()))
        {
            return db.updateCard(toUpdate, newCard);
        } else {
            return false;
        }
    }

    /**
     * Getter method to get the debit cards.
     *
     * @return debitCards ArrayList.
     */
    public ArrayList<Card> getDebitCards() {
        return db.getDebitCards();
    }

    /**
     * Getter method to get the credit cards.
     *
     * @return creditCards ArrayList.
     */
    public ArrayList<Card> getCreditCards() {
        return db.getCreditCards();
    }

    public ArrayList<Card> getCards()
    {
        return db.getCards();
    }

    /**
     * Calculates the total amount spent for a given Card from the transactions in that category
     * based on the given month
     *
     * @param currentCard is the specified BudgetCategory
     * @param monthAndYear is the month and year to query
     * @return the total amount from transactions in that budget category
     */
    public float calculateCardTotal(Card currentCard, Calendar monthAndYear){
        float sum = 0;
        List<Transaction> transactions = db.getTransactions();

        for(int i = 0; i < transactions.size() && monthAndYear != null; i++){
            Transaction currentTransaction = transactions.get(i);
            Card transactionCard = currentTransaction.getCard();
            Calendar currTime = Calendar.getInstance();
            currTime.setTime(currentTransaction.getTime());
            // Check if the budget categories are the same and if the year and month are the same
            if(transactionCard.equals(currentCard) &&
                    currTime.get(Calendar.MONTH) == monthAndYear.get(Calendar.MONTH) &&
                    currTime.get(Calendar.YEAR) == monthAndYear.get(Calendar.YEAR)){
                sum += currentTransaction.getAmount();
            }
        }

        return sum;
    }

    /**
     * Retrieves a list of months that have transactions for a certain card.
     *
     * @param card      The credit card to query.
     * @return              A list of Calendar instances with the year and month specified.
     */
    public List<Calendar> getActiveMonths(Card card) {
        List<Calendar> activeMonths = new ArrayList<Calendar>();

        // Loop through all transactions
        for(Transaction transaction : db.getTransactions()) {
            if(card.equals(transaction.getCard())) {
                // Construct the calendar object
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(transaction.getTime());
                // Remove time after month
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                // Add to set if not appeared
                if(!activeMonths.contains(calendar)) {
                    activeMonths.add(calendar);
                }
            }
        }
        return activeMonths;
    }
}