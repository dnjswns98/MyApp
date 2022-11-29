package com.example.myapp.BulletinBoard;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapp.Activity.BasicActivity;
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

public class ReviewActivity extends BasicActivity {
    private static final String TAG = "ReviewActivity";
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private BullentinBoardAdapter bullentinBoardAdapter;
    private ArrayList<PostWriteinfo> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        setToolbarTitle("챌린지 후기 게시판");

        FirebaseStorage storage = FirebaseStorage.getInstance();

        postList = new ArrayList<>();
        bullentinBoardAdapter = new BullentinBoardAdapter(ReviewActivity.this, postList);
        bullentinBoardAdapter.setOnPostListener(onPostListener);

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));
        recyclerView.setAdapter(bullentinBoardAdapter);

        FloatingActionButton btn_plus = findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewActivity.this, ReviewWriteActivity.class);
                startActivity(intent);

            }
        });
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        postUpdate();
    }

    private void postUpdate() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts");
            collectionReference.orderBy("createAt", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            postList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                postList.add(new PostWriteinfo(
                                        document.getData().get("title").toString(),
                                        (ArrayList<String>) document.getData().get("contents"),
                                        (ArrayList<String>) document.getData().get("formats"),
                                        document.getData().get("publisher").toString(),
                                        new Date(document.getDate("createAt").getTime()),
                                        document.getId()));
                            }
                            bullentinBoardAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }
    }

}