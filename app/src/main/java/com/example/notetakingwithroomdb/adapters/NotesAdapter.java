package com.example.notetakingwithroomdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notetakingwithroomdb.R;
import com.example.notetakingwithroomdb.roomdb.NoteModel;

import org.w3c.dom.Text;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.noteHolder> {

    private List<NoteModel> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnNoteItemClick onNoteItemClick;

    //Constructor


    public NotesAdapter(List<NoteModel> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onNoteItemClick = (OnNoteItemClick) context;
    }

    public interface OnNoteItemClick{
        void onNoteClick(int pos);
    }

    @NonNull
    @Override
    public noteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.note_list_item, parent, false);

        return new noteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull noteHolder holder, int position) {
        holder.textViewTitle.setText(list.get(position).getTitle());
        holder.textViewContent.setText(list.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    //NoteHolder Class  (ViewHolder)
    public class noteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewContent;
        TextView textViewTitle;

        public noteHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewContent = itemView.findViewById(R.id.item_text);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void onClick(View v) {
            onNoteItemClick.onNoteClick(getAdapterPosition());
        }
    }

}


