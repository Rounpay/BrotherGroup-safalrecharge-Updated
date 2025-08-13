package com.solution.brothergroup.Aeps.UI;

import android.os.Bundle;

import com.roundpay.emoneylib.Object.MiniStatements;
import com.solution.brothergroup.Aeps.Adapter.MiniStatementAdapter;
import com.solution.brothergroup.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MiniStatementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_statement);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<MiniStatements> miniStatements= (ArrayList<MiniStatements>) getIntent().getSerializableExtra("MINI_STATEMENT");
        recyclerView.setAdapter(new MiniStatementAdapter(miniStatements,this));
    }
}