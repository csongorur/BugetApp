package com.ucs.ur.buget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void Transaction(View v)
    {
        Intent intent = new Intent(getApplicationContext(),Transaction.class);
        startActivity(intent);
    }

    public void Query(View v)
    {
        Intent intent = new Intent(getApplicationContext(),Query.class);
        startActivity(intent);
    }
}
