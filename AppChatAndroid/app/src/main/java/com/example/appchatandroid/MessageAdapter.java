package com.example.appchatandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    List<String> list;
    public MessageAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        holder.textViewUsername.setText(list.get(position));
        //holder.textViewMessage.setText(list.get(position));
    }
    public void addMessage(String m){
        list.add(m);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUsername;
        private TextView textViewMessage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = (TextView) itemView.findViewById(R.id.text_view_username);
            //textViewMessage = (TextView) itemView.findViewById(R.id.text_view_message);
        }
    }
}
