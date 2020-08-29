package com.ddukddak.simpunglee;

import android.content.Context;
import android.graphics.Color;
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
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View view = inflate.inflate(R.layout.item_category, parent, false);

        CategoryViewHolder vh = new CategoryViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // question 목록 다 저장된거 데이터 하나씩 갖고와서
        categoryData = categoryVOList.get(position);

        String gradeLevel = categoryData.getGrade();

        // recycler view의 item view에 붙이기
        holder.categoryName.setText(categoryData.getCategoryName());
        holder.categoryGrade.setText(""+gradeLevel);
        holder.categoryScore.setText("점수:  "+categoryData.getScore());

        if(gradeLevel.equals("심각")) holder.categoryGrade.setTextColor(Color.parseColor("#C00A32"));
        else if(gradeLevel.equals("주의")) holder.categoryGrade.setTextColor(Color.parseColor("#ffd633"));
        else holder.categoryGrade.setTextColor(Color.parseColor("#009933"));

    }
    @Override
    public int getItemCount() {
        return categoryVOList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView categoryName;
        public TextView categoryScore;
        public TextView categoryGrade;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.categoryName = (TextView)itemView.findViewById(R.id.categoryName);
            this.categoryScore = (TextView)itemView.findViewById(R.id.categoryScore);
            this.categoryGrade = (TextView)itemView.findViewById(R.id.categoryGrade);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
