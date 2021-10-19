package com.example.grocemart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocemart.CartPage;
import com.example.grocemart.R;

import org.jetbrains.annotations.NotNull;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    int[] imageArray;
    String[] names;
    String[] priceArray;

    int totalPrice = 0;
    String totPrice;

    public ProductAdapter(CartPage cartPage, int[] images, String[] name, String[] strings) {

        this.context = cartPage;
        this.imageArray = images;
        this.names = name;
        this.priceArray = strings;
    }

    @NonNull
    @NotNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_page,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(imageArray[position]);
        holder.text_name.setText(names[position]);
        holder.price.setText(priceArray[position]);


        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(false);


                String t = holder.t2.getText().toString().trim();

                String p = priceArray[position];

                int t1 = Integer.parseInt(t);
                int p1 = Integer.parseInt(p);

                int m =  t1 * p1 ;

                Toast.makeText(context, ""+m, Toast.LENGTH_SHORT).show();

                String m1 = String.valueOf(m);
                holder.price.setText(m1);

                totPrice = holder.price.getText().toString().trim();

            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(true);

                String t = holder.t2.getText().toString().trim();

                String p = priceArray[position];

                int t1 = Integer.parseInt(t);
                int p1 = Integer.parseInt(p);

                int m =  t1 * p1 ;

                Toast.makeText(context, ""+m, Toast.LENGTH_SHORT).show();
                String m1 = String.valueOf(m);
                holder.price.setText(m1);

                totPrice = holder.price.getText().toString().trim();

            }
        });

        totPrice = holder.price.getText().toString().trim();
        int price1 = Integer.parseInt(totPrice);
        int count = getItemCount();
        for (int i = 0; i<count; i++)
        {
            totalPrice = totalPrice + price1;
            Log.d("totalpay",String.valueOf(totalPrice));
            return;
        }

    }

    public int getvalue(){

        return totalPrice;
    }

    @Override
    public int getItemCount() {
        return imageArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView text_name,t1, t2, t3,price;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagecart);
            text_name = itemView.findViewById(R.id.topProduct);
            price = itemView.findViewById(R.id.price);

            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);

        }
        private void linearLayout(Boolean x){
            int y = Integer.parseInt(t2.getText().toString());
            if(x){
                y++;
                t2.setText(String.valueOf(y));
            }else {
                y--;
                if(y <= 0){
                    t2.setText("0");
                }else {
                    t2.setText(String.valueOf(y));
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
