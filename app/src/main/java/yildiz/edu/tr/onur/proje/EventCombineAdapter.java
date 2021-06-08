package yildiz.edu.tr.onur.proje;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class EventCombineAdapter extends RecyclerView.Adapter<EventCombineAdapter.ViewHolder>{
    ArrayList<Combine> combines = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    File src;
    int eventID;
    public EventCombineAdapter(ArrayList<Combine> combines, int eventID, Context context){
        this.combines = combines;
        this.context = context;
        this.eventID = eventID;
    }

    @Override
    public EventCombineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View rowView = layoutInflater.inflate(R.layout.combines_row, parent, false);
        return new EventCombineAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCombineAdapter.ViewHolder holder, int position){
        ItemsDB itemsDB = new ItemsDB(context);
        Item hat = itemsDB.getAnItemWID(combines.get(position).getHatItemID());
        Item glasses = itemsDB.getAnItemWID(combines.get(position).getGlassesItemID());
        Item upper = itemsDB.getAnItemWID(combines.get(position).getUpperBodyItemID());
        Item lower = itemsDB.getAnItemWID(combines.get(position).getLowerBodyItemID());
        Item shoe = itemsDB.getAnItemWID(combines.get(position).getShoeItemID());

        src = new File(context.getFilesDir() + File.separator + "Items"+ File.separator + hat.getImgName());
        if (src.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
            holder.hat.setImageBitmap(myBitmap);
        }

        src = new File(context.getFilesDir() + File.separator + "Items"+ File.separator + glasses.getImgName());
        if (src.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
            holder.glasses.setImageBitmap(myBitmap);
        }

        src = new File(context.getFilesDir() + File.separator + "Items"+ File.separator + upper.getImgName());
        if (src.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
            holder.upper.setImageBitmap(myBitmap);
        }

        src = new File(context.getFilesDir() + File.separator + "Items"+ File.separator + lower.getImgName());
        if (src.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
            holder.lower.setImageBitmap(myBitmap);
        }

        src = new File(context.getFilesDir() + File.separator + "Items"+ File.separator + shoe.getImgName());
        if (src.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
            holder.shoe.setImageBitmap(myBitmap);
        }
        int id = combines.get(position).getId();
        holder.shareButton.setVisibility(View.GONE);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this combine from this Event?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                RelationWEventCombineDB db  = new RelationWEventCombineDB(context);
                                combines.remove(position);
                                notifyDataSetChanged();
                                db.deleteACombineFromEvent(eventID, id);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return combines.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView hat, glasses, upper, lower, shoe;
        ImageButton deleteButton, shareButton;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hat = itemView.findViewById(R.id.combineRowHat);
            this.glasses = itemView.findViewById(R.id.combineRowGlasses);
            this.upper = itemView.findViewById(R.id.combineRowUpperBody);
            this.lower = itemView.findViewById(R.id.combineRowLowerBody);
            this.shoe = itemView.findViewById(R.id.combineRowShoe);
            this.card = itemView.findViewById(R.id.combineRow);
            this.deleteButton = itemView.findViewById(R.id.combineRowDeleteButton);
            this.shareButton = itemView.findViewById(R.id.combineRowShareButton);
        }
    }
}

