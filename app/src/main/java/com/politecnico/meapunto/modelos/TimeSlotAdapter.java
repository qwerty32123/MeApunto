package com.politecnico.meapunto.modelos;

import android.content.Context;



import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.politecnico.meapunto.R;


// como implementar el click en un item del recycler view https://youtu.be/69C1ljfDvl0
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ProductViewHolder> {
    private Context mCtx;
    private OnNoteListener mOnNoteListener;
    private List<TimeSlot> productList;

    public TimeSlotAdapter(Context mCtx, List<TimeSlot> productList,OnNoteListener onNoteListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mOnNoteListener=onNoteListener;
    }


    public List<TimeSlot> getProductList() {
        return productList;
    }

    public void setProductList(List<TimeSlot> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.timeslot_list, null);
        return new ProductViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        TimeSlot product = productList.get(position);

        //loading the image
//        Glide.with(mCtx)
//                .load(product.getImage())
//                .into(holder.imageView);

        holder.textViewTitle.setText(product.getDescription());
       // holder.textViewShortDesc.setText(String.valueOf(product.getId()));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView textViewTitle,textViewShortDesc;
        OnNoteListener onNoteListener;

        public ProductViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTitle.setOnClickListener(this);
            this.onNoteListener=onNoteListener;

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getBindingAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);

    }

}
