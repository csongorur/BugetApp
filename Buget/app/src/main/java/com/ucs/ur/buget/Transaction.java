package com.ucs.ur.buget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Transaction extends AppCompatActivity {

    EditText name;
    EditText money;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        name = (EditText) findViewById(R.id.edName);
        money = (EditText) findViewById(R.id.edMoney);
    }

    public void Insert (View v)
    {
        String txtName;
        String txtMoney;

        txtName=name.getText().toString();
        txtMoney=money.getText().toString();


        AsyncPostData asyncPostData = new AsyncPostData(this, txtName, txtMoney, "1");
        asyncPostData.execute("http://bugetapp.azurewebsites.net/API/api.php");
        name.setText("");
        money.setText("");
    }

    public void Remove (View v)
    {
        String txtName;
        String txtMoney;

        txtName=name.getText().toString();
        txtMoney=money.getText().toString();


        AsyncPostData asyncPostData = new AsyncPostData(this, txtName, txtMoney, "2");
        asyncPostData.execute("http://bugetapp.azurewebsites.net/API/api.php");
        name.setText("");
        money.setText("");
    }
}
