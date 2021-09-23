package com.example.assignment_androidnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment_androidnetwork.Fragment.LoginFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AllProductsFragment extends Fragment{
    private ListView lvProducts;
    LoadAllProductsTask task;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_products, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        lvProducts = (ListView)view.findViewById(R.id.listProducts);
        FragmentTransaction ft =getFragmentManager().beginTransaction();
        task = new LoadAllProductsTask(getContext(),lvProducts, ft);
        task.execute();
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment add = new AddProductFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame, add);
                ft.commit();
            }
        });
        receiveRC();
    }

    public void receiveRC() {
        Bundle bundle = new Bundle();
        String a = bundle.getString("pid");
        String aa = "";
        int b = bundle.getInt("rC");

        if (aa.equals(a) && b == 100) {
            Fragment allProduct = new AllProductsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, allProduct);
            ft.commit();
        }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == 100){
//            // if result code 100 is received
//            // means user edited/deleted product
//            // reload this screen again
//            Fragment allProduct = new AllProductsFragment();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.fragament_frame, allProduct);
//            ft.commit();
//        }
//    }

    }
}
