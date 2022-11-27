package com.example.myapp.Activity;

import android.content.Intent;
import android.os.Bundle;
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

public class FoodDictionaryActivity extends BasicActivity implements ItemAdapter.onItemListener {
    Button btn_home,btn_setting, btn_community, btn_menu;
    private ItemAdapter adapter;
    private List<FoodData> itemList;
    Button buttonInsert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dictionary);
        btn_setting = findViewById(R.id.btn_setting);
        btn_home = findViewById(R.id.btn_home);
        btn_community = findViewById(R.id.btn_community);
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
                final AlertDialog dialog = builder.create();
                ButtonSubmit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String strFood = editFood.getText().toString();
                        String strUnit = editUnit.getText().toString();
                        String strCal = editCal.getText().toString();
                        FoodData dictionary = new FoodData(strFood,strCal,strUnit);
                        itemList.add(0,dictionary);
                        adapter.notifyItemInserted(0);
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

        setUpRecyclerView();
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
        //        itemList = new ArrayList<>(); //샘플테이터
        fillData();
        adapter = new ItemAdapter(itemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); //밑줄
        recyclerView.addItemDecoration(dividerItemDecoration);

        //데이터셋변경시
        //adapter.dataSetChanged(exampleList);

        //어댑터의 리스너 호출
        adapter.setOnClickListener(this);
    }

    private void fillData() {
        itemList = new ArrayList<>(); //샘플테이터
        itemList.add(new FoodData("사과", "57", "100g"));
        itemList.add(new FoodData("바나나", "93", "100g"));
        itemList.add(new FoodData("닭가슴살", "109","100g"));
        itemList.add(new FoodData("상추", "18", "100g"));
        itemList.add(new FoodData("참크래커", "85", "1봉지"));
        itemList.add(new FoodData("방울토마토", "16", "100g"));
        itemList.add(new FoodData("고등어", "183", "100g"));
        itemList.add(new FoodData("양배추", "31", "100g"));
        itemList.add(new FoodData("오이", "9", "100g"));
        itemList.add(new FoodData("달걀", "74", "50g"));
        itemList.add(new FoodData("군고구마", "123", "100g"));
        itemList.add(new FoodData("두부", "95", "100g"));
        itemList.add(new FoodData("구운계란", "55", "50g"));
        itemList.add(new FoodData("팽이버섯", "29", "100g"));
        itemList.add(new FoodData("아이스 아메리카노", "10", "1잔"));
        itemList.add(new FoodData("코카콜라", "212", "500ml"));
    }

    /****************************************************
     onCreateOptionsMenu SearchView  기능구현
     ***************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("검색어를 입력하세요.");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(FoodDictionaryActivity.this, "[검색버튼클릭] 검색어 = "+query, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /****************************************************
     리사이클러뷰 클릭이벤트 인터페이스 구현
     ***************************************************/
    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "" +position, Toast.LENGTH_SHORT).show();
    }
}