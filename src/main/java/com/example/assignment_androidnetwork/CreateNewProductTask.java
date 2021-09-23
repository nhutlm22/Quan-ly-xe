package com.example.assignment_androidnetwork;

import android.app.Activity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Han on 29/12/2016.
 */
// tryen text vo dau ba
public class CreateNewProductTask extends AsyncTask<String, String, String>{
    bai2 bai2;
    Context context;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    int a;
    FragmentTransaction fragmentTransaction;
    public CreateNewProductTask(Context context,FragmentTransaction fragmentTransaction ){
        this.context = context;
        jsonParser = new JSONParser();
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
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put("name",strings[0]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put("price",strings[1]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put("description",strings[2]);
        params.add(hsDes);
        // getting JSON Object
        // Note that create product url accepts POST Method
        JSONObject jsonObject = jsonParser.makeHttpRequest(Constants.url_create_products,"POST",params);
        Log.d("Create response",jsonObject.toString());
        try {
            int success = jsonObject.getInt(Constants.TAG_SUCCESS);
            if(success == 1){
                    a = success;
//                FragmentManager man = ((Activity)context).getFragmentManager();
//
//                FragmentTransaction tran=(Activity)context).man.beginTransaction();
//                tran.replace(R.id.fragment_frame,load);
//                tran.commit();
//                Fragment add = new AddProductFragment();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_frame, add);
//                ft.commit();
                // Successfully created product
             //   Intent intent = new Intent(context, AllProductsFragment.class);
             //   context.startActivity(intent);
              //  ((Activity)context).finish();
                // closing Create product screen
            }
        }catch (Exception e){
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
        if(a == 1){
            Fragment add = new AllProductsFragment();
            fragmentTransaction.replace(R.id.fragment_frame, add);
            fragmentTransaction.commit();
        }
    }
}


