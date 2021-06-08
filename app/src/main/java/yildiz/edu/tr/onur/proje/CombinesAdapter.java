package yildiz.edu.tr.onur.proje;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class CombinesAdapter extends RecyclerView.Adapter<CombinesAdapter.ViewHolder>{
    ArrayList<Combine> combines = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    File src;
    int flag;
    public CombinesAdapter(ArrayList<Combine> combines, int flag, Context context){
        this.combines = combines;
        this.context = context;
        this.flag = flag;
    }

    @Override
    public CombinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View rowView = layoutInflater.inflate(R.layout.combines_row, parent, false);
        return new CombinesAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull CombinesAdapter.ViewHolder holder, int position){
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
        if(flag == 0){
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Delete Combine")
                            .setMessage("Are you sure you want to delete this combine?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    CombinesDB db  = new CombinesDB(context);
                                    combines.remove(position);
                                    notifyDataSetChanged();
                                    db.deleteACombine(id);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    imageUris.add(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(context.getFilesDir() + File.separator + "Items"+ File.separator + hat.getImgName())));
                    imageUris.add(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(context.getFilesDir() + File.separator + "Items"+ File.separator + glasses.getImgName())));
                    imageUris.add(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(context.getFilesDir() + File.separator + "Items"+ File.separator + upper.getImgName())));
                    imageUris.add(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(context.getFilesDir() + File.separator + "Items"+ File.separator + lower.getImgName())));
                    imageUris.add(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(context.getFilesDir() + File.separator + "Items"+ File.separator + shoe.getImgName())));

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, \n\t I am sending my combine to you. Please give me your recommendations..\n\n Onur Aydemir");
                    shareIntent.setType("image/*");
                    shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(Intent.createChooser(shareIntent, "Share images to.."));
                }
            });
        }else{
            holder.deleteButton.setVisibility(View.GONE);
            holder.shareButton.setVisibility(View.GONE);
            holder.card.setClickable(true);

            CombinesDB db = new CombinesDB(context);
            Combine combine = db.getACombineWID(combines.get(position).getId());

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddNewEventActivity.class);
                    intent.putExtra("combineid", combine.getId());
                    intent.putExtra("eventid", flag);
                    intent.putExtra("flag", 1);
                    context.startActivity(intent);
                }
            });
        }

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
