package com.example.taller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.taller.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    // Define local attributes to use the ref.
    private EditText etNumero;
    private Button btActualiza;
    private TextView tvActual;
    private Integer ValorEnBD;

    //Working with FirebaseDatabase
    private FirebaseDatabase database; //Db instances
    private DatabaseReference myRef; //Element Reference

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //Map controllers with attributes
        //Remember: on a fragment you should use root
        etNumero = root.findViewById(R.id.etNumero);
        btActualiza = root.findViewById(R.id.btActualiza);
        tvActual = root.findViewById(R.id.tvNumeroActual);

        //Create the connection instance with Firebase.
        //Get the db instance, get the element name
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("numero");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valor = snapshot.getValue(String.class);
                ValorEnBD = Integer.parseInt(valor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvActual.setText("Error");
            }
        });

        //Define the onclick method
        btActualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get number value
                String strNumero = etNumero.getText().toString();
                Integer num = Integer.parseInt(strNumero);
                if (num < 1 || num > 100){
                    tvActual.setText("El valor ingresado tiene que ser mayor que 1 y menor que 100");
                }else{
                    if (ValorEnBD!=num){
                        tvActual.setText("OH NO, SIGUE PARTICIPANDO");
                    }else{
                        tvActual.setText("GANASTE, HAS ACERTADO EL NUMERO");
                    }
                }
            }
        });

        return root;
    }
}