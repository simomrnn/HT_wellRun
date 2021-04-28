package com.example.ht_well;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


//Adapter is needed for recyclerView to work as intended.
//In recyclerview, the notes saved in the database will bve showed
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Note> entries;
    private NotesMain notesMain;

    Adapter(Context context, List<Note> entries, NotesMain notesMain) {
        this.inflater = LayoutInflater.from(context);
        this.entries = entries;
        this.notesMain = notesMain;
    }

    //method creates a new viewholder adapter, used for displaying items
    //introduces layout
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_list_view,viewGroup, false);
        return new ViewHolder(view);
    }

    //method gets information from list, calls upon methods from note class.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title = entries.get(i).getTitle();
        String date = entries.get(i).getDate();
        String time = entries.get(i).getTime();
        long ID = entries.get(i).getID();
        viewHolder.entryTitle.setText(title);
        viewHolder.entryDate.setText(date);
        viewHolder.entryTime.setText(time);
        viewHolder.entryID.setText(String.valueOf(entries.get(i).getID()));
    }


    //gets item ocunt on list
    @Override
    public int getItemCount() { return entries.size();}

    //A constructor viewholder, viewholder provides access to a dataset.
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView entryTitle, entryDate, entryTime, entryID;

        // a method for viewing items, that are in the xmll file.
        // an Onclicklistener for items inside the view, gets position of item clicked,
        // then information can be retrieved.
        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            entryTitle = itemView.findViewById(R.id.EntryTitle);
            entryDate = itemView.findViewById(R.id.EntryDate);
            entryTime = itemView.findViewById(R.id.EntryTime);
            entryID = itemView.findViewById(R.id.listID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notesMain.onRecycleClick(getAdapterPosition());
                }
            });
        }
    }
}
