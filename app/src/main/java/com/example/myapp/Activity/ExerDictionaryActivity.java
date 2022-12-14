package com.example.myapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.ExerData;
import com.example.myapp.FoodData;
import com.example.myapp.R;
import com.example.myapp.adapter.ExItemAdapter;
import com.example.myapp.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExerDictionaryActivity  extends  BasicActivity implements ExItemAdapter.onItemListener{
    Button btn_home,btn_setting, btn_community, btn_menu;
    private ExItemAdapter adapter;
    private List<ExerData> itemList = new ArrayList<>(), filteredList=new ArrayList<>();
    Button buttonInsert;
    EditText searchET;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_exer_dictionary);
        setUpRecyclerView();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_ex);
        searchET = (EditText)findViewById(R.id.searchEx);
        //editText.addTextChangedListener(this);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String searchText = searchET.getText().toString();
                searchFilter(searchText);

            }
        });

        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
        btn_menu = findViewById(R.id.btn_menu);
        buttonInsert=findViewById(R.id.btn_add_ex);
        buttonInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExerDictionaryActivity.this);
                View view = getLayoutInflater().from(ExerDictionaryActivity.this).inflate(R.layout.add_exer, null,false);
                builder.setView(view);
                final Button ButtonSubmit=(Button)view.findViewById(R.id.btn_insert);
                final Button ButtonBack=(Button)view.findViewById(R.id.btn_back);
                final EditText editExer = (EditText) view.findViewById(R.id.d_exer);
                final EditText editUnit = (EditText) view.findViewById(R.id.d_unit);
                final EditText editCal = (EditText) view.findViewById(R.id.d_cal);
                final AlertDialog dialog = builder.create();
                ButtonSubmit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String strExer = editExer.getText().toString();
                        String strUnit = editUnit.getText().toString();
                        String strCal = editCal.getText().toString();
                        ExerData dictionary = new ExerData(strExer,strCal,strUnit);
                        itemList.add(dictionary);
                        //adapter.notifyItemInserted(0);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                ButtonBack.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                dialog.show();
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(ExerDictionaryActivity.this, SettingActivity.class));
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExerDictionaryActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExerDictionaryActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExerDictionaryActivity.this, CommunityActivity.class));
            }
        });


    }
    private void setUpRecyclerView() {
        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recycler_view_ex);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //adapter
        itemList = new ArrayList<>(); //???????????????
        fillData();
        adapter = new ExItemAdapter(itemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); //??????
        recyclerView.addItemDecoration(dividerItemDecoration);

        //?????????????????????
        adapter.dataSetChanged(itemList);

        //???????????? ????????? ??????
        adapter.setOnClickListener(this);
    }

    private void fillData() {
        //???????????????
        itemList.add(new ExerData("??????", "360", "30"));
        itemList.add(new ExerData("?????? ??????", "210", "30"));
        itemList.add(new ExerData("??????", "240","30"));
        itemList.add(new ExerData("?????????", "240", "30"));
        itemList.add(new ExerData("?????????", "360", "30"));
        itemList.add(new ExerData("?????????", "142", "30"));
        itemList.add(new ExerData("?????? ?????????", "135", "30"));
        itemList.add(new ExerData("????????? ??????", "120", "30"));
        itemList.add(new ExerData("????????????", "120", "30"));
        itemList.add(new ExerData("?????? ??????", "430", "30"));
        itemList.add(new ExerData("??????", "370", "30"));
        itemList.add(new ExerData("??????", "79", "30"));
        itemList.add(new ExerData("????????????", "189", "30"));
        itemList.add(new ExerData("????????????", "132", "30"));
        itemList.add(new ExerData("??????", "182", "30"));
        itemList.add(new ExerData("??????", "104", "30"));

    }

    public void searchFilter(String searchText) {
        filteredList.clear();

        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getExer().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(itemList.get(i));
            }
            adapter.setItems((ArrayList<ExerData>) filteredList);
        }

        adapter.filterList((ArrayList<ExerData>) filteredList);
    }



    /****************************************************
     ?????????????????? ??????????????? ??????????????? ??????
     ***************************************************/
    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "" +itemList.get(position).getExer(), Toast.LENGTH_SHORT).show();
    }
}
