package com.naqib.food_ordering3_sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase mDatabase;
    List<Order> orderList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.listViewOrder);
        orderList = new ArrayList<>();
        dbHelper = new DBHelper(this, R.layout.list_layout, orderList, mDatabase);

        listView.setAdapter(dbHelper);

        mDatabase = openOrCreateDatabase("FoodOrders", MODE_PRIVATE, null);
        showOrdersFromDatabase();
    }

    private void showOrdersFromDatabase() {
        Cursor cursorOrders = mDatabase.rawQuery("SELECT * FROM orders", null);

        if (cursorOrders.moveToFirst()) {
            do {
                int id = cursorOrders.getInt(0);
                int burger = cursorOrders.getInt(1);
                int fries = cursorOrders.getInt(2);
                int chicken = cursorOrders.getInt(3);
                int hotdog = cursorOrders.getInt(4);
                int cola = cursorOrders.getInt(5);
                double total = cursorOrders.getDouble(6);
                String joiningDate = cursorOrders.getString(7);

                Order order = new Order(id, burger, fries, chicken, hotdog, cola, total, joiningDate);
                orderList.add(order);
            } while (cursorOrders.moveToNext());

            cursorOrders.close();
            dbHelper.notifyDataSetChanged();
        }
    }
}
