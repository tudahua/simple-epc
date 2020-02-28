package com.qixiubao.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qixiubao.R;
import com.simpleepc.model.SimpleEpcCatalogBean;
import com.simpleepc.widget.SimpleEpcImageView;

import java.util.List;

/**
 * @author tudahua
 * @date 2019/1/18
 * Description:
 */
public class SimpleEpcImageViewActivity extends AppCompatActivity {
    private SimpleEpcImageView mSimpleEpcIv;
    private TextView mContentTv;
    private SimpleEpcCatalogBean mCatalogBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_epc_image_view);
        mSimpleEpcIv = findViewById(R.id.simple_epc_iv);
        mContentTv = findViewById(R.id.content_tv);
        mCatalogBean = (SimpleEpcCatalogBean) getIntent().getSerializableExtra("data");
        findViewById(R.id.reset_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSimpleEpcIv.reset();
            }
        });
        if (mCatalogBean != null) {
            Glide.with(this)
                    .load(mCatalogBean.image)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            setData(resource);
                        }
                    });
        }
    }

    private void setData(Bitmap resource) {
        mSimpleEpcIv.setData(mCatalogBean.width, mCatalogBean.height, mCatalogBean.children, resource);
        mSimpleEpcIv.setOnSelectedListener(new SimpleEpcImageView.OnSelectedListener() {
            @Override
            public void onClickSelected(int index) {
                mContentTv.setText("点选中" + mCatalogBean.children.get(index).title);
            }

            @Override
            public void onDrawSelected(List<SimpleEpcCatalogBean> selectedList) {
                StringBuilder content = new StringBuilder();
                content.append("圈选中：\n");
                for (int i = 0; i < selectedList.size(); i++) {
                    SimpleEpcCatalogBean catalogBean = selectedList.get(i);
                    content.append(catalogBean.title);
                    content.append("-->");
                    for (int j = 0; j < catalogBean.selectedChildren.size(); j++) {
                        SimpleEpcCatalogBean bean = catalogBean.selectedChildren.get(j);
                        content.append(bean.title);
                        if (j != catalogBean.selectedChildren.size() - 1) {
                            content.append(",");
                        } else {
                            content.append("\n");
                        }
                    }
                }
                mContentTv.setText(content);
            }

            @Override
            public void onSlide() {
                mSimpleEpcIv.reset();
            }
        });
    }

    public static Intent getCallingIntent(Context context, SimpleEpcCatalogBean catalogBean) {
        Intent intent = new Intent(context, SimpleEpcImageViewActivity.class);
        intent.putExtra("data", catalogBean);
        return intent;
    }
}
