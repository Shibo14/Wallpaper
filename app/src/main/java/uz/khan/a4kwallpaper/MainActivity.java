package uz.khan.a4kwallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryClickInterface {

    private BroadcastReceiver broadcastReceiver;

    EditText searchEdt;
    ImageView searchImg;
    RecyclerView categoryRV, wallpaperRV;
    ArrayList<String> wallpaperArraylist;
    ArrayList<CategoryModel> categoryAdapterArrayList;
    CategoryAdapter categoryAdapter;
    WallpaperAdapter wallpaperAdapter;
//563492ad6f917000010000016841567e571c4414a49b31fd441ee456


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdt = findViewById(R.id.idEdtSearch);
        searchImg = findViewById(R.id.idIVS);
        categoryRV = findViewById(R.id.idRLCategory);
        wallpaperRV = findViewById(R.id.idRLWallpaper);
        wallpaperArraylist = new ArrayList<>();
        categoryAdapterArrayList = new ArrayList<>();

        getBroadcastReceiver();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(categoryAdapterArrayList, this, this::onCategoryClick);
        categoryRV.setAdapter(categoryAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaperAdapter = new WallpaperAdapter(wallpaperArraylist, this);
        wallpaperRV.setAdapter(wallpaperAdapter);


        getCategories();
        getWallpapers();

        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchStr = searchEdt.getText().toString();

                if (searchStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your search query", Toast.LENGTH_SHORT).show();
                } else {
                    getWallpapersByCategory(searchStr);
                }
            }
        });

    }

    public BroadcastReceiver getBroadcastReceiver() {
        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        return broadcastReceiver;
    }


    private void getWallpapersByCategory(String category) {
        wallpaperArraylist.clear();
        String url = "https://api.pexels.com/v1/search?query=" + category + "&per_page=60&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray photoArray = null;

                try {
                    photoArray = response.getJSONArray("photos");
                    for (int i = 0; i < photoArray.length(); i++) {
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArraylist.add(imgUrl);
                    }
                    wallpaperAdapter.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to load wallpapers...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", "563492ad6f917000010000016841567e571c4414a49b31fd441ee456");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void getWallpapers() {
        wallpaperArraylist.clear();
        String url = "https://api.pexels.com/v1/curated?per_page=60&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray photoArray = response.getJSONArray("photos");
                    for (int i = 0; i < photoArray.length(); i++) {
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArraylist.add(imgUrl);
                    }
                    wallpaperAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to load wallpapers...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", "563492ad6f917000010000016841567e571c4414a49b31fd441ee456");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void getCategories() {
        //categoryAdapterArrayList.add(new CategoryModel("", ""));
        categoryAdapterArrayList.add(new CategoryModel("Nature", "https://images.unsplash.com/photo-1659077545364-817dc936837b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDE0fDZzTVZqVExTa2VRfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryAdapterArrayList.add(new CategoryModel("Technology", "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODd8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryAdapterArrayList.add(new CategoryModel("Music", "https://images.unsplash.com/photo-1492563817904-5f1dc687974f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODh8fG11c2ljfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryAdapterArrayList.add(new CategoryModel("Cars", "https://images.unsplash.com/photo-1580274455191-1c62238fa333?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80"));
        categoryAdapterArrayList.add(new CategoryModel("Flowers", "https://images.unsplash.com/photo-1444021465936-c6ca81d39b84?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80"));
        categoryAdapterArrayList.add(new CategoryModel("Abstract", "https://images.unsplash.com/photo-1617140237060-d09a58ba8edd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODV8fGFic3RyYWN0fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryAdapterArrayList.add(new CategoryModel("Programming", "https://images.unsplash.com/photo-1599837487527-e009248aa71b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryAdapterArrayList.add(new CategoryModel("Arts", "https://images.pexels.com/photos/354939/pexels-photo-354939.jpeg?auto=compress&cs=tinysrgb&w=600"));
        categoryAdapterArrayList.add(new CategoryModel("Travel", "https://images.pexels.com/photos/4553618/pexels-photo-4553618.jpeg?auto=compress&cs=tinysrgb&w=600"));
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryAdapterArrayList.get(position).getCategoryImg();
        getWallpapersByCategory(category);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }
}