package com.example.endesh.myhealthcareapp.EasyNote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.endesh.myhealthcareapp.R;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    public interface OnDeleteClickListener{
        void OnDeleteClickListener(Note myNote);
    }

    //LayoutInflater will Instantiates a layout XML file into its corresponding View objects
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Note> mNotes;
    private OnDeleteClickListener onDeleteClickListener;

    public NoteListAdapter(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        if(mNotes != null){
            Note note = mNotes.get(position);
            holder.setData(note.getNote(), position);
            holder.setListener();
        }
        else {
            //covers the case of data not being ready yet
            holder.noteItemView.setText(R.string.no_note);
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    //we call this method from main activity to update note list
    //and it also notify the dataset changed
    public void setNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView;
        private int mPosition;
        private ImageView imgDelete, imgEdit;
        public NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            imgDelete = itemView.findViewById(R.id.ivRowDelete);
            imgEdit = itemView.findViewById(R.id.ivRowEdit);
        }
        //setting the note in the item view and setting the position
        public void setData(String note, int position){
            noteItemView.setText(note);
            mPosition = position;
        }

        public void setListener() {
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditNoteActivity.class);
                    intent.putExtra("note_id", mNotes.get(mPosition).getId());
                    ((Activity)mContext).startActivityForResult(intent, MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);

                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onDeleteClickListener != null){
                        onDeleteClickListener.OnDeleteClickListener(mNotes.get(mPosition));
                    }

                }
            });
        }
           }

}
