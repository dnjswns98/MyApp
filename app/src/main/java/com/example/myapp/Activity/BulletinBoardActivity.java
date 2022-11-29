package com.example.myapp.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapp.R;
import com.example.myapp.PostWriteinfo;
import com.example.myapp.adapter.BullentinBoardAdapter;
import com.example.myapp.listener.OnPostListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import java.util.Date;

public class BulletinBoardActivity extends BasicActivity {
    private static final String TAG = "BulletinBoardActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private BullentinBoardAdapter bullentinBoardAdapter;
    private ArrayList<PostWriteinfo> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);

        setToolbarTitle("게시판");

        FirebaseStorage storage = FirebaseStorage.getInstance();

        //객체 생성
        postList = new ArrayList<>();
        bullentinBoardAdapter = new BullentinBoardAdapter(BulletinBoardActivity.this, postList);
        bullentinBoardAdapter.setOnPostListener(onPostListener);

        //RecyclerView로 동적 목록 생성
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //LinearLayoutManager사용하여 1차원으로 목록 정렬
        recyclerView.setLayoutManager(new LinearLayoutManager(BulletinBoardActivity.this));
        //Adapter는 필요에 따라 ViewHolder 객체를 만들고 이러한 뷰에 데이터를 설정
        recyclerView.setAdapter(bullentinBoardAdapter);

        //+버튼 클릭시 동작
        FloatingActionButton btn_plus = findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BulletinBoardActivity.this, BulletinBoardWriteActivity.class);
                startActivity(intent);
            }
        });
    }

    //interface 구현
    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete() {
            postUpdate();
            Log.e("로그", "삭제 성공");
        }

        @Override
        public void onModify() {
            Log.e("로그", "수정 성공");
        }
    };

    //사용자와 상호작용 하는 단계, 작성된 포스트 보여주기
    @Override
    protected void onResume() {
        super.onResume();
        postUpdate();
    }

    private void postUpdate() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseUser != null) {
            ////CollectionReference는 파이어스토어의 컬렉션을 참조하는 객체
            CollectionReference collectionReference = firebaseFirestore.collection("posts");
            //Query로 orderBy로 지정된 것을 기본값으로 사용하여 서버 값 가져오기
            collectionReference.orderBy("createAt", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener(task -> {
                        //task가 성공적일 때 (서버로부터 값을 잘 가져왔다면)
                        if (task.isSuccessful()) {
                            //리스트의 모든 아이템을 초기화
                            postList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //리스트에 데이터 넣기
                                postList.add(new PostWriteinfo(
                                        document.getData().get("title").toString(),
                                        (ArrayList<String>) document.getData().get("contents"),
                                        (ArrayList<String>) document.getData().get("formats"),
                                        document.getData().get("publisher").toString(),
                                        new Date(document.getDate("createAt").getTime()),
                                        document.getId()));
                            }
                            //notifyDataSetChanged()를 호출하면 리스트 새로고침 됨
                            bullentinBoardAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }
    }

}