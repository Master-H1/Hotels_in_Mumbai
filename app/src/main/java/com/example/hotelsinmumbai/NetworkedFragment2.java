package com.example.hotelsinmumbai;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkedFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkedFragment2 extends Fragment {
    private ArrayList<Food> foods;
    private RecyclerView recyclerView;
    LinearLayout emptyRecyclerViewNetworked;
    private NetworkedRecyclerViewAdapter adapter;
    RequestQueue requestQueue;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NetworkedFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetworkedFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkedFragment2 newInstance(String param1, String param2) {
        NetworkedFragment2 fragment = new NetworkedFragment2();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_networked2, container, false);

        // Initialize user list and adapter
        foods = new ArrayList<>();

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(getContext());

        // initialize empty LinearLayout
        emptyRecyclerViewNetworked = view.findViewById(R.id.emptyRecyclerViewNetworked);

        // Fetch data from Django API
        fetchDataFromApi();

        // Initialize RecyclerView
        adapter = new NetworkedRecyclerViewAdapter(foods);
        recyclerView = view.findViewById(R.id.recyclerViewNetwork);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void fetchDataFromApi() {
        // Define the URL of your Django API
        String url = "http://192.168.122.114:8000/food_api/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // parse the JSON response
                        if (response.length() != 0){
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject foodObject = response.getJSONObject(i);
                                    int id = foodObject.getInt("id");
                                    String name = foodObject.getString("food_name");
                                    String category = foodObject.getString("food_category");
                                    int price = foodObject.getInt("food_price");
                                    String is_vegetarian = String.valueOf(foodObject.getBoolean("food_is_vegetarian"));

                                    // Create User object and add to the list
                                    foods.add(new Food(id, name, category, price, is_vegetarian));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            emptyRecyclerViewNetworked.setVisibility(View.VISIBLE);
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error",error.toString());
                        Toast.makeText(getContext(),"Error fetching data",Toast.LENGTH_SHORT).show();
                    }
                });
              requestQueue.add(jsonArrayRequest);
    }
}