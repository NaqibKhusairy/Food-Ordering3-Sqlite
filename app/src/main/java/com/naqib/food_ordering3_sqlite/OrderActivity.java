package com.naqib.food_ordering3_sqlite;

import androidx.appcompat.app.AppCompatActivity;

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
    int qburger, qfries, qchicken, qdrinks, qhotdog;
    double totalorder;
    SQLiteDatabase mDatabase;

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

        // Open the SQLite database or create if not exists
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

    private boolean inputsAreCorrect(String burger, String fries, String chicken,
                                     String hotdog, String cola) {
        if (burger.isEmpty()) {
            etqburger.setError("Please enter quantity");
            etqburger.requestFocus();
            return false;
        } else if (fries.isEmpty()) {
            etqfries.setError("Please enter quantity");
            etqfries.requestFocus();
            return false;
        } else if (chicken.isEmpty()) {
            etqchicken.setError("Please enter quantity");
            etqchicken.requestFocus();
            return false;
        } else if (hotdog.isEmpty()) {
            etqhotdog.setError("Please enter quantity");
            etqhotdog.requestFocus();
            return false;
        } else if (cola.isEmpty()) {
            etqdrinks.setError("Please enter quantity");
            etqdrinks.requestFocus();
            return false;
        }
        qburger = Integer.parseInt(burger);
        qfries = Integer.parseInt(fries);
        qchicken = Integer.parseInt(chicken);
        qhotdog = Integer.parseInt(hotdog);
        qdrinks = Integer.parseInt(cola);

        // Calculate total order
        totalorder = qburger * 8.90 + qfries * 4.90 + qchicken * 5.90 + qhotdog * 11.90 + qdrinks * 4.90;

        return true;
    }

    private void addOrder() {
        String burger = etqburger.getText().toString().trim();
        String fries = etqfries.getText().toString().trim();
        String chicken = etqchicken.getText().toString().trim();
        String hotdog = etqhotdog.getText().toString().trim();
        String cola = etqdrinks.getText().toString().trim();
        //double total=totalorder;
        String total=String.valueOf(totalorder);

        //getting the current time for joining date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());
        //validating the input

        if (inputsAreCorrect(burger, fries, chicken, hotdog, cola)) {
            String insertSQL = "INSERT INTO orders (burger, fries, chicken, hotdog, drinks, total, timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            //using the same method execsql for inserting values
            //this time it has two parameters
            //first is the sql string and second is the parameters that is to be binded with the query
            mDatabase.execSQL(insertSQL, new
                    String[]{burger, fries, chicken, hotdog, cola, total, joiningDate});
            Toast.makeText(this, "Order Added Successfully",
                    Toast.LENGTH_SHORT).show();
            etqchicken.setText("");
            etqburger.setText("");
            etqfries.setText("");
            etqdrinks.setText("");
            etqhotdog.setText("");
        }
    }
    @Override
    public void onClick(View view) {
        qburger = Integer.parseInt(etqburger.getText().toString().trim());
        qfries = Integer.parseInt(etqfries.getText().toString().trim());
        qchicken = Integer.parseInt(etqchicken.getText().toString().trim());
        qhotdog = Integer.parseInt(etqhotdog.getText().toString().trim());
        qdrinks = Integer.parseInt(etqdrinks.getText().toString().trim());
        totalorder = qburger + qfries + qchicken + qhotdog + qdrinks;

        Toast.makeText(getApplicationContext(),
                "Your Order is\n1. Burger - "+qburger+
                        "\n2. Fries -"+qfries+
                        "\n3. Chicken Wing -"+qchicken+
                        "\n4. Hot Dog -"+qhotdog+
                        "\n5. Drinks -"+qdrinks+
                        "\nYour Total Order -"+ totalorder
                ,Toast.LENGTH_LONG).show();
        addOrder();
    }
}