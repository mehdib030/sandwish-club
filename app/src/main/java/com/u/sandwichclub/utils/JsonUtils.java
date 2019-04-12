package com.u.sandwichclub.utils;

import com.u.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Parses the json sandwich string
     * @param json
     * @return a Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");
            sandwich.setMainName(name.getString("mainName"));
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(convertJSONArrayToList(alsoKnownAsArray));
            sandwich.setDescription(sandwichJson.getString("description"));
            sandwich.setImage(sandwichJson.getString("image"));
            sandwich.setIngredients(convertJSONArrayToList(sandwichJson.getJSONArray("ingredients")));
            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    /**
     * Converts a json array to a Java list
     * @param jsonArray
     * @return a list
     */
    private static List<String> convertJSONArrayToList(JSONArray jsonArray){

        ArrayList<String> listdata = new ArrayList();

        if (jsonArray != null) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    listdata.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listdata;
    }
//
//    {\"name\":{\"mainName\":\"Ham and cheese
//        sandwich\",\"alsoKnownAs\":[]},\"placeOfOrigin\":\"\",\"description\":\"A ham and cheese
//        sandwich is a common type of sandwich. It is made by putting cheese and sliced ham
//        between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables
//        like lettuce, tomato, onion or pickle slices can also be included. Various kinds of
//        mustard and mayonnaise are also
//        common.\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG\",\"ingredients\":[\"Sliced
//        bread\",\"Cheese\",\"Ham\"]}
}
