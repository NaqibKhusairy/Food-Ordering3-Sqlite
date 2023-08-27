package com.naqib.food_ordering3_sqlite;;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
public class DBHelper extends ArrayAdapter<Order> {
    Context mCtx;
    int listLayoutRes;
    List<Order> orderList;
    SQLiteDatabase mDatabase;
    public DBHelper(Context mCtx, int listLayoutRes, List<Order> orderList,
                    SQLiteDatabase mDatabase){
        super(mCtx, listLayoutRes, orderList);
        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.orderList = orderList;
        this.mDatabase = mDatabase;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final Order order = orderList.get(position);

        TextView textViewBurger = view.findViewById(R.id.textViewBur);
        TextView textViewFries = view.findViewById(R.id.textViewFries);
        TextView textViewChicken = view.findViewById(R.id.textViewChicken);
        TextView textViewhotDog = view.findViewById(R.id.textViewHotDog);
        TextView textViewCola = view.findViewById(R.id.textViewCola);
        TextView textViewTotal = view.findViewById(R.id.textViewTotal);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);

        // Set the values of the TextViews using the Order object's properties
        textViewBurger.setText("Burger: " + order.getBurger());
        textViewFries.setText("Fries: " + order.getFries());
        textViewChicken.setText("Chicken: " + order.getChicken());
        textViewhotDog.setText("Hot Dog: " + order.getHotdog());
        textViewCola.setText("Cola: " + order.getCola());
        textViewTotal.setText("Total: " + order.getTotal());
        textViewJoiningDate.setText("Date: " + order.getJoiningDate());

        return view;
    }
}