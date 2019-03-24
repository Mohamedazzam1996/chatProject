package com.example.mazzam.chatproject.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mazzam.chatproject.FireBaseUtil.Model.Room;
import com.example.mazzam.chatproject.R;

import java.util.List;

public class RoomsRecyclerAdapter extends RecyclerView.Adapter<RoomsRecyclerAdapter.ViewHolder> {
        List<Room>rooms;
        OnItemClickListner onRoomClick;

    public void setOnRoomClick(OnItemClickListner onRoomClick) {
        this.onRoomClick = onRoomClick;
    }

    public RoomsRecyclerAdapter(List<Room> rooms) {
        this.rooms = rooms;
    }

    View view;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_room_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        final Room room=rooms.get(pos);
        viewHolder.room_name.setText(room.getRoom_name());
        viewHolder.room_desc.setText(room.getRoom_desc());
        viewHolder.room_stamp.setText(room.getRoom_stamp());
        if (onRoomClick!=null)
        {
         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onRoomClick.onItemClick(pos,room);
             }
         });
        }
    }

    @Override
    public int getItemCount() {
        if (rooms==null)
        return 0;
        return rooms.size();
    }
    public void changeData(List<Room> rooms)
    {
        this.rooms=rooms;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView room_name,room_desc,room_stamp;
        public ViewHolder(@NonNull View view) {
            super(view);
            room_name=view.findViewById(R.id.room_name);
           room_desc =view.findViewById(R.id.room_desc);
            room_stamp=view.findViewById(R.id.room_stamp);
        }
    }
    public interface OnItemClickListner{
        public void onItemClick(int pos,Room room);

    }
}
