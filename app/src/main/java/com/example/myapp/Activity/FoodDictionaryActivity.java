package com.example.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.FoodData;
import com.example.myapp.R;
import com.example.myapp.adapter.ItemAdapter;

import java.util.ArrayList;


//////google 복붙
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

//import com.nurisoft.recyclerview.adapters.ItemAdapter;
//import com.nurisoft.recyclerview.models.ItemModel;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import android.text.TextWatcher;


public class FoodDictionaryActivity extends BasicActivity implements ItemAdapter.onItemListener {
    Button btn_home,btn_setting, btn_community, btn_menu;
    private ItemAdapter adapter;
    private List<FoodData> itemList = new ArrayList<>(), filteredList=new ArrayList<>();
    Button buttonInsert;
    EditText searchET;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dictionary);
        setUpRecyclerView();

        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_food);
        searchET = (EditText)findViewById(R.id.searchFood);
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

        btn_menu = findViewById(R.id.btn_menu);
        buttonInsert=findViewById(R.id.btn_add_food);
        buttonInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodDictionaryActivity.this);
                View view = getLayoutInflater().from(FoodDictionaryActivity.this).inflate(R.layout.add_food, null,false);
                builder.setView(view);
                final Button ButtonSubmit=(Button)view.findViewById(R.id.btn_insert);
                final Button ButtonBack=(Button)view.findViewById(R.id.btn_back);
                final EditText editFood = (EditText) view.findViewById(R.id.d_food);
                final EditText editUnit = (EditText) view.findViewById(R.id.d_unit);
                final EditText editCal = (EditText) view.findViewById(R.id.d_cal);

                final EditText editCarbo = (EditText) view.findViewById(R.id.d_carbo);
                final EditText editProtein = (EditText) view.findViewById(R.id.d_protein);
                final EditText editFat = (EditText) view.findViewById(R.id.d_fat);
                final EditText editSugars = (EditText) view.findViewById(R.id.d_sugars);
                final EditText editSodium = (EditText) view.findViewById(R.id.d_sodium);



                final AlertDialog dialog = builder.create();
                ButtonSubmit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String strFood = editFood.getText().toString();
                        String strUnit = editUnit.getText().toString();
                        String strCal = editCal.getText().toString();
                        String strCarbo = editCarbo.getText().toString();
                        String strProtein = editProtein.getText().toString();
                        String strFat = editFat.getText().toString();
                        String strSugars = editSugars.getText().toString();
                        String strSodium = editSodium.getText().toString();

                        FoodData dictionary = new FoodData(strFood,strCal,strUnit, strCarbo, strProtein, strFat, strSugars, strSodium);
                        itemList.add(dictionary);
                        //adapter.notifyItemInserted(0);
                        //adapter.notifyDataSetChanged();
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
                startActivity(new Intent(FoodDictionaryActivity.this, SettingActivity.class));
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDictionaryActivity.this, MainActivity.class));
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDictionaryActivity.this, MenuActivity.class));
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodDictionaryActivity.this, CommunityActivity.class));
            }
        });

    }

    /****************************************************
     리사이클러뷰, 어댑터 셋팅
     ***************************************************/
    private void setUpRecyclerView() {
        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recycler_view_food);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //adapter
        itemList = new ArrayList<>(); //샘플테이터
        fillData();
        adapter = new ItemAdapter(itemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); //밑줄
        recyclerView.addItemDecoration(dividerItemDecoration);

        //데이터셋변경시
        adapter.dataSetChanged(itemList);

        //어댑터의 리스너 호출
        adapter.setOnClickListener(this);
    }

    private void fillData() {
         //샘플테이터
        itemList.add(new FoodData("사과", "52", "100g", "13.81", "0.29", "0.17", "14.34", "1"));
        itemList.add(new FoodData("바나나", "89", "100g", "22.84", "1.09", "0.33", "14.43", "1"));
        itemList.add(new FoodData("닭가슴살", "109","100g", "0", "22.98", "1.23", "0", "65"));
        itemList.add(new FoodData("상추", "14", "100g", "2.97", "0.9", "0.14", "0.8", "10"));
        itemList.add(new FoodData("참크래커", "85", "1봉지", "13.8", "1.6", "2.4", "0.5", "138"));
        itemList.add(new FoodData("방울토마토", "18", "100g", "3.92", "0.88", "0.2", "2.63", "5"));
        itemList.add(new FoodData("고등어", "167", "100g", "0", "19.32", "9.36", "0", "78"));
        itemList.add(new FoodData("고등어구이", "190", "100g", "0.35", "17.59", "12.6", "0.09", "411"));
        itemList.add(new FoodData("양배추", "24", "100g", "5.58", "1.44", "0.12", "3.58", "18"));
        itemList.add(new FoodData("오이", "12", "100g", "2.16", "0.59", "0.16", "1.38", "2"));
        itemList.add(new FoodData("삶은 계란", "68", "1개", "0.49", "5.51", "4.65", "0.49", "122"));
        itemList.add(new FoodData("구운계란", "65", "1개", "0.34", "5.57", "4.4", "0.34", "131"));
        itemList.add(new FoodData("계란후라이", "78", "1개", "0.37", "5.42", "5.88", "0.33", "207"));
        itemList.add(new FoodData("군고구마", "123", "100g", "28.38", "1.44", "0.49", "3.63", "48"));
        itemList.add(new FoodData("밤고구마", "128", "100g", "30.28", "1.39", "0.23", "3.19", "18"));
        itemList.add(new FoodData("호박고구마", "155", "100g", "36.78", "1.41", "0.27", "2.2", "16"));
        itemList.add(new FoodData("두부", "79", "100g", "2.81", "8.1", "4.21", "1.01", "29"));
        itemList.add(new FoodData("팽이버섯", "42", "100g", "6.96", "2.7", "0.41", "0.22", "3"));
        itemList.add(new FoodData("아이스 아메리카노", "20", "1잔", "1.87", "0.27", "1.29", "1.7", "5"));
        itemList.add(new FoodData("코카콜라", "108", "1캔(250ml)", "27", "0", "0", "27", "8"));

    }

    public void searchFilter(String searchText) {
        filteredList.clear();

        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getFood().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(itemList.get(i));
            }
            adapter.setItems((ArrayList<FoodData>) filteredList);
        }

        adapter.filterList((ArrayList<FoodData>) filteredList);
    }



    /****************************************************
     리사이클러뷰 클릭이벤트 인터페이스 구현
     ***************************************************/
    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "" +itemList.get(position).getFood(), Toast.LENGTH_SHORT).show();
    }
}