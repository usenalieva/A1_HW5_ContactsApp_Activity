package com.example.contacts_app;

import  android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    OnItemMenuClick listener;
    List<TitleModel> list;
    Context context;
    OnContactClickListener onContactClickListener;

    public void setOnContactClickListener(OnContactClickListener onContactClickListener) {
        this.onContactClickListener = onContactClickListener;
    }

    public void setListener(OnItemMenuClick listener) {
        this.listener = listener;
    }

    public MainAdapter(List<TitleModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void addApplication(TitleModel titleModel) {
        list.add(titleModel);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new MainViewHolder(view);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.txtMenu.setOnClickListener(v -> {
            final PopupMenu popupMenu = new PopupMenu(context, holder.txtMenu);
            popupMenu.inflate(R.menu.menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.menu_save:
                        listener.save(position);
                        break;
                    case R.id.menu_delete:
                        listener.delete(position);
                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPhone;
        ImageView imageView;
        TitleModel titleModel;
        TextView txtMenu;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPhone = itemView.findViewById(R.id.phone);
            txtName = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.imageView);
            txtMenu = itemView.findViewById(R.id.txtOptionMenu);
            itemView.setOnClickListener(view -> onContactClickListener.onContactCLick(getAdapterPosition()));

        }

        @SuppressLint("NonConstantResourceId")
        public void onBind(TitleModel model) {
            this.titleModel = model;
            txtName.setText(model.title);
            txtPhone.setText(model.number);
            Glide.with(context)
                    .load(model.imageView)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
        }
    }
}

