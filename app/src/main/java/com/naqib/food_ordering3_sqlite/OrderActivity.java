package com.naqib.food_ordering3_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etqburger, etqfries, etqchicken, etqdrinks, etqhotdog;
    Button btnorder;
    int qburger, qfries, qchicken, qdrinks, qhotdog, x = 0;
    double totalorder;
    SQLiteDatabase mDatabase;
    String ToasT="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        etqburger = findViewById(R.id.etqburger);
        etqfries = findViewById(R.id.etqfries);
        etqchicken = findViewById(R.id.etqchicken);
        etqdrinks = findViewById(R.id.etqdrinks);
        etqhotdog = findViewById(R.id.etqhotdog);
        btnorder = findViewById(R.id.btnorder2);
        btnorder.setOnClickListener(this);


        mDatabase = openOrCreateDatabase("FoodOrders", MODE_PRIVATE, null);
        createOrderTable();
    }

    private void createOrderTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS orders (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "burger TEXT," +
                        "fries TEXT," +
                        "chicken TEXT," +
                        "hotdog TEXT," +
                        "drinks TEXT," +
                        "total TEXT," +
                        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);"
        );
    }

    @SuppressLint("DefaultLocale")
    private boolean inputsAreCorrect(String burger, String fries, String chicken,
                                     String hotdog, String cola) {
        ToasT += "Your Order is : ";
        if (burger.isEmpty()) {
            burger = "0";
        }
        else {
            x += 1;
            ToasT += "\n"+x+". Burger - "+burger;
        }
        if (fries.isEmpty()) {
            fries = "0";
        }
        else {
            x += 1;
            ToasT += ", "+x+". Fries - "+fries;
        }
        if (chicken.isEmpty()) {
            chicken = "0";
        }
        else {
            x += 1;
            ToasT += ", "+x+". Chicken Wing - "+chicken;
        }
        if (hotdog.isEmpty()) {
            hotdog = "0";
        }
        else {
            x += 1;
            ToasT += ", "+x+". Hot Dog - "+hotdog;
        }
        if (cola.isEmpty()) {
            cola = "0";
        }
        else {
            x += 1;
            ToasT += ", "+x+". Drinks - "+cola;
        }
        try {
            qburger = Integer.parseInt(burger);
            qfries = Integer.parseInt(fries);
            qchicken = Integer.parseInt(chicken);
            qhotdog = Integer.parseInt(hotdog);
            qdrinks = Integer.parseInt(cola);

            totalorder = qburger * 8.90 + qfries * 4.90 + qchicken * 5.90 + qhotdog * 11.90 + qdrinks * 4.90;

            if (totalorder != 0){
                ToasT += ", Your Total Order - RM " + String.format("%.2f", totalorder);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @SuppressLint({"SimpleDateFormat","DefaultLocale"})
    private void addOrder() {
        String burger = etqburger.getText().toString().trim();
        String fries = etqfries.getText().toString().trim();
        String chicken = etqchicken.getText().toString().trim();
        String hotdog = etqhotdog.getText().toString().trim();
        String cola = etqdrinks.getText().toString().trim();
        String total=String.format("%.2f", totalorder);

        Calendar cal = Calendar.getInstance();SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());


        if (inputsAreCorrect(burger, fries, chicken, hotdog, cola)) {
            String insertSQL = "INSERT INTO orders (burger, fries, chicken, hotdog, drinks, total, timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            mDatabase.execSQL(insertSQL, new
                    String[]{burger, fries, chicken, hotdog, cola, total, joiningDate});
            Toast.makeText(this, "Order Added Successfully",
                    Toast.LENGTH_SHORT).show();
            etqchicken.setText("");
            etqburger.setText("");
            etqfries.setText("");
            etqdrinks.setText("");
            etqhotdog.setText("");
            ToasT = "";
            x = 0;
        }
    }
    @Override
    public void onClick(View view) {
        String burger = etqburger.getText().toString().trim();
        String fries = etqfries.getText().toString().trim();
        String chicken = etqchicken.getText().toString().trim();
        String hotdog = etqhotdog.getText().toString().trim();
        String cola = etqdrinks.getText().toString().trim();

        try {
            inputsAreCorrect(burger, fries, chicken, hotdog, cola);

            if(totalorder!=0.0){
                Toast.makeText(getApplicationContext(), ToasT,Toast.LENGTH_SHORT).show();
                addOrder();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Please add items to your order.",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Invalid input. Please enter valid quantities.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}