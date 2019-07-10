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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CartDataAdapter extends RecyclerView.Adapter<CartDataAdapter.EditorViewHolder> {
    private List<CartItem> CartList;


    public int addTime(int a, int b){
        int total = a + b;
        int hour = total / 100;
        int minutes = total % 100;
        if(minutes >= 60){
            ++hour;
            minutes -= 60;
        }
        return hour * 100 + minutes;
    }

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

        int restaurantOpening =  Integer.parseInt(item.restaurantOpening);
        final int restaurantClosing = Integer.parseInt(item.restaurantClosing);
        int dishPrepTime = Integer.parseInt(item.prepTime);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HHmm");
        String formattedTime = dateFormat.format(date);
        int timeNow = Integer.parseInt(formattedTime);
        final int minOrderTime = timeNow > restaurantOpening ? addTime(timeNow ,dishPrepTime) : addTime(restaurantOpening, dishPrepTime);
        String orderTime = minOrderTime < restaurantClosing ?  "Click to select collection time: " +  String.format("%02d",minOrderTime / 100) + ':' + String.format("%02d",minOrderTime % 100) + " to " +  String.format("%02d", restaurantClosing / 100) + ':' + String.format("%02d", restaurantClosing % 100)
                : "Item Unavailable";
//        String orderTime = "Collection Time: " +  minOrderTime / 100 + ':' + String.format("%02d",minOrderTime % 100) + " to " + restaurantClosing / 100 + ':' + String.format("%02d", restaurantClosing % 100);
        EditorViewHolder.eText.setText(orderTime);
        CartDataAdapter.EditorViewHolder.rName.setText(item.restaurantName);
        CartDataAdapter.EditorViewHolder.mName.setText(item.itemName);
        CartDataAdapter.EditorViewHolder.mPrice.setText("$" + item.price);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(item.imageRef);
        GlideApp.with(EditorViewHolder.dishImage.getContext()/* context */)
                .load(storageRef)
                .into(EditorViewHolder.dishImage);
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
        if(true/*orderTime != "Item Unavailable"*/) {
            collection_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int hour = cldr.get(Calendar.HOUR_OF_DAY);
                    int minutes = cldr.get(Calendar.MINUTE);
                    // time picker dialog

                    CartDataAdapter.EditorViewHolder.picker = new RangeTimePickerDialog(v.getContext(), R.style.Theme_AppCompat_Dialog_Alert,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    collection_time.setText("Collection time: " + String.format("%02d",sHour) + ":" + String.format("%02d",sMinute));
                                    collection_time.setTextColor(Color.argb(255, 0, 0, 0));
                                }

                            }, hour, minutes, true);
                    CartDataAdapter.EditorViewHolder.picker.setMin(minOrderTime/100, minOrderTime % 100);
                    CartDataAdapter.EditorViewHolder.picker.setMax(restaurantClosing / 100, restaurantClosing % 100);
                    EditorViewHolder.picker.updateTime(minOrderTime/100 , minOrderTime % 100);
                    CartDataAdapter.EditorViewHolder.picker.show();
//                EditorViewHolder.eText.setText("Selected Time: "+ .getText());
                }
            });
        }




    }

    public static class  EditorViewHolder extends RecyclerView.ViewHolder{
        protected static TextView rName;
        protected static TextView mName;
        protected static TextView mPrice;
        protected static View cardView;
        protected static ImageButton delete;
        protected static RangeTimePickerDialog picker;
        protected static TextView eText;
        protected static ImageView dishImage;

        public EditorViewHolder(View view){
            super(view);
            rName = view.findViewById(R.id.item_restaurant_name);
            mName = view.findViewById(R.id.item_name);
            mPrice = view.findViewById(R.id.item_price);
            cardView = view.findViewById((R.id.cart_view));
            delete = view.findViewById(R.id.delete_from_cart);
            dishImage = view.findViewById(R.id.dish_image);
            eText= view.findViewById(R.id.item_collection_time);
            eText.setTextColor(Color.argb(100, 0, 0, 0));
            eText.setPaintFlags(eText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
