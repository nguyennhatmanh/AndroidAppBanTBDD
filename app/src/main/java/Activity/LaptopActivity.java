package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.LaptopAdapter;
import model.Sanpham;
import until.Checkconnection;
import until.Server;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewlaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> sanphamArrayList;
    SearchView searchViewLapTop;
    Button btnGiaduoi10tr, btnGiatren10tr;
    int idlaptop=0;
    int page=1;
    String query = "";
    View footerview;
    boolean isLoading=false;
    mHandler mHandler;
    boolean limitdata= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
            Getidsp();
            Actiontoolbar();
            Getdata(page);
            setupListeners();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        listViewlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("thongtinsanpham",sanphamArrayList.get(i));
                startActivity(intent);
            }
        });
//        listViewlaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int FisrtItem, int VisibleItem, int TotalItem) {
//                if(FisrtItem+VisibleItem==TotalItem && TotalItem!=0 && isLoading==false && limitdata==false){
//                    isLoading=true;
//                    ThreadData threadData=new ThreadData();
//                    threadData.start();
//                }
//            }
//        });
    }

    private void Getdata(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Đuongandienthoai+String.valueOf(page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenlaptop="";
                int Gialatop=0;
                String Hinhanhlaptop="";
                String Motalatop="";
                int Idsplatop=0;
                if(response!=null && response.length()!=2){
                    listViewlaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tenlaptop=jsonObject.getString("tensp");
                            Gialatop=jsonObject.getInt("giasp");
                            Hinhanhlaptop=jsonObject.getString("hinhanhsp");
                            Motalatop=jsonObject.getString("motasp");
                            Idsplatop=jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(id,Tenlaptop,Gialatop,Hinhanhlaptop,Motalatop,Idsplatop
                            ));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata=true;
                    listViewlaptop.removeFooterView(footerview);
                    Checkconnection.Showtoast_short(getApplicationContext(),"Het du lieu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
        LoadMoreData();
    }

    private void Getduoi(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdansearchduoi10tr+String.valueOf(page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenlaptop="";
                int Gialatop=0;
                String Hinhanhlaptop="";
                String Motalatop="";
                int Idsplatop=0;
                if(response!=null && response.length()!=2){
                    listViewlaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tenlaptop=jsonObject.getString("tensp");
                            Gialatop=jsonObject.getInt("giasp");
                            Hinhanhlaptop=jsonObject.getString("hinhanhsp");
                            Motalatop=jsonObject.getString("motasp");
                            Idsplatop=jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(id,Tenlaptop,Gialatop,Hinhanhlaptop,Motalatop,Idsplatop
                            ));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata=true;
                    listViewlaptop.removeFooterView(footerview);
                    Checkconnection.Showtoast_short(getApplicationContext(),"Het du lieu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Gettren(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdancsearchtren10tr+String.valueOf(page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenlaptop="";
                int Gialatop=0;
                String Hinhanhlaptop="";
                String Motalatop="";
                int Idsplatop=0;
                if(response!=null && response.length()!=2){
                    listViewlaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tenlaptop=jsonObject.getString("tensp");
                            Gialatop=jsonObject.getInt("giasp");
                            Hinhanhlaptop=jsonObject.getString("hinhanhsp");
                            Motalatop=jsonObject.getString("motasp");
                            Idsplatop=jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(id,Tenlaptop,Gialatop,Hinhanhlaptop,Motalatop,Idsplatop
                            ));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata=true;
                    listViewlaptop.removeFooterView(footerview);
                    Checkconnection.Showtoast_short(getApplicationContext(),"Het du lieu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void searchProducts(int Page, String query) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdancsearch+String.valueOf(page)+"&query="+String.valueOf(query);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenlaptop="";
                int Gialatop=0;
                String Hinhanhlaptop="";
                String Motalatop="";
                int Idsplatop=0;
                if(response!=null && response.length()!=2){
                    listViewlaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tenlaptop=jsonObject.getString("tensp");
                            Gialatop=jsonObject.getInt("giasp");
                            Hinhanhlaptop=jsonObject.getString("hinhanhsp");
                            Motalatop=jsonObject.getString("motasp");
                            Idsplatop=jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(id,Tenlaptop,Gialatop,Hinhanhlaptop,Motalatop,Idsplatop
                            ));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata=true;
                    listViewlaptop.removeFooterView(footerview);
                    Checkconnection.Showtoast_short(getApplicationContext(),"Het du lieu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();;
            }
        });
    }

    private void Getidsp() {
        idlaptop=getIntent().getIntExtra("idloaisp",-1);
        Log.d("Gia tri loai san pham",idlaptop+"");
    }

    private void Anhxa() {
        toolbar=(Toolbar) findViewById(R.id.toolbarLaptop);
        listViewlaptop=(ListView) findViewById(R.id.listviewLaptop);
        searchViewLapTop = (SearchView) findViewById(R.id.searchViewLaptop);
        btnGiaduoi10tr = findViewById(R.id.btnGiaduoi10tr);
        btnGiatren10tr = findViewById(R.id.btnGiatren10tr);
        sanphamArrayList=new ArrayList<>();
        laptopAdapter=new LaptopAdapter(getApplicationContext(),sanphamArrayList);
        listViewlaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.processbar,null);
        mHandler=new mHandler();
    }

    private void setupListeners() {
        btnGiaduoi10tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanphamArrayList.clear(); // Xóa dữ liệu cũ
                laptopAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                page = 1; // Đặt lại trang
                Getduoi(page);
            }
        });

        btnGiatren10tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sanphamArrayList.clear(); // Xóa dữ liệu cũ
                laptopAdapter.notifyDataSetChanged(); // Cập nhật giao diện
                page = 1; // Đặt lại trang
                Gettren(page);
            }
        });
        searchViewLapTop.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                sanphamArrayList.clear();
                laptopAdapter.notifyDataSetChanged();
                page = 1;
                searchProducts(page, query);
                return false;
            }
        });
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewlaptop.addFooterView(footerview);
                    break;
                case 1:
                    Getdata(++page);
                    isLoading= false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}