package com.drizzle.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.drizzle.textanimbutton.TextAnimButton;

public class MainActivity extends AppCompatActivity {

	private TextAnimButton textAnimButton;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textAnimButton = (TextAnimButton)findViewById(R.id.textanimbtn);
		textAnimButton.setAnimStyle(TextAnimButton.RIGHT);
		textAnimButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				textAnimButton.setCurrentState(TextAnimButton.STATE_DONE);
				textAnimButton.setDefaultColor(Color.YELLOW);
			}
		});
	}
}
