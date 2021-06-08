package yildiz.edu.tr.onur.proje;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder>{
    ArrayList<Item> items = new ArrayList<>();
    Context context;
    int drawerID;
    int requestCode;
    LayoutInflater layoutInflater;
    Integer [] ids;
    public DrawerAdapter(ArrayList<Item> items, int drawerID, int requestCode, Integer[] ids, Context context){
        this.items = items;
        this.context = context;
        this.drawerID = drawerID;
        this.requestCode = requestCode;
        this.ids = ids;
    }

    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View rowView = layoutInflater.inflate(R.layout.items_row, parent, false);
        return new DrawerAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerAdapter.ViewHolder holder, int position){
        if(requestCode == 0){
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

            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Delete Item")
                            .setMessage("Are you sure you want to delete this item from this drawer?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    RelationDB db  = new RelationDB(context);
                                    items.remove(position);
                                    notifyDataSetChanged();
                                    db.deleteAnItemFromDrawer(drawerID, id);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }else{
            holder.deleteButton.setVisibility(View.GONE);
            holder.editButton.setVisibility(View.GONE);
            holder.itemCard.setClickable(true);
            switch (requestCode){
                case 1001:
                    if(items.get(position).getKind().equals("Hat")){
                        holder.kindText.setText(items.get(position).getKind());
                        holder.colorText.setText(items.get(position).getColor());
                        holder.patternText.setText(items.get(position).getPattern());
                        holder.dateText.setText(items.get(position).getBuyingDate());
                        holder.priceText.setText(String.valueOf(items.get(position).getPrice()));
                        File imageFile = new File(context.getFilesDir()  +  File.separator  + "Items" + File.separator + items.get(position).getImgName());
                        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        holder.image.setImageBitmap(imageBitmap);
                        int id = items.get(position).getId();
                    }
                    else{
                        holder.itemCard.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    }
                    break;
                case 1002:
                    if(items.get(position).getKind().equals("Glasses")){
                        holder.kindText.setText(items.get(position).getKind());
                        holder.colorText.setText(items.get(position).getColor());
                        holder.patternText.setText(items.get(position).getPattern());
                        holder.dateText.setText(items.get(position).getBuyingDate());
                        holder.priceText.setText(String.valueOf(items.get(position).getPrice()));
                        File imageFile = new File(context.getFilesDir()  +  File.separator  + "Items" + File.separator + items.get(position).getImgName());
                        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        holder.image.setImageBitmap(imageBitmap);
                        int id = items.get(position).getId();
                    }
                    else{
                        holder.itemCard.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    }
                    break;
                case 1003:
                    if(items.get(position).getKind().equals("T-Shirt")){
                        holder.kindText.setText(items.get(position).getKind());
                        holder.colorText.setText(items.get(position).getColor());
                        holder.patternText.setText(items.get(position).getPattern());
                        holder.dateText.setText(items.get(position).getBuyingDate());
                        holder.priceText.setText(String.valueOf(items.get(position).getPrice()));
                        File imageFile = new File(context.getFilesDir()  +  File.separator  + "Items" + File.separator + items.get(position).getImgName());
                        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        holder.image.setImageBitmap(imageBitmap);
                        int id = items.get(position).getId();
                    }
                    else{
                        holder.itemCard.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    }
                    break;
                case 1004:
                    if(items.get(position).getKind().equals("Trousers")){
                        holder.kindText.setText(items.get(position).getKind());
                        holder.colorText.setText(items.get(position).getColor());
                        holder.patternText.setText(items.get(position).getPattern());
                        holder.dateText.setText(items.get(position).getBuyingDate());
                        holder.priceText.setText(String.valueOf(items.get(position).getPrice()));
                        File imageFile = new File(context.getFilesDir()  +  File.separator  + "Items" + File.separator + items.get(position).getImgName());
                        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        holder.image.setImageBitmap(imageBitmap);
                        int id = items.get(position).getId();
                    }
                    else{
                        holder.itemCard.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    }
                    break;
                case 1005:
                    if(items.get(position).getKind().equals("Shoe")){
                        holder.kindText.setText(items.get(position).getKind());
                        holder.colorText.setText(items.get(position).getColor());
                        holder.patternText.setText(items.get(position).getPattern());
                        holder.dateText.setText(items.get(position).getBuyingDate());
                        holder.priceText.setText(String.valueOf(items.get(position).getPrice()));
                        File imageFile = new File(context.getFilesDir()  +  File.separator  + "Items" + File.separator + items.get(position).getImgName());
                        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        holder.image.setImageBitmap(imageBitmap);
                        int id = items.get(position).getId();
                    }
                    else{
                        holder.itemCard.setVisibility(View.GONE);
                        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    }
                    break;
            }

            holder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String kind = items.get(position).getKind();
                    Intent returnIntent;
                    switch (kind){
                        case "Hat":
                            returnIntent = new Intent(context, CabinActivity.class);
                            returnIntent.putExtra("hatID",items.get(position).getId());
                            returnIntent.putExtra("glassID", ids[1]);
                            returnIntent.putExtra("upperID", ids[2]);
                            returnIntent.putExtra("lowerID", ids[3]);
                            returnIntent.putExtra("shoeID", ids[4]);
                            context.startActivity(returnIntent);
                            break;
                        case "Glasses":
                            returnIntent = new Intent(context, CabinActivity.class);
                            returnIntent.putExtra("hatID",ids[0]);
                            returnIntent.putExtra("glassID", items.get(position).getId());
                            returnIntent.putExtra("upperID", ids[2]);
                            returnIntent.putExtra("lowerID", ids[3]);
                            returnIntent.putExtra("shoeID", ids[4]);
                            context.startActivity(returnIntent);
                            break;
                        case "T-Shirt":
                            returnIntent = new Intent(context, CabinActivity.class);
                            returnIntent.putExtra("hatID",ids[0]);
                            returnIntent.putExtra("glassID", ids[1]);
                            returnIntent.putExtra("upperID", items.get(position).getId());
                            returnIntent.putExtra("lowerID", ids[3]);
                            returnIntent.putExtra("shoeID", ids[4]);
                            context.startActivity(returnIntent);
                            break;
                        case "Trousers":
                            returnIntent = new Intent(context, CabinActivity.class);
                            returnIntent.putExtra("hatID",ids[0]);
                            returnIntent.putExtra("glassID", ids[1]);
                            returnIntent.putExtra("upperID", ids[2]);
                            returnIntent.putExtra("lowerID", items.get(position).getId());
                            returnIntent.putExtra("shoeID", ids[4]);
                            context.startActivity(returnIntent);
                            break;
                        case "Shoe":
                            returnIntent = new Intent(context, CabinActivity.class);
                            returnIntent.putExtra("hatID",ids[0]);
                            returnIntent.putExtra("glassID", ids[1]);
                            returnIntent.putExtra("upperID", ids[2]);
                            returnIntent.putExtra("lowerID", ids[3]);
                            returnIntent.putExtra("shoeID", items.get(position).getId());
                            context.startActivity(returnIntent);
                            break;
                    }


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
