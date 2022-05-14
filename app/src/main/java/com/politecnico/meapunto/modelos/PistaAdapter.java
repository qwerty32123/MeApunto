package com.politecnico.meapunto.modelos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.politecnico.meapunto.R;

import java.util.List;


import android.content.Context;



import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.politecnico.meapunto.R;


// como implementar el click en un item del recycler view https://youtu.be/69C1ljfDvl0
import java.util.List;

    public class PistaAdapter extends RecyclerView.Adapter<PistaAdapter.ProductViewHolder> {
        private Context mCtx;
        private OnNoteListener mOnNoteListener;
        private List<Pista> productList;

        public PistaAdapter(Context mCtx, List<Pista> productList, PistaAdapter.OnNoteListener onNoteListener) {
            this.mCtx = mCtx;
            this.productList = productList;
            this.mOnNoteListener=onNoteListener;
        }

        @Override
        public PistaAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.pista_list, null);
            return new ProductViewHolder(view,mOnNoteListener);
        }

        @Override
        public void onBindViewHolder(PistaAdapter.ProductViewHolder holder, int position) {
            Pista product = productList.get(position);

            //loading the image
//        Glide.with(mCtx)
//                .load(product.getImage())
//                .into(holder.imageView);

            holder.textViewTitle.setText(product.getNombre());
            // holder.textViewShortDesc.setText(String.valueOf(product.getId()));

        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView textViewTitle;
            PistaAdapter.OnNoteListener onNoteListener;

            public ProductViewHolder(View itemView, PistaAdapter.OnNoteListener onNoteListener) {
                super(itemView);

                textViewTitle = itemView.findViewById(R.id.pista_text_view);
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


