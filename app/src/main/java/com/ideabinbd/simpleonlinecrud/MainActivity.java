package com.ideabinbd.simpleonlinecrud;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView dataList;
    ArrayAdapter listArray;
    Button btnSave;
    RequestQueue registrationNetworkQueue;
    String onlineURL="http://10.0.3.2/mycrud/uploadtask.php",getDataURL="http://10.0.3.2/mycrud/gettasks.php";
    String deleteDataURL="http://10.0.3.2/mycrud/deletedata.php";

    String[] id, titles,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        edtEmail=(EditText) findViewById(R.id.edt_reg_email);
        edtName=(EditText) findViewById(R.id.edt_reg_name);
        edtMobile=(EditText) findViewById(R.id.edt_reg_mobile);
*/
        btnSave=(Button) findViewById(R.id.btn_home_add);

        dataList=(ListView) findViewById(R.id.list_home_datalist);


        registrationNetworkQueue= Volley.newRequestQueue(this);

        fetchData();

        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent update=new Intent(MainActivity.this,Update.class);
                update.putExtra("id",id[i]);
                update.putExtra("title", titles[i]);
                update.putExtra("desc",desc[i]);

                startActivity(update);
            }
        });

        dataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder confirmDelete=new AlertDialog.Builder(MainActivity.this);
                confirmDelete.setTitle("Sure you want to delete??")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFromServer(id[position]);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                confirmDelete.show();

                return true;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText inpTitle,inpDesc;


            startActivity(new Intent(MainActivity.this, Upload.class));

        /*        String strName,strEmail,strMobile;
                strName=edtName.getText().toString();
                strEmail=edtEmail.getText().toString();
                strMobile=edtMobile.getText().toString();

                if(TextUtils.isEmpty(strName) || TextUtils.isEmpty(strEmail) ||TextUtils.isEmpty(strMobile)){
                    Toast.makeText(MainActivity.this, "Enter all data", Toast.LENGTH_SHORT).show();
                }else{
                    sendDataToServer(strName,strEmail,strMobile);
                }
    */        }
        });
    }

    private void deleteFromServer(final String s) {
        StringRequest deleteRequest= new StringRequest(Request.Method.POST, deleteDataURL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverResponse",response);
                Toast.makeText(MainActivity.this,response , Toast.LENGTH_SHORT).show();
                fetchData();

                /*
                try {
                    JSONArray fullData= new JSONArray(response);
                    titles=new String[fullData.length()];
                    listArray=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,titles);
                    dataList.setAdapter(listArray);
                    for (int i=0;i<fullData.length();i++){
                        JSONObject singleData= fullData.getJSONObject(i);
                        titles[i]=singleData.getString("name");
                      Log.d("serverResponse",titles[i]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Data is not JSON", Toast.LENGTH_SHORT).show();
                }

                listArray.notifyDataSetChanged();

                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("serverError",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> myUserData=new HashMap<>();
                myUserData.put("id",s);

                return myUserData;
            }
        };
        registrationNetworkQueue.add(deleteRequest);
    }

    private void fetchData() {
        StringRequest getAllData= new StringRequest(Request.Method.POST, getDataURL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverResponse",response);
                Toast.makeText(MainActivity.this,response , Toast.LENGTH_SHORT).show();

                try {
                    JSONArray fullData= new JSONArray(response);
                    titles =new String[fullData.length()];
                    id=new String[fullData.length()];
                    desc=new String[fullData.length()];

                    listArray=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, titles);
                    dataList.setAdapter(listArray);
                    for (int i=0;i<fullData.length();i++){
                        JSONObject singleData= fullData.getJSONObject(i);
                        id[i]=singleData.getString("id");
                        desc[i]=singleData.getString("description");
                        titles[i]=singleData.getString("title");

                      Log.d("serverResponse", titles[i]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Data is not JSON", Toast.LENGTH_SHORT).show();
                }

                listArray.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("serverError",error.toString());
            }
        });
        registrationNetworkQueue.add(getAllData);
    }


    private void sendDataToServer(final String strName, final String strEmail, final String strMobile) {
        //send data here
        StringRequest saveDataRequest= new StringRequest(Request.Method.POST, onlineURL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverResponse",response);
                Toast.makeText(MainActivity.this,response , Toast.LENGTH_SHORT).show();

                /*
                try {
                    JSONArray fullData= new JSONArray(response);
                    titles=new String[fullData.length()];
                    listArray=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,titles);
                    dataList.setAdapter(listArray);
                    for (int i=0;i<fullData.length();i++){
                        JSONObject singleData= fullData.getJSONObject(i);
                        titles[i]=singleData.getString("name");
                      Log.d("serverResponse",titles[i]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Data is not JSON", Toast.LENGTH_SHORT).show();
                }

                listArray.notifyDataSetChanged();

                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("serverError",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> myUserData=new HashMap<>();
                myUserData.put("name",strName);
                myUserData.put("email",strEmail);
                myUserData.put("mobile",strMobile);
                return myUserData;
            }
        };
        registrationNetworkQueue.add(saveDataRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchData();
    }
}
