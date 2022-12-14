package com.example.myapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.PostWriteinfo;
import com.example.myapp.view.ContentsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import static com.example.myapp.Util.GALLERY_IMAGE;
import static com.example.myapp.Util.GALLERY_VIDEO;
import static com.example.myapp.Util.INTENT_MEDIA;
import static com.example.myapp.Util.INTENT_PATH;
import static com.example.myapp.Util.isImageFile;
import static com.example.myapp.Util.isStorageUrl;
import static com.example.myapp.Util.isVideoFile;
import static com.example.myapp.Util.showToast;
import static com.example.myapp.Util.storageUrlToName;

public class BulletinBoardWriteActivity extends BasicActivity {
    private static final String TAG = "BulletinBoardWriteActivity";
    private FirebaseUser user;
    private StorageReference storageRef;

    // 클릭했을 때 어떤 게시물을 클릭했는지 게시물 번호를 담기 위한 배열
    private ArrayList<String> pathList = new ArrayList<>();

    private LinearLayout parent;
    private RelativeLayout ButtonBackgroundLayout;
    private ImageView selectedImageView;
    private EditText selectedEditText;
    private EditText contentsEditText;
    private EditText titleEditText;
    private PostWriteinfo writeinfo;
    private int pathCount, successCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_write_board);
        setToolbarTitle("게시글 작성");

        parent = findViewById(R.id.contentsLayout);
        ButtonBackgroundLayout = findViewById(R.id.ButtonBackgroundLayout);
        contentsEditText = findViewById(R.id.contentsEditText);
        titleEditText = findViewById(R.id.titleEditText);

        findViewById(R.id.btn_upload).setOnClickListener(onClickListener);
        findViewById(R.id.btn_image).setOnClickListener(onClickListener);
        findViewById(R.id.btn_video).setOnClickListener(onClickListener);
        findViewById(R.id.imageModify).setOnClickListener(onClickListener);
        findViewById(R.id.videoModify).setOnClickListener(onClickListener);
        findViewById(R.id.Modifydelete).setOnClickListener(onClickListener);

        ButtonBackgroundLayout.setOnClickListener(onClickListener);
        //포커스는 다음 키입력을 받을 뷰가 누구인지를 가리키는 표식
        //Focusable의 속성이 true이어야 하는데, EditText의 경우 별도의 작업이 필요 없이 true
        //EditText를 누르면 키보드가 보여지면서 EditText와 상호작용할 수 있게 되는데,
        //이 때 EditText는 Focus를 갖게 된다.
        contentsEditText.setOnFocusChangeListener(onFocusChangeListener);
        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) { //포커스일때 selectedEditText를 비워준다
                    selectedEditText = null;
                }
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        //클래스 안에 멤버 변수들은 연속된 메모리에 할당되지 않기 때문에 직렬화 객체가 될 수 없다.
        //다른 액티비티에 이러한 데이터 객체를 넘겨주기 위해서는
        //값이 변경될 수 있는 멤버 변수들을 연속된 메모리에 할당된 형태인 직렬화 형태로 변경해야 가능하다.
        //getSerializableExtra로 직렬화 형태로 변경한다다
        writeinfo = (PostWriteinfo)getIntent().getSerializableExtra("writeinfo");

        postInit();
    }


    //데이터 넘겨 받기
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    String path = data.getStringExtra(INTENT_PATH); //"path"
                    pathList.add(path);

                    ContentsView contentsItemView = new ContentsView(this);

                    if(selectedEditText == null){
                        parent.addView(contentsItemView);
                    } else {
                        for(int i = 0; i < parent.getChildCount(); i++){
                            if(parent.getChildAt(i) == selectedEditText.getParent()){
                                parent.addView(contentsItemView, i + 1);
                                break;
                            }
                        }
                    }

                    //이미지 클릭시 ui 보이게
                    contentsItemView.setImage(path);
                    contentsItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ButtonBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v;
                        }
                    });
                    contentsItemView.setOnFocusChangeListener(onFocusChangeListener);

                }
                break;
            case 1:
                if(resultCode == Activity.RESULT_OK){
                    String path = data.getStringExtra(INTENT_PATH);
                    pathList.set(parent.indexOfChild((View)selectedImageView.getParent()) - 1, path);
                    Glide.with(this).load(path).override(1000).into(selectedImageView);
                }
                break;
        }
    }

    View.OnClickListener onClickListener = (v) -> {
        switch (v.getId()){
            case R.id.btn_upload:
                Toast.makeText(BulletinBoardWriteActivity.this, "화면을 터치하지말고 잠시만 기다려주세요.", Toast.LENGTH_LONG).show();
                storageUpload();
                break;
            case R.id.btn_image:
                startActivity(GalleryActivity.class, GALLERY_IMAGE, 0);
                break;
            case R.id.btn_video:
                startActivity(GalleryActivity.class, GALLERY_VIDEO, 0);
                break;
            case R.id.ButtonBackgroundLayout:
                if(ButtonBackgroundLayout.getVisibility() == View.VISIBLE){
                    ButtonBackgroundLayout.setVisibility(View.GONE);
                }
                break;
            case R.id. imageModify:
                startActivity(GalleryActivity.class, GALLERY_IMAGE, 1);
                ButtonBackgroundLayout.setVisibility(View.GONE);
                break;
            case R.id.videoModify:
                startActivity(GalleryActivity.class, GALLERY_VIDEO, 1);
                ButtonBackgroundLayout.setVisibility(View.GONE);
                break;
            case R.id.Modifydelete:
                final View selectedView = (View)selectedImageView.getParent();
                String path = pathList.get(parent.indexOfChild(selectedView) -1);
                if(isStorageUrl(path)){
                    StorageReference desertRef = storageRef.child("posts/"+writeinfo.getId()+"/"+storageUrlToName(path));
                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showToast(BulletinBoardWriteActivity.this,"파일 삭제 성공");
                            pathList.remove(parent.indexOfChild(selectedView) - 1);
                            parent.removeView(selectedView);
                            ButtonBackgroundLayout.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            showToast(BulletinBoardWriteActivity.this,"파일 삭제 실패");
                        }
                    });
                } else {
                    pathList.remove(parent.indexOfChild(selectedView) - 1);
                    parent.removeView(selectedView);
                    ButtonBackgroundLayout.setVisibility(View.GONE);
                }
                break;
        }
    };

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                selectedEditText = (EditText) v;
            }
        }
    };

    public void storageUpload() {
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();

        if (title.length() > 0) {
            final ArrayList<String> contentsList = new ArrayList<>();
            final ArrayList<String> formatList = new ArrayList<>();

            user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = writeinfo == null ? firebaseFirestore.collection("posts").document() : firebaseFirestore.collection("posts").document(writeinfo.getId());
            final Date date = writeinfo == null ? new Date() : writeinfo.getCreateAt();

            for(int i = 0; i < parent.getChildCount(); i++){
                LinearLayout linearLayout = (LinearLayout)parent.getChildAt(i);
                for(int ii = 0; ii < linearLayout.getChildCount(); ii++){
                    View view = linearLayout.getChildAt(ii);
                    if(view instanceof EditText){
                        String text = ((EditText)view).getText().toString();
                        if(text.length() > 0){
                            contentsList.add(text);
                            formatList.add("text");
                        }
                    } else if (!isStorageUrl(pathList.get(pathCount))){
                        String path = pathList.get(pathCount);
                        successCount++;
                        contentsList.add(path);

                        if(isImageFile(path)){
                            formatList.add("image");
                        } else if (isVideoFile(path)){
                            formatList.add("video");
                        } else {
                            formatList.add("text");
                        }

                        String[] pathArray = path.split("\\.");
                        final StorageReference mountainImagesRef = storageRef.child("posts/" +documentReference.getId() + "/"+pathCount+"."+pathArray[pathArray.length -1]);
                        try {
                            InputStream stream = new FileInputStream(new File(pathList.get(pathCount)));
                            StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("index",""+(contentsList.size()-1)).build();
                            UploadTask uploadTask = mountainImagesRef.putStream(stream, metadata);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    final int index = Integer.parseInt(taskSnapshot.getMetadata().getCustomMetadata("index"));
                                    mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            contentsList.set(index, uri.toString());
                                            successCount--;
                                            if (successCount == 0){
                                                //완료
                                                PostWriteinfo writeinfo = new PostWriteinfo(title, contentsList, formatList, user.getUid(), date);
                                                storeUpload(documentReference, writeinfo);
                                            }
                                        }
                                    });
                                }
                            });
                        } catch (FileNotFoundException e){
                        }

                        pathCount++;
                    }
                }
            }
            if(successCount == 0){
                storeUpload(documentReference, new PostWriteinfo(title, contentsList, formatList, user.getUid(), date));
            }
        } else {

            showToast(BulletinBoardWriteActivity.this,"제목을 입력해주세요.");
        }
    }

    private void storeUpload(DocumentReference documentReference, final PostWriteinfo writeinfo) {
        documentReference.set(writeinfo.getWriteinfo())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("writeinfo", writeinfo);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    private void postInit(){
        if(writeinfo != null){
            titleEditText.setText(writeinfo.getTitle());
            ArrayList<String> contentsList = writeinfo.getContents();
            //contents에 저장된 리스트 불러오기
            for (int i = 0; i < contentsList.size(); i++) {
                String contents = contentsList.get(i);
                if (isStorageUrl(contents)) {
                    //리스트에 컨텐츠 추가
                    pathList.add(contents);
                    ContentsView contentsItemView = new ContentsView(this);

                    //LinearLayout 추가
                    parent.addView(contentsItemView);

                    contentsItemView.setImage(contents);
                    //이미지클릭시 ui나옴
                    contentsItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ButtonBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v;
                        }
                    });
                    contentsItemView.setOnFocusChangeListener(onFocusChangeListener);
                    if(i < contentsList.size() - 1){
                        String nextContents = contentsList.get(i + 1);
                        if(!isStorageUrl(nextContents)){
                            contentsItemView.setText(nextContents);
                        }
                    }

                }else if(i == 0){
                    contentsEditText.setText(contents);
                }
            }
        }
    }

    private void startActivity(Class c, int media, int requestCode) {
        Intent intent = new Intent(this, c);
        intent.putExtra(INTENT_MEDIA, media);
        startActivityForResult(intent, requestCode);
    }

}