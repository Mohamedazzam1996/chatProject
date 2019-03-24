package com.example.mazzam.chatproject.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mazzam.chatproject.FireBaseUtil.DataHolder;
import com.example.mazzam.chatproject.FireBaseUtil.Model.Message;
import com.example.mazzam.chatproject.FireBaseUtil.Model.Room;
import com.example.mazzam.chatproject.R;

import java.util.ArrayList;
import java.util.List;

public class RoomsChatRecyclerAdapter extends RecyclerView.Adapter<RoomsChatRecyclerAdapter.ViewHolder> {
        List<Message>messages;
        String userId;

    public RoomsChatRecyclerAdapter(List<Message> messages,String userId) {
        this.messages =messages;
        this.userId=userId;
    }

    final int incoming=1;
    final int outgoing=2;
    @Override
    public int getItemViewType(int position) {
        Message message=messages.get(position);
        if (message.getSender_id()==(userId))
        {
            return outgoing;
        }
        else
            return incoming;
        }

    View view=null;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType==incoming)
        {
            view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.messgae_card_item_incoming,viewGroup,false);
            return new IncomingViewHolder(view);

        }
        else if (viewType==outgoing) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.messgae_card_item_outgoing, viewGroup, false);
            return new ViewHolder(view);
        }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        Message message=messages.get(pos);
        int viewType=getItemViewType(pos);
        if (viewType==incoming)
        {
            ((IncomingViewHolder) viewHolder).sender_name.setText(message.getSender_name());
            ((IncomingViewHolder) viewHolder).date.setText(message.getDate());
            ((IncomingViewHolder) viewHolder).message.setText(message.getContent());
        }
        else if (viewType==outgoing)
        {
            viewHolder.message.setText(message.getContent());
            viewHolder.date.setText(message.getDate());
        }

    }

    @Override
    public int getItemCount() {
        if (messages==null)
        return 0;
        return messages.size();
    }
    public void changeData(List<Message> messages)
    {
        this.messages=messages;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView message,date;
        public ViewHolder(@NonNull View view) {
            super(view);
            message=view.findViewById(R.id.message_sent);
           date =view.findViewById(R.id.date);
        }
    }
    public void addMessageTolist(Message message)
    {
         if (messages==null)
         {
             messages=new ArrayList<>();
         }
        messages.add(message);
         notifyItemInserted(messages.size()-1);
    }
    public class IncomingViewHolder extends ViewHolder{
        TextView sender_name;
        public IncomingViewHolder(@NonNull View view) {
            super(view);
            sender_name=view.findViewById(R.id.sender_name);
        }
    }


}
