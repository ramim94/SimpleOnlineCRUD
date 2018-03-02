package com.ideabinbd.simpleonlinecrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity {
    EditText edtTitle,edtDesc;
    Button btnUpload;
    RequestQueue rq;
    String URL="http://10.0.3.2/mycrud/uploadtask.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        edtTitle=(EditText) findViewById(R.id.edt_task_title);
        edtDesc=(EditText) findViewById(R.id.edt_task_desc);

        btnUpload=(Button) findViewById(R.id.btn_task_upload);

        rq= Volley.newRequestQueue(this);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTitle,strDesc;
                strTitle=edtTitle.getText().toString();
                strDesc=edtDesc.getText().toString();

            sendDataToServer(strTitle,strDesc);
            }
        });

    }

    private void sendDataToServer(final String strTitle, final String strDesc) {
        StringRequest saveDataRequest= new StringRequest(Request.Method.POST, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverResponse",response);
                Toast.makeText(Upload.this,response , Toast.LENGTH_SHORT).show();

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
                Map<String,String> myTaskData=new HashMap<>();
                myTaskData.put("title",strTitle);
                myTaskData.put("description",strDesc);
                return myTaskData;
            }
        };
        rq.add(saveDataRequest);
    }
}

