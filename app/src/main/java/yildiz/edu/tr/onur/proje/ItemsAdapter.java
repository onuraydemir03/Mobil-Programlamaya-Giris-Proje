package yildiz.edu.tr.onur.proje;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    ArrayList<Item> items = new ArrayList<>();
    Context context;
    int flag;
    LayoutInflater layoutInflater;
    public ItemsAdapter(ArrayList<Item> items, int flag , Context context){
        this.items = items;
        this.flag = flag;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View rowView = layoutInflater.inflate(R.layout.items_row, parent, false);
        return new ItemsAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position){
        holder.kindText.setText(items.get(position).getKind());
        holder.colorText.setText(items.get(position).getColor());
        holder.patternText.setText(items.get(position).getPattern());
        holder.dateText.setText(items.get(position).getBuyingDate());
        holder.priceText.setText(String.valueOf(items.get(position).getPrice()));
        //Image setle..
        File imageFile = new File(context.getFilesDir()  +  File.separator  + "Items" + File.separator + items.get(position).getImgName());
        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        holder.image.setImageBitmap(imageBitmap);
        int id = items.get(position).getId();
        if (flag == 0){
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Delete Item")
                            .setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ItemsDB db  = new ItemsDB(context);
                                    items.remove(position);
                                    notifyDataSetChanged();
                                    db.deleteAnItem(id);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditItemActivity.class);
                    intent.putExtra("ID",id);
                    context.startActivity(intent);
                }
            });
        }
        else{
            holder.deleteButton.setVisibility(View.GONE);
            holder.editButton.setVisibility(View.GONE);
            holder.itemCard.setClickable(true);
            ItemsDB db = new ItemsDB(context);
            Item item = db.getAnItemWID(items.get(position).getId());

            holder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DrawerActivity.class);
                    intent.putExtra("itemID", item.getId());
                    intent.putExtra("drawerid", flag);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView kindText, colorText, patternText, dateText, priceText;
        ImageView image;
        ImageButton editButton, deleteButton;
        CardView itemCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.kindText = itemView.findViewById(R.id.itemRowKindOfItemText);
            this.colorText = itemView.findViewById(R.id.itemRowColorOfItemText);
            this.patternText = itemView.findViewById(R.id.itemRowPatternOfItemText);
            this.dateText = itemView.findViewById(R.id.itemRowBuyDateOfItemText);
            this.priceText = itemView.findViewById(R.id.itemRowPriceOfItemText);
            this.image = itemView.findViewById(R.id.itemRowImage);
            this.editButton = itemView.findViewById(R.id.itemsRowEditButton);
            this.deleteButton = itemView.findViewById(R.id.itemsRowDeleteButton);
            this.itemCard = itemView.findViewById(R.id.itemCard);

        }
    }
}
