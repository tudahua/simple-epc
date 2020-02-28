package com.qixiubao.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qixiubao.R;
import com.simpleepc.api.ApiServiceDelegate;
import com.simpleepc.interf.CallBack;
import com.simpleepc.model.BaseModel;
import com.simpleepc.model.CataModel;
import com.simpleepc.model.SimpleEpcCatalogBean;
import com.simpleepc.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;


public class CatalogListActivity extends AppCompatActivity {

    private ListView mListView;
    private LoadingDialog mLoadingDialog;
    private Call<BaseModel<CataModel>> mCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_list);
        mListView = findViewById(R.id.list_view);
        HashMap<String, Object> data = new HashMap<>();
        data.put("brand_id", 14);
        data.put("epc_id", 1014);
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.showLoadingDialog();
        mCall = ApiServiceDelegate.getApiServiceDelegate(this).getAllCate(data);
        mCall.enqueue(new CallBack<CataModel>() {
            @Override
            public void onSuccess(CataModel cataModel) {
                initData(cataModel.list);
                mLoadingDialog.dismissLoadingDialog();
            }

            @Override
            public void onFailure(String message, int code) {
                mLoadingDialog.dismissLoadingDialog();
            }
        });
        mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mCall.cancel();
            }
        });
    }

    private void initData(final ArrayList<SimpleEpcCatalogBean> list) {
        if (list != null && !list.isEmpty()) {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                data.add(list.get(i).title);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SimpleEpcCatalogBean listBean = list.get(i);
                    Intent intent = SimpleEpcImageViewActivity.getCallingIntent(CatalogListActivity.this, listBean);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
