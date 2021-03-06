package comp3350.pbbs.presentation.mainActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessCard;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.presentation.updateObject.UpdateCard;
import comp3350.pbbs.presentation.viewObject.ViewCard;

/**
 * MainCards
 * Group4
 * PBBS
 * <p>
 * This fragment displays all cards.
 */
public class MainCards extends Fragment {
	private AccessCard accessCard;
	private List<Card> cardsList;
	private ArrayAdapter<Card> listViewAdapter;
	private ListView listView;

	// Required empty public constructor
	public MainCards() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		accessCard = new AccessCard();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_cards, container, false);

		// list all the credit cards
		listView = view.findViewById(R.id.listCards);
		accessCard = new AccessCard();    // gain access to credit cards
		cardsList = accessCard.getCards();
		listViewAdapter = new ArrayAdapter<>(
				requireActivity(),
				android.R.layout.simple_list_item_1,
				cardsList
		);
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener((adapterView, view1, i, l) ->
		{
			Intent updateCard = new Intent(view1.getContext(), UpdateCard.class);
			updateCard.putExtra("toUpdate", cardsList.get(i));
			startActivityForResult(updateCard, 0);
		});

		// display Card detail
		listView.setOnItemClickListener((arg0, view1, position, arg3) ->
		{
			Intent viewCard = new Intent(view1.getContext(), ViewCard.class);
			viewCard.putExtra("Card", cardsList.get(position));
			startActivityForResult(viewCard, 0);
		});
		return view;
	}

	/**
	 * This method updates the list after adding.
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		cardsList = accessCard.getCards();
		listViewAdapter = new ArrayAdapter<>(
				requireActivity(),
				android.R.layout.simple_list_item_1,
				cardsList
		);

		listView.setAdapter(listViewAdapter);
		listViewAdapter.notifyDataSetChanged();
	}
}