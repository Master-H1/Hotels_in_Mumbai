package com.example.hotelsinmumbai;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkedFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkedFragment1 extends Fragment {
    EditText add_food_name_network, add_food_price_network;
    Spinner add_food_category_network;
    CheckBox is_it_vegetarian_network;
    Button save_food_network;
    // Declare request ques
    RequestQueue requestQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NetworkedFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetworkedFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkedFragment1 newInstance(String param1, String param2) {
        NetworkedFragment1 fragment = new NetworkedFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_networked1, container, false);

        add_food_name_network= view.findViewById(R.id.add_food_name_network);
        add_food_category_network = view.findViewById(R.id.add_food_category_network);
        add_food_price_network = view.findViewById(R.id.add_food_price_network);
        is_it_vegetarian_network = view.findViewById(R.id.is_it_vegetarian_network);

        // Let's create a request queue
        requestQueue = Volley.newRequestQueue(getContext());

        // Let's create a Volley that will POST data to Django web app
        save_food_network = view.findViewById(R.id.save_food_network);
        save_food_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

        return view;
    }

    // This function will help us to submit data to Django web app
    private void submitData() {
        // Let's create the strings that will be saved to our Django web app
        String food_name = add_food_name_network.getText().toString();
        String food_category = add_food_category_network.getSelectedItem().toString();
        String food_price = add_food_price_network.getText().toString();
        String is_it_vegetarian = String.valueOf(is_it_vegetarian_network.isChecked());

        // create a JSON to send
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("food_name",food_name);
            jsonObject.put("food_category",food_category);
            jsonObject.put("food_price",food_price);
            jsonObject.put("food_is_vegetarian",is_it_vegetarian);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Define the URL of your Django API
        String url = "http://192.168.122.114:8000/food_api/";

        // Create a JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(), "Data Submitted Successfully", Toast.LENGTH_LONG).show();
                        add_food_name_network.setText("");
                        add_food_price_network.setText("");
                        is_it_vegetarian_network.setChecked(false);
                        add_food_category_network.setSelection(0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(getContext(),"Submit Error",Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
}