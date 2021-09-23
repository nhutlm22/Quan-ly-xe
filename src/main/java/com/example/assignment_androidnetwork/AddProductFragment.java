package com.example.assignment_androidnetwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment_androidnetwork.Fragment.LoginFragment;

public class AddProductFragment extends Fragment {
    private EditText edtName, edtPrice, edtDescription;
    private Button btnAdd;
    String strName,strPrice,strDes;
    CreateNewProductTask newProductTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        edtName = (EditText)view.findViewById(R.id.edtProductName);
        edtPrice = (EditText)view.findViewById(R.id.edtProductPrice);
        edtDescription = (EditText)view.findViewById(R.id.edtProductDes);
        btnAdd = (Button)view.findViewById(R.id.btnAdd);

        FragmentTransaction ft =getFragmentManager().beginTransaction();
        newProductTask = new CreateNewProductTask(getContext(),ft);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strName = edtName.getText().toString();
                strPrice = edtPrice.getText().toString();
                strDes = edtDescription.getText().toString();
                newProductTask.execute(strName,strPrice,strDes);
            }
        });
    }

}