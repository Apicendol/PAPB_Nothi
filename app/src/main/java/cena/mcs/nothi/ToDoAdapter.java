package cena.mcs.nothi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter {
    private final Context ctx;
    private List<Data> data;

    public  ToDoAdapter(List<Data> data, Context ctx) {
        this.ctx = ctx;
        this.data = data;
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title, desc;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            desc = itemView.findViewById(R.id.note_content);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder vh = (MyHolder) holder;
        Data notes = data.get(position);
        vh.title.setText(notes.getTitle());
        vh.desc.setText(notes.getDesc());

        vh.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(ctx, NoteDetails.class);
            intent.putExtra("title",notes.title);
            intent.putExtra("content",notes.desc);
            intent.putExtra("id",notes.id);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
