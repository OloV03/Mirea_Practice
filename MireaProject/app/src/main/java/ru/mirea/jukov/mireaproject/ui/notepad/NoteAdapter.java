package ru.mirea.jukov.mireaproject.ui.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.jukov.mireaproject.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Note> notes;

    NoteAdapter(Context context, List<Note> notes) {
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note, parent, false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.nameNote.setText(note.getName());
        holder.dateNote.setText(note.getDate());
        holder.textNote.setText(note.getText());
    }

    @Override
    public int getItemCount() { return notes.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameNote, dateNote, textNote;
        ViewHolder(View view){
            super(view);
            nameNote = view.findViewById(R.id.nameNote);
            dateNote = view.findViewById(R.id.dateNote);
            textNote = view.findViewById(R.id.textNote);
        }
    }
}
