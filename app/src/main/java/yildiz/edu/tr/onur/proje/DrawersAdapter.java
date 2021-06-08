package yildiz.edu.tr.onur.proje;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrawersAdapter extends RecyclerView.Adapter<DrawersAdapter.ViewHolder>{
    ArrayList<Drawer> drawers = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    int cabin;
    Integer [] ids;
    public DrawersAdapter(ArrayList<Drawer> drawers,int cabin, Integer[] ids, Context context){
        this.drawers = drawers;
        this.context = context;
        this.cabin = cabin;
        this.ids = ids;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View rowView = layoutInflater.inflate(R.layout.drawers_row, parent, false);
        return new ViewHolder(rowView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.drawerName.setText(drawers.get(position).getName());
        int id = drawers.get(position).getId();

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Drawer")
                        .setMessage("Are you sure you want to delete this drawer?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DrawersDB db  = new DrawersDB(context);
                                drawers.remove(position);
                                notifyDataSetChanged();
                                db.deleteADrawer(id);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DrawerActivity.class);
                intent.putExtra("drawerid", id);
                intent.putExtra("requestCode", cabin);
                intent.putExtra("hatID", ids[0]);
                intent.putExtra("glassID", ids[1]);
                intent.putExtra("upperID", ids[2]);
                intent.putExtra("lowerID", ids[3]);
                intent.putExtra("shoeID", ids[4]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView drawerName;
        ImageButton deleteButton;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.drawerName = itemView.findViewById(R.id.drawersRowName);
            this.deleteButton = itemView.findViewById(R.id.drawersRowDeleteButton);
            this.card = itemView.findViewById(R.id.drawerCard);
        }
    }
}
