package com.ddukddak.simpunglee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.omidh.liquidradiobutton.LiquidRadioButton;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    Context mContext;
    List<SelfDiagnosisCategoryVO> categoryVOList = new ArrayList<>();
    SelfDiagnosisCategoryVO categoryData;

    public CategoryRecyclerAdapter(Context context, List<SelfDiagnosisCategoryVO> categoryVOList) {
        this.mContext = context;
        this.categoryVOList = categoryVOList;
    }

    @NonNull
    @Override
    public CategoryRecyclerAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View view = inflate.inflate(R.layout.item_category, parent, false);

        CategoryRecyclerAdapter.CategoryViewHolder vh = new CategoryRecyclerAdapter.CategoryViewHolder(view);
        return vh;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView categoryName;
        public TextView categoryScore;
        public TextView categoryGrade;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.categoryName = itemView.findViewById(R.id.categoryName);
            this.categoryScore = itemView.findViewById(R.id.categoryScore);
            this.categoryGrade = itemView.findViewById(R.id.categoryGrade);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // question 목록 다 저장된거 데이터 하나씩 갖고와서
        categoryData = categoryVOList.get(position);

        // recycler view의 item view에 붙이기
        holder.categoryName.setText(categoryData.getCategoryName());
        holder.categoryGrade.setText(categoryData.getGrade());
        holder.categoryScore.setText(categoryData.getScore());
    }
    @Override

    public int getItemCount() {
        return categoryVOList.size();
    }

}
