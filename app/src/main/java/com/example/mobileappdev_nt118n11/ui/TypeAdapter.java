package com.example.mobileappdev_nt118n11.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdev_nt118n11.AdminAddTypeActivity;
import com.example.mobileappdev_nt118n11.AdminEditTypeActivity;
import com.example.mobileappdev_nt118n11.Model.TypeFood;
import com.example.mobileappdev_nt118n11.R;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {
    private ArrayList<TypeFood> mTypes;
    private Context mContext;

    public TypeAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(ArrayList<TypeFood> list) {
        mTypes = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {
            TypeFood typeFood = mTypes.get(position);
            if (typeFood == null) return;

            holder.tvId.setText(typeFood.getId());
            holder.tvName.setText(typeFood.getName());
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, AdminEditTypeActivity.class);
                    intent.putExtra("TYPE_ID", typeFood.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        if (mTypes != null) return mTypes.size();
        return 0;
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvName;
        private Button btnEdit;

        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_type_id);
            tvName = itemView.findViewById(R.id.tv_type_name);
            btnEdit = itemView.findViewById(R.id.btn_type_edit);
        }
    }
}
