package com.example.assignment_androidnetwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveProductDetailsTask extends AsyncTask<String, String,String> {
    Context context;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    boolean boolSuccess = false;
    FragmentTransaction fragmentTransaction;
    int a;
    public SaveProductDetailsTask(Context context, FragmentTransaction fragmentTransaction) {
        this.context = context;
        jsonParser = new JSONParser();
        this.fragmentTransaction = fragmentTransaction;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Saving product...");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsPid = new HashMap<>();
        hsPid.put(Constants.TAG_PID, strings[0]);
        params.add(hsPid);
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put(Constants.TAG_NAME, strings[1]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put(Constants.TAG_PRICE, strings[2]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put(Constants.TAG_DESCRIPTION, strings[3]);
        params.add(hsDes);
        // sending modified data through http request
        // Notice that update product url accepts POST method
        JSONObject object =
                jsonParser.makeHttpRequest(Constants.url_update_product, "POST", params);
        try{
            int success = object.getInt(Constants.TAG_SUCCESS);
            if(success == 1){
                // successfully updated
                boolSuccess = true;
                a = success;
            }else {
                // failed to update product
                boolSuccess = false;
            }
        }catch (Exception e){
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
        if(boolSuccess){
            Fragment add = new AllProductsFragment();
            fragmentTransaction.replace(R.id.fragment_frame, add);
            fragmentTransaction.commit();
        }
    }
}