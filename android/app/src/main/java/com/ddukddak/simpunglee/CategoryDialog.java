package com.ddukddak.simpunglee;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryDialog extends Dialog implements View.OnClickListener {
    public CategoryDialog(@NonNull Context context) {
        super(context);
    }

    public Activity activity;
    public Dialog dialog;
    public ImageButton closeBtn;
    TextView textview_alterdialog_title;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter adapter;

    CategoryDialog(Activity a, RecyclerView.Adapter adapter) {
        super(a);
        this.activity = a;
        this.adapter = adapter;
        setupLayout();
    }

    private void setupLayout() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_category);
        closeBtn = (ImageButton) findViewById(R.id.closeBtn);
        textview_alterdialog_title = findViewById(R.id.textview_alterdialog_title);
        recyclerView = findViewById(R.id.recyclerViewCategory);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
