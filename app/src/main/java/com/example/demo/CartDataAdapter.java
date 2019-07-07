package com.example.demo;

import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class CartDataAdapter extends RecyclerView.Adapter<CartDataAdapter.EditorViewHolder> {
    private List<CartItem> CartList;



    public  CartDataAdapter(List<CartItem> CartList){
        this.CartList = CartList;
    }

    @Override
    public int getItemCount(){
        return CartList.size();
    }

    @NonNull
    @Override
    public CartDataAdapter.EditorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_single_item, viewGroup, false);
        return new CartDataAdapter.EditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartDataAdapter.EditorViewHolder cartViewHolder, final int i){
        final CartItem item = CartList.get(i);
        CartDataAdapter.EditorViewHolder.rName.setText(item.restaurantName);
        CartDataAdapter.EditorViewHolder.mName.setText(item.itemName);
        CartDataAdapter.EditorViewHolder.mPrice.setText(item.price);
        CartDataAdapter.EditorViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartItemID = item.cartItemID;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("buyers")
                        .child(user.getUid()).child("cart").child(cartItemID);
                databaseReference.removeValue();
                Log.i("test","no error");
                

            }
        });

        final TextView collection_time = CartDataAdapter.EditorViewHolder.eText;
        collection_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                CartDataAdapter.EditorViewHolder.picker = new RangeTimePickerDialog(v.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        String AM_PM ;
                        if(sHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        collection_time.setText("Collection time: "+sHour + ":" + sMinute + ' ' + AM_PM );
                        collection_time.setTextColor(Color.argb(255, 0, 0, 0));
                    }

                }, hour, minutes, false);
                ((RangeTimePickerDialog) CartDataAdapter.EditorViewHolder.picker).setMax(8,30);
                ((RangeTimePickerDialog) CartDataAdapter.EditorViewHolder.picker).setMax(22,30);

                CartDataAdapter.EditorViewHolder.picker.show();
//                EditorViewHolder.eText.setText("Selected Time: "+ .getText());
            }
        });




    }

    public static class  EditorViewHolder extends RecyclerView.ViewHolder{
        protected static TextView rName;
        protected static TextView mName;
        protected static TextView mPrice;
        protected static View cardView;
        protected static ImageButton delete;
        protected static RangeTimePickerDialog picker;
        protected static TextView eText;

        public EditorViewHolder(View view){
            super(view);
            rName = view.findViewById(R.id.item_restaurant_name);
            mName = view.findViewById(R.id.item_name);
            mPrice = view.findViewById(R.id.item_price);
            cardView = view.findViewById((R.id.cart_view));
            delete = view.findViewById(R.id.delete_from_cart);

            eText= view.findViewById(R.id.item_collection_time);
            eText.setTextColor(Color.argb(100, 0, 0, 0));
            eText.setPaintFlags(eText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
