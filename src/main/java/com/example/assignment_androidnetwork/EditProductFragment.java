package com.example.assignment_androidnetwork;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditProductFragment extends Fragment implements
        View.OnClickListener {
    private EditText edtName, edtPrice, edtDes;
    private Button btnSave, btnDelete;
    String pid, strName, strPrice, strDes;
    GetProductDetailsTask productDetailsTask;
    SaveProductDetailsTask saveProductDetailsTask;
    DeleteProductTask deleteProductTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_product, container, false);
        initViews(view);
        //pid = getString(Integer.parseInt(String.valueOf(b)));
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            pid = bundle.getString(Constants.TAG_PID);
        }

        productDetailsTask = new GetProductDetailsTask(getContext(),edtName,edtPrice,edtDes);
        productDetailsTask.execute(pid);
        return view;
    }

    private void initViews(View view) {
        edtName = (EditText)view.findViewById(R.id.edtProductName);
        edtPrice = (EditText) view.findViewById(R.id.edtProductPrice);
        edtDes = (EditText) view.findViewById(R.id.edtProductDes);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        //int b = saveProductDetailsTask.a;
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                strName = edtName.getText().toString();
                strPrice = edtPrice.getText().toString();
                strDes = edtDes.getText().toString();
                FragmentTransaction ft =getFragmentManager().beginTransaction();
                saveProductDetailsTask = new SaveProductDetailsTask(getContext(), ft);
                saveProductDetailsTask.execute(pid,strName,strPrice,strDes);
                break;
            case R.id.btnDelete:
                FragmentTransaction ftt =getFragmentManager().beginTransaction();
                deleteProductTask = new DeleteProductTask(getContext(), ftt);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Deleting product...");
                builder.setMessage("Are you sure you want delete this product?");
                        builder.setNegativeButton("Yes", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        deleteProductTask.execute(pid);
                                        dialogInterface.dismiss();

                                        //Toast.makeText(EditProductFragment.class,"Deleted",Toast.LENGTH_LONG).show();
                                    }
                                });
                builder.setPositiveButton("No", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
                break;
        }
    }
}
