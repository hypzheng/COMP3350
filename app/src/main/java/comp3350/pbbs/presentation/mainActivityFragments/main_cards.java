package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessCreditCard;
import comp3350.pbbs.objects.CreditCard;

/**
 * main_cards
 * Group4
 * PBBS
 *
 * This fragment displays all cards.
 */
public class main_cards extends Fragment
{
    private AccessCreditCard accessCreditCard;
    private List<CreditCard> creditCardsList;
    private ArrayAdapter<CreditCard> listViewAdapter;
    private ListView listView;


    // Required empty public constructor
    public main_cards() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessCreditCard = new AccessCreditCard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_cards, container, false);

        // list all the credit cards
        listView = view.findViewById(R.id.listCards);
        accessCreditCard = new AccessCreditCard();    // gain access to credit cards
        creditCardsList = accessCreditCard.getCreditCards();
        listViewAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                creditCardsList
        );
        listView.setAdapter(listViewAdapter);

        return view;
    }

    /**
     * This method updates the list after adding.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        creditCardsList = accessCreditCard.getCreditCards();
        listViewAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                creditCardsList
        );

        listView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();
    }
}