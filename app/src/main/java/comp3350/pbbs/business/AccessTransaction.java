package comp3350.pbbs.business;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.objects.BudgetCategory;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.persistence.StubDatabase;

/**
 * TransactionAccess
 * Joshua Smallwood
 * PBBS
 *
 * This class defines the access layer between the presentation layer and the persistance layer,
 * focusing on sending new transactions to the database
 * TODO and retrieving transactions
 */
public class AccessTransaction {
    private StubDatabase db;    // Access to the database

    // Formats for the dates
    public static final String[] DATE_FORMATS = new String[]{
            "d/M/yyyy H:m",
            "d-M-yyyy H:m",
    };

    /**
     * This constructor gets access to the database.
     */
    public AccessTransaction() {
        db = Services.getDataAccess(Main.dbName);
    }

    public AccessTransaction(boolean test) {
        db = Services.getDataAccess("test");
    }

    /**
     * This method parses the given date string and time string into a single date time object.
     * @param dateStr   The given date to convert
     * @param timeStr   The given time to convert
     * @return          java.text.Date object that contains the date time, or null if the strings
     *                  do nbot match any of the predefined formats
     */
    private Date parseDatetime(String dateStr, String timeStr) {
        Date toReturn = null;

        // Check the possible date formats
        for(String format : DATE_FORMATS)
        {
            DateFormat df = new SimpleDateFormat(format);
            // Needed or else 30/13/2020 will become 30/1/2021
            df.setLenient(false);
            try {
                // Parse the date
                toReturn = df.parse(dateStr + " " + timeStr);
            } catch (ParseException pe) { }
        }
        return toReturn;
    }

    /**
     * This method parses the given amount string to a float number, rounded to 2 decimals
     *
     * @param amountStr     The string to convert
     * @return      The converted float, or null if the amount is invalid
     */
    private Float parseAmount(String amountStr) {
        Float toReturn = null;

        if(amountStr != null) {
            // If the string is decimal-like
            if(amountStr.contains(".")) {
                // Check if the string is a decimal number with 2 decimal places
                if(amountStr.matches("\\d*\\.\\d\\d$")) {
                    // Parse the string
                    toReturn = Float.parseFloat(amountStr);
                    if(toReturn < 0)
                        toReturn = null;
                }
            }
            // Check if the amount is a positive integer
            else if(amountStr.matches("[0-9]+")){
                toReturn = (float)Integer.parseInt(amountStr);
            }
        }
        return toReturn;
    }

    /**
     * This method checks if the given date string and time string are valid.
     *
     * A valid date follows the format dd/mm/yyyy or dd-mm-yyyy, while a valid time follows the
     * 24-hour format: 0:00 to 23:59
     *
     * @param dateStr   The date to parse.
     * @param timeStr   The time to check.
     * @return          True if the amount is valid, or false if it is invalid
     */
    public boolean isValidDateTime(String dateStr, String timeStr) {
        return parseDatetime(dateStr, timeStr) != null;
    }

    /**
     * This method tells if the given amount string is a valid numeric amount
     *
     * A valid amount is either a positive integer (20) or a positive decimal number with 2 decimal
     * places (20.03)
     *
     * @param amountStr     The amount to check.
     * @return              True if the amount is valid, or false if it is invalid
     */
    public boolean isValidAmount(String amountStr) {
        return parseAmount(amountStr) != null;
    }

    /**
     * This method checks if the description is valid or not
     *
     * A valid description is non null
     *
     * @param desc      The description to check
     * @return          True if the description is valid, or false if it is invalid
     */
    public boolean isValidDescription(String desc) {
        return desc != null;
    }

    /**
     * This method parses the transaction and adds it to the database
     *
     * @param desc              The description of the transaction
     * @param dateStr           The date of the transaction
     * @param timeStr           The time of the transaction
     * @param amountStr         The amount of the transaction
     * @param card              The card the transaction was paid with
     * @param budgetCategory    The category of the transaction
     * @return                  True if the transaction was added successfully, or false if it was
     *                          not added successfully
     */
    public boolean addTransaction(String desc, String dateStr, String timeStr, String amountStr, CreditCard card, BudgetCategory budgetCategory) {
        boolean toReturn = false;
        // Ensure the parameters are valid
        if(isValidAmount(amountStr) && isValidDateTime(dateStr, timeStr) && isValidDescription(desc)) {
            // Parse the date
            Date transactionTime = parseDatetime(dateStr, timeStr);
            // Parse the amount
            float amount = parseAmount(amountStr);
            // Create the transaction
            try {
                Transaction transaction = new Transaction(transactionTime, amount, desc, card, budgetCategory);
                toReturn = db.insertTransaction(transaction);
            }
            catch (IllegalArgumentException ignored) { }
        }

        return toReturn;
    }

    /**
     * Retrieves a list of all the transactions in the database.
     *
     * @return List of all transactions in the database
     */
    public List<Transaction> retrieveTransactions() {
        return db.getTransactions();
    }

    /**
     * Retrieves a list of transactions that fall between the 2 given dates
     *
     * @param to    Starting date range
     * @param from  Ending date range
     * @return      List of transactions that come after @param to and come before @param from.
     */
    public List<Transaction> retrieveTransactions(Date to, Date from) {
        List<Transaction> toReturn = null;

        // If the parameters are valid
        if(to != null && from != null) {
            toReturn = new ArrayList<Transaction>();
            List<Transaction> allTransactions = retrieveTransactions();

            // Loop through all transactions
            for(Transaction transaction : allTransactions) {
                // If the time of the transaction is between the range [to, from], add to the list
                if(transaction.getTime().after(to) && transaction.getTime().before(from)) {
                    toReturn.add(transaction);
                }
            }
        }

        return toReturn;
    }
}
