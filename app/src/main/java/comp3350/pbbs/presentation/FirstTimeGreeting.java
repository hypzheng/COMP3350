package comp3350.pbbs.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.pbbs.R;
import comp3350.pbbs.business.AccessUser;

/**
 * FirstTimeGreeting
 * Group4
 * PBBS
 * <p>
 * This class implements the greeting when an user enters the app
 */
public class FirstTimeGreeting extends AppCompatActivity {

	String a;

	/**
	 * This method initiates the first time greeting activity and asks users name.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_time_greeting);

		a = (String) getIntent().getSerializableExtra("a");
		Button b = findViewById(R.id.button);
		EditText nameBox = findViewById(R.id.textBox);

		b.setOnClickListener(view ->
		{
			if (nameBox.getText().toString().isEmpty()) {
				nameBox.setError("This field cannot be empty.");
			} else {
				new AccessUser().setUsername(nameBox.getText().toString());
				if (a == null) {
					startActivity(new Intent(FirstTimeGreeting.this, MainActivity.class));
				}
				finish();
			}
		});
	}
}