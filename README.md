# 简版EPC SDK开发文档

* build.gradle设置 

 ```
  implementation 'com.qixiubao:simple-epc:1.0.1'
  ```


* 初始化 

 ```
  ApiServiceDelegate.init(Context context,String appId, String secret, boolean isDebug); 
  ```


* 获取分类数据 

 ```java
          HashMap<String, Object> map = new HashMap<>();
          map.put("brand_id", 14);
          map.put("epc_id", 1014);
          ApiServiceDelegate.getApiServiceDelegate(this)
                  .getAllCate(map)
                  .enqueue(new CallBack<CataModel>() {
                      @Override
                      public void onSuccess(CataModel cataModel) {
                          //成功回调
                      }
                      @Override
                      public void onFailure(String message, int code) {
                          //失败回调
                      }
                  });
  ```


* 获取当前部位下组图数据

  ```java
          HashMap<String, Object> map = new HashMap<>();
          //分类部件Id
          map.put("id", 99);
          map.put("model_id", 7252);
          ApiServiceDelegate.getApiServiceDelegate(this)
                  .getCataDetail(map)
                  .enqueue(new CallBack<CataDetail>() {
                      @Override
                      public void onSuccess(CataDetail data) {
                      }
  
                      @Override
                      public void onFailure(String message, int code) {
                      }
                  });
  ```

* 默认UI入口

 ```java
  RequireData requireData = new RequireData();
//0:BSM 模式 1：EPC模式
  requireData.type = 0;
  requireData.brand_id = 68;
  requireData.model_id = 7252;
  requireData.epc_id = 1034;
  requireData.token = "549bdd7064d455610946d69d0ca6717b";
  requireData.access_time = "1548466724";
  requireData.param = "5kNCKBb8XZ0vYMgxb3e2odC4zvRz-v6y_Eywj-2hzzJzbneuYdJzlJymgv6czzJzY3KxU2k0a34vYMg0adBzlJybmg6YpEIx_zz7z3k7YMk-Zda0Y3s_Zde5od4uzvRzjvq3izz7z3a0b2gwVzy9pcNzYns_YZy9pZyzj-qvj-6vzzJzjvuxivu0jzyA1z04VnjzlJyDnvZzXgF7z2wjadgxUnnzlJyzj-qvj-2xzzJzbns0aGe2V3egVjy9zvqxz2H";
  Intent intent = SimpleEpcCatalogActivity.getCallingIntent(MainActivity.this, requireData);
  startActivity(intent);
  //选中回调
  SimpleEpcCatalogActivity.setOnSelectedCallBack(new SimpleEpcCatalogActivity.OnSelectedCallBack() {
        @Override
        public void onSelected(SimpleEpcDetail data, int position) {
        //选中的配件
        SimpleEpcDetail.DataBean.ListBean listBean = data.data.list.get(position); 
        }
  });

  ```


* SimpleEpcImageView使用方法\(详细示例查看demo\)

 ```java
  void setData(int picWidth, int picHeight, final List<SimpleEpcCatalogBean> data, Bitmap bitmap);
  void setOnSelectedListener(OnSelectedListener onSelectedListener);
  public interface OnSelectedListener {
        /**
         * 点选回调
         *
         * @param index 点中第几个
         */
        void onClickSelected(int index);

        /**
         * 圈选回调
         *
         * @param selectedList selectedList.selectedChildren 圈选中的配件
         */
        void onDrawSelected(List<SimpleEpcCatalogBean> selectedList);

        /**
         * 旋转后滑动收回
         */
        void onSlide();
    }

  ```


