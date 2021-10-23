package com.example.grocemart.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.grocemart.activity.AddressDetails;
import com.example.grocemart.modelclass.ViewAddressDetails_ModelClass;
import com.example.grocemart.url.APPURLS;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewaddressDetailsAdapter extends RecyclerView.Adapter<ViewaddressDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<ViewAddressDetails_ModelClass> address_Details;
    private OnItemClickListener mListener;
    String addressId;

    public ViewaddressDetailsAdapter(AddressDetails addressDetails,
                                     ArrayList<ViewAddressDetails_ModelClass> addressDetails1) {

        this.context = addressDetails;
        this.address_Details = addressDetails1;

    }

    public interface OnItemClickListener {

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewaddressDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addressdetails,parent,false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewaddressDetailsAdapter.ViewHolder holder, int position) {

        ViewAddressDetails_ModelClass viewAddress = address_Details.get(position);

        holder.text_Address.setText(viewAddress.getUserName()+", "+viewAddress.getMobileNo()
                                    +", "+viewAddress.getEmail()+", "+viewAddress.getCityName()
                                    +", "+viewAddress.getArea()+", "+viewAddress.getAddress()+", "+viewAddress.getPincode());

        addressId = viewAddress.getAddressId();

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                TextView textView = progressDialog.findViewById(R.id.text);
                textView.setText("Delete Address Please wait...");
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.setCancelable(false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.deleteAddress, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String message = jsonObject.getString("success");

                            if (message.equals("true")){

                                Toast.makeText(context, "Address Deleted Success Fully.", Toast.LENGTH_SHORT).show();
                                address_Details.remove(position);
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        error.printStackTrace ();
                        Toast.makeText (context, "Address not Stored Successfully", Toast.LENGTH_SHORT).show ( );

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params = new HashMap<>();

                        params.put("address_id",addressId);

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            }
        });

    }

    @Override
    public int getItemCount() {
        return address_Details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_Address;
        Button btn_Delete;

        public ViewHolder(@NonNull  View itemView,final OnItemClickListener listener) {
            super(itemView);

            text_Address = itemView.findViewById(R.id.addressdetails);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);

            btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}