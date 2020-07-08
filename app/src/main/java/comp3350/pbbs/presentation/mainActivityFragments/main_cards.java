package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessTransaction;
import comp3350.pbbs.presentation.addObject.addCard;
import comp3350.pbbs.business.AccessCreditCard;
import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.presentation.addObject.addTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link main_cards#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main_cards extends Fragment
{
    private AccessCreditCard accessCreditCard;
    private ArrayList<CreditCard> creditCardsList;
    private ArrayAdapter<CreditCard> listViewAdapter;
    private ListView listView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public main_cards()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment main_cards.
     */
    // TODO: Rename and change types and number of parameters
    public static main_cards newInstance(String param1, String param2)
    {
        main_cards fragment = new main_cards();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessCreditCard = new AccessCreditCard();
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_cards, container, false);
        FloatingActionButton fab = view.findViewById(R.id.addCardFAB);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivityForResult(new Intent(view.getContext(), addCard.class),1);
            }
        });

        accessCreditCard = new AccessCreditCard();
        listView =(ListView) view.findViewById(R.id.listCards);
        creditCardsList = accessCreditCard.getCreditCards();
        listViewAdapter = new ArrayAdapter<CreditCard>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                creditCardsList
        );
        listView.setAdapter(listViewAdapter);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main_transactions, container, false);
        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        creditCardsList = accessCreditCard.getCreditCards();
        listViewAdapter = new ArrayAdapter<CreditCard>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                creditCardsList
        );

        listView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();
    }
}