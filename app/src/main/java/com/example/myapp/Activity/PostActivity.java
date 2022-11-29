package com.example.myapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.myapp.BulletinBoard.FreeWriteActivity;
import com.example.myapp.StoreLink;
import com.example.myapp.R;
import com.example.myapp.PostWriteinfo;
import com.example.myapp.listener.OnPostListener;
import com.example.myapp.view.ReadContentsView;


public class PostActivity extends BasicActivity {
    private PostWriteinfo writeinfo;
    private StoreLink firebaseHelper;
    private ReadContentsView readContentsView;
    private LinearLayout contentsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        writeinfo = (PostWriteinfo)getIntent().getSerializableExtra("writeinfo");
        contentsLayout = findViewById(R.id.contentsLayout);
        readContentsView = findViewById(R.id.readContentsView);

        firebaseHelper = new StoreLink(this);
        firebaseHelper.setOnPostListener(onPostListener);
        uiUpdate();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    writeinfo = (PostWriteinfo) data.getSerializableExtra("writeinfo");
                    contentsLayout.removeAllViews();
                    uiUpdate();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                firebaseHelper.storageDelete(writeinfo);
                finish();
                return true;

            case R.id.modify:
                startActivity(FreeWriteActivity.class, writeinfo);
                //startActivity(ReviewWriteActivity.class, writeinfo);
                //startActivity(TipWriteActivity.class, writeinfo);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete() {
            Log.e("로그","삭제 성공");
        }

        @Override
        public void onModify() {
            Log.e("로그","수정 성공");

        }
    };

    private void uiUpdate(){
        readContentsView.setPostInfo(writeinfo);
        setToolbarTitle(writeinfo.getTitle());
    }

    private void startActivity(Class c, PostWriteinfo writeinfo) {
        Intent intent = new Intent(this, c);
        intent.putExtra("writeinfo", writeinfo);
        startActivityForResult(intent,0);
    }
}