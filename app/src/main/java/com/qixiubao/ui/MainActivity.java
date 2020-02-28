package com.qixiubao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qixiubao.R;
import com.simpleepc.api.ApiServiceDelegate;
import com.simpleepc.model.RequireData;
import com.simpleepc.model.SimpleEpcDetail;
import com.simpleepc.ui.SimpleEpcCatalogActivity;

/**
 * @author tudahua
 * @date 2019/1/21
 * Description:
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化appId和secret
        ApiServiceDelegate.init(this, "53652a26d4a8db783b5b0a7736e23033", "839f3235ba41946d8a3ee33bce4124ea", true);
        final RequireData requireData = new RequireData();
        //0:BSM 模式 1：EPC模式
        requireData.type = 0;
        requireData.brand_id = 14;
        requireData.model_id = 7252;
        requireData.epc_id = 1014;
        requireData.token = "0768036056aa96bbeebe534669dfea2e";
        requireData.access_time = "1561687126";
        requireData.param = "Krf75KRAXZ00Uwa0aJy9jjJzbneuYdIeaNs6YZy9zvqxyuq-_ucZ_jz7z3k4UdC_V23zlJy-gJz7z3GxZdAebdokzvRziFixkz0q";
        findViewById(R.id.default_ui_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SimpleEpcCatalogActivity.getCallingIntent(MainActivity.this, requireData);
                startActivity(intent);
            }
        });
        findViewById(R.id.customer_ui_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CatalogListActivity.class));
            }
        });

        SimpleEpcCatalogActivity.setOnSelectedCallBack(new SimpleEpcCatalogActivity.OnSelectedCallBack() {
            @Override
            public void onSelected(SimpleEpcDetail data, int position) {
                //选中的配件
                SimpleEpcDetail.DataBean.ListBean listBean = data.data.list.get(position);
                Toast.makeText(MainActivity.this,
                        "onSelected=" + listBean.caption, Toast.LENGTH_LONG).show();
            }
        });


    }
}
