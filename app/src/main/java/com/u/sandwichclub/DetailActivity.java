package com.u.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.u.sandwichclub.model.Sandwich;
import com.u.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mMainNameTextView;
    TextView mAlsoKnownAsTextView;
    TextView mDescriptionTextView;
    TextView mIngredientsTextView;
    TextView mPlaceOfOriginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mMainNameTextView = findViewById(R.id.main_name_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);

        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populates the UI with the Json parsed values
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        mAlsoKnownAsTextView.setText(convertListToString(sandwich.getAlsoKnownAs()));
        mIngredientsTextView.setText(convertListToString(sandwich.getIngredients()));
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mMainNameTextView.setText(sandwich.getMainName());
        mDescriptionTextView.setText(sandwich.getDescription());
    }

    /**
     * Converts the list of strings to a comma delimited string
     * @param dataList
     * @return
     */
    private String convertListToString(List<String> dataList){

        if(dataList == null || dataList.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for(String item:dataList){
            sb.append(item+" , ");
        }

        String str = sb.toString();

        return str.substring(0, str.length() - 2);
    }
}
