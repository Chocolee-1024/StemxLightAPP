package com.example.light.ui.mostPost;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.light.R;
import com.example.light.base.MorseCodeTool;

public class MoresPostAdapter extends RecyclerView.Adapter<MoresPostAdapter.ViewHolder> implements MorsePostContract.adapter {
    private MorsePostContract.view view;
    private  String[][] abbreviationMorseCode  = new MorseCodeTool().getAbbreviationMorseCode();
    public MoresPostAdapter(MorsePostContract.view view) {
        this.view = view;
    }

    @NonNull
    @Override
    public MoresPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.abbreviation_morse_code_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoresPostAdapter.ViewHolder holder, int position) {
        holder.code_textview.setText(abbreviationMorseCode[position][0]);
        holder.mean_textview.setText(abbreviationMorseCode[position][1]);
        holder.abbreviation_linearLayout.setOnClickListener(abbreviationClicked(position));
    }


    @Override
    public int getItemCount() {
        return abbreviationMorseCode.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView code_textview;
        private TextView mean_textview;
        private LinearLayout abbreviation_linearLayout;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             code_textview = itemView.findViewById(R.id.abbreviation_code);
             mean_textview = itemView.findViewById(R.id.abbreviation_mean);
             abbreviation_linearLayout = itemView.findViewById(R.id.abbreviation_item_linearLayout);
         }
    }
   private View.OnClickListener abbreviationClicked(int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getAbbreviationMorseCode(abbreviationMorseCode[position][0]);
            }
        };
   }
}