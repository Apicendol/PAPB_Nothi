package cena.mcs.nothi;

import android.content.Context;
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
    private List<Users> todos;
//    private DatabaseReference mDatabase;

    public void setNotesList(List<Users> todos) {
        this.todos = todos;
    }

//    public ToDoAdapter(Context ctx) {
    public ToDoAdapter(Context ctx, List<Users> todos) {
        this.ctx = ctx;
        this.todos = todos;
    }

    class VHTodo extends RecyclerView.ViewHolder {
        private TextView tvTanggal, tvIsi;
        private ImageView btnOption;

        public VHTodo(View rowView) {
            super(rowView);
            this.tvTanggal = rowView.findViewById(R.id.tvTanggal);
            this.tvIsi = rowView.findViewById(R.id.tvIsi);
            this.btnOption = rowView.findViewById(R.id.iVDelete);
        }
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx).inflate(R.layout.card, parent, false);
        return new VHTodo(rowView);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VHTodo vh = (VHTodo) holder;
        Users t = todos.get(position);
        vh.tvTanggal.setText(t.tanggal);
        vh.tvIsi.setText(t.isi);
    }

    public int getItemCount() {
        return todos.size();
    }
}
