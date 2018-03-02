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

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    EditText edtUpdateTitle,edtUpdateDesc;
    Button btnUpdate;
    String id,title,description;

    RequestQueue updateReq;
    private String URL="http://10.0.3.2/mycrud/updatedata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        id=getIntent().getExtras().getString("id");
        title=getIntent().getExtras().getString("title");
        description=getIntent().getExtras().getString("desc");

        updateReq= Volley.newRequestQueue(this);

        edtUpdateDesc=(EditText) findViewById(R.id.edt_task_descupdate);
        edtUpdateTitle=(EditText) findViewById(R.id.edt_task_titleupdate);

        btnUpdate=(Button) findViewById(R.id.btn_task_update);

        edtUpdateTitle.setText(title);
        edtUpdateDesc.setText(description);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle,updatedDescription;
                updatedTitle= edtUpdateTitle.getText().toString();
                updatedDescription= edtUpdateDesc.getText().toString();

                saveUpdatedData(updatedTitle,updatedDescription);
            }
        });

    }

    private void saveUpdatedData(final String updatedTitle, final String updatedDescription) {
        StringRequest updateData= new StringRequest(Request.Method.POST, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverResponse",response);
                Toast.makeText(Update.this,response , Toast.LENGTH_SHORT).show();

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
                myTaskData.put("id",id);
                myTaskData.put("title",updatedTitle);
                myTaskData.put("description",updatedDescription);
                return myTaskData;
            }
        };
        updateReq.add(updateData);
    }
}

