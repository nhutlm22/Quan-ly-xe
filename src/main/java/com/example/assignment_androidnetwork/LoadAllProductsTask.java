package com.example.assignment_androidnetwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadAllProductsTask extends AsyncTask<String, String, String> {
    Context context;
    ListView lvProducts;
    ProgressDialog pDialog;
    JSONParser jParser;
    ArrayList<Product> listProducts;
    JSONArray products = null;
    AdapterProduct adapterProduct;
    FragmentTransaction fragmentTransaction;
    public LoadAllProductsTask(Context context, ListView lvProducts, FragmentTransaction fragmentTransaction) {
        this.context = context;
        this.lvProducts = lvProducts;
        jParser = new JSONParser();
        listProducts = new ArrayList<>();
        this.fragmentTransaction = fragmentTransaction;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        JSONObject jsonObject =
                jParser.makeHttpRequest(Constants.url_all_products, "GET", params);
        try {
            int success = jsonObject.getInt("success");
            if (success == 1) {
                //Log.d("All Products: ", jsonObject.toString());
                products = jsonObject.getJSONArray(Constants.TAG_PRODUCTS);
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);
                    // Storing each json item in variable
                    String id = c.getString(Constants.TAG_PID);
                    String name = c.getString(Constants.TAG_NAME);
                    String price = c.getString(Constants.TAG_PRICE);
                    // creating new Product
                    Product product = new Product();
                    product.setId(id);
                    product.setName(name);
                    product.setPrice(price);

                    listProducts.add(product);
                }
            } else {
                // no products found
                // Launch Add New product Activity
                Fragment add = new AddProductFragment();
                fragmentTransaction.replace(R.id.fragment_frame, add);
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        adapterProduct = new AdapterProduct(context, listProducts);
        lvProducts.setAdapter(adapterProduct);
        lvProducts.setOnItemClickListener(new
          AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  String pid = listProducts.get(i).getId();
//                  Intent intent = new Intent(context, EditProductFragment.class);
//                  intent.putExtra(Constants.TAG_PID, pid);
//                  ((Fragment) context).startActivityForResult(intent, 100);

                  //Fragment edit = new EditProductFragment();

                  Bundle b = new Bundle();
                  b.putString("pid", pid);
                  b.putInt("rC", 100);
                  EditProductFragment fragment = new EditProductFragment();
                  fragment.setArguments(b);
                  fragmentTransaction.replace(R.id.fragment_frame, fragment);
                  fragmentTransaction.commit();
                  Log.e("pid ", pid);

              }
          });
    }
}