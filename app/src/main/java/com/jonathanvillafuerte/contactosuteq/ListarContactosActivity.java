package com.jonathanvillafuerte.contactosuteq;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jonathanvillafuerte.contactosuteq.Adapters.Adapter_ListarContactos;
import com.jonathanvillafuerte.contactosuteq.Adapters.dataContacts;
import com.jonathanvillafuerte.contactosuteq.WebService.Asynchtask;
import com.jonathanvillafuerte.contactosuteq.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListarContactosActivity extends AppCompatActivity implements Asynchtask {
    private RecyclerView recyclerView;

    private  final int REQUEST_ACCESS= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);
        recyclerView = findViewById(R.id.rv_contacts);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_ACCESS);
        }
        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.fab);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarContactosActivity.this,AgregarContactoActivity.class);
                startActivity(intent);

            }
        });
        Map<String, String> datos = new HashMap<String, String>();


        WebService ws= new WebService(Helper.LISTARDATOS, datos,this,  this);
        ws.execute("");
    }

    private List<dataContacts> listdata = new ArrayList<>();
    private Adapter_ListarContactos adapter_listarContactos;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public void processFinish(String result) throws JSONException {
        try {

            JSONObject jsonIssues = new JSONObject(result);
            JSONArray jsonArrayIssue = jsonIssues.getJSONArray("lista");
            for(int i=0; i< jsonArrayIssue.length();i++)
            {
                JSONObject objIssue = jsonArrayIssue.getJSONObject(i);
                dataContacts dataContacts;
                dataContacts= new dataContacts(objIssue.getString("nombres"),objIssue.getString("apellidos"),objIssue.getString("url_foto"),objIssue.getString("telefono_hogar"),objIssue.getString("telefono_personal"),objIssue.getString("telefono_laboral"),objIssue.getString("correo_electronico"),objIssue.getString("longitud"),objIssue.getString("latitud"));
                listdata.add(dataContacts);
            }

            adapter_listarContactos= new Adapter_ListarContactos(listdata,this);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter_listarContactos);
            recyclerView.setHasFixedSize(true);
        }
        catch (Exception ex)
        {
            String e = ex.getMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_ACCESS)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permiso permitido", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Permiso denegado",Toast.LENGTH_SHORT).show();
            }
        }
    }
}