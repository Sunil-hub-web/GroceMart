package com.example.grocemart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.activity.CartPage;
import com.example.grocemart.activity.MyOrderDetails;
import com.example.grocemart.modelclass.OrderDetails_ModelClass;
import com.example.grocemart.modelclass.ProductDetails_ModelClass;
import com.example.grocemart.modelclass.Variation_ModelClass;
import com.example.grocemart.url.APPURLS;
import com.google.gson.JsonArray;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsadapter extends RecyclerView.Adapter<OrderDetailsadapter.MyViewHolder> {

    Context context;
    ArrayList<OrderDetails_ModelClass> order;
    ArrayList<ProductDetails_ModelClass> product_details;


    String Userid;
    Dialog dialogMenu;
    TextView textView;
    String name,city;

    public OrderDetailsadapter(MyOrderDetails myOrderDetails, ArrayList<OrderDetails_ModelClass> orderDetails) {

        this.context = myOrderDetails;
        this.order = orderDetails;

    }

    @NonNull
    @NotNull
    @Override
    public OrderDetailsadapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetails,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderDetailsadapter.MyViewHolder holder, int position) {

        OrderDetails_ModelClass orderDetails = order.get(position);

        //ProductDetails_ModelClass product_det = orderDetails.getProductdetails().get(position);

        holder.text_orderId.setText(orderDetails.getOrder_id());
        holder.text_payment.setText(orderDetails.getPayment_mode());
        holder.text_statues.setText(orderDetails.getOrder_status());

        Userid = SharedPrefManager.getInstance(context).getUser().getId();



        holder.btn_ViewOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product_details = new ArrayList<ProductDetails_ModelClass>();
                product_details = orderDetails.getProductdetails();

                dialogMenu = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
                dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMenu.setContentView(R.layout.showproductdetails);
                dialogMenu.setCancelable(true);
                dialogMenu.setCanceledOnTouchOutside(true);

                textView = dialogMenu.findViewById(R.id.addressdetails);
                TextView subTotalPrice = dialogMenu.findViewById(R.id.subTotalPrice);
                TextView shippingCharges = dialogMenu.findViewById(R.id.shippingCharges);
                TextView totalPrice = dialogMenu.findViewById(R.id.totalPrice);
                TextView addressdetails = dialogMenu.findViewById(R.id.addressdetails);

                addressdetails.setText(orderDetails.getName()+", "+orderDetails.getCity()+", "+
                        orderDetails.getAddress()+", "+orderDetails.getPhoneNumber());

                Button btn_dismiss = dialogMenu.findViewById(R.id.btn_dismiss);

                subTotalPrice.setText(orderDetails.getSubtotal());
                shippingCharges.setText(orderDetails.getShipping_charge());
                totalPrice.setText(orderDetails.getTotal());

                RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                rv_vars.setLayoutManager(new LinearLayoutManager(context));
                rv_vars.setNestedScrollingEnabled(false);
                OrderDetAdapter varad = new OrderDetAdapter(product_details,context);
                rv_vars.setAdapter(varad);

                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogMenu.dismiss();

                    }
                });

                dialogMenu.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_orderId,text_payment,text_statues;
        Button btn_ViewOrderDetails;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_orderId = itemView.findViewById(R.id.text_orderId);
            text_statues = itemView.findViewById(R.id.text_statues);
            text_payment = itemView.findViewById(R.id.text_payment);
            btn_ViewOrderDetails = itemView.findViewById(R.id.btn_ViewOrderDetails);

        }
    }

}
