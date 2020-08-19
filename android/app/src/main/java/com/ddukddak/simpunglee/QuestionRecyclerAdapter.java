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

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionViewHolder> {

    Context mContext;
    List<selfDiagnosisQuestionVO> questionVOList = new ArrayList<>();
    selfDiagnosisQuestionVO questionData;
    RecyclerView recyclerView;

    //instance of interface created
    private ListItemClickListener myOnClickListener;

    private int[] state;

    //create a interface which helps to communicates with your main activity
    public interface ListItemClickListener
    {
        void onListItemClick(int clickItemIndex,int pointSelected);
    }


    public QuestionRecyclerAdapter(Context context, ListItemClickListener myOnClickListener, List<selfDiagnosisQuestionVO> questionVOList) {
        this.mContext = context;
        this.questionVOList = questionVOList;
        this.myOnClickListener = myOnClickListener;
        this.state = new int[questionVOList.size()];
        Arrays.fill(this.state, -1);
    }

    @NonNull
    @Override
    public QuestionRecyclerAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View view = inflate.inflate(R.layout.item_question, parent, false);

        QuestionRecyclerAdapter.QuestionViewHolder vh = new QuestionRecyclerAdapter.QuestionViewHolder(view);
        return vh;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView questionText;
        public RadioGroup answerGrp;
        public LiquidRadioButton score4, score3, score2, score1;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.questionText = itemView.findViewById(R.id.questionText);

            this.answerGrp = itemView.findViewById(R.id.testAnswerGrp);

            this.score4 = itemView.findViewById(R.id.scoreRadio4);
            this.score3 = itemView.findViewById(R.id.scoreRadio3);
            this.score2 = itemView.findViewById(R.id.scoreRadio2);
            this.score1 = itemView.findViewById(R.id.scoreRadio1);

            score4.setOnClickListener(this);
            score3.setOnClickListener(this);
            score2.setOnClickListener(this);
            score1.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
                int clickedCardPosition = getAdapterPosition();
                System.out.println(clickedCardPosition);
                if(score4.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition,4);
                if(score3.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition,3);
                if(score2.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition,2);
                if(score1.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition,1);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        // question 목록 다 저장된거 데이터 하나씩 갖고와서
        questionData = questionVOList.get(position);

        // recycler view의 item view에 붙이기
        holder.questionText.setText(questionData.getQuestion());
        holder.setIsRecyclable(false);

        switch (questionData.getSelectPoint()) {
            case 4:
                holder.score4.setChecked(true);
                break;
            case 3:
                holder.score3.setChecked(true);
                break;
            case 2:
                holder.score2.setChecked(true);
                break;
            case 1:
                holder.score1.setChecked(true);
                break;
            default:
                holder.answerGrp.clearCheck();
        }
    }
    @Override

    public int getItemCount() {
        return questionVOList.size();
    }


    public int getPointSum(){
        int sum =0;
        for(int i=0; i<getItemCount(); i++){
            if(questionVOList.get(i).getSelectPoint()<0){
                return -1;
            }
            sum = sum + questionVOList.get(i).getSelectPoint();
        }
        return sum;
    }
}
