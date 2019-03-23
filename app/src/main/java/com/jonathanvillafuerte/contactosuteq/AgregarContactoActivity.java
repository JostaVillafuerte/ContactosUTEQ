package com.jonathanvillafuerte.contactosuteq;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.jonathanvillafuerte.contactosuteq.WebService.Asynchtask;
import com.jonathanvillafuerte.contactosuteq.WebService.WebService;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarContactoActivity extends AppCompatActivity implements Asynchtask {


    private final int REQUEST_IMAGE_FROM_GALLERY = 200;
    private ProgressDialog progressDialog;
    private String encodedString;
    private String filename;
    private ImageView imageToUpload;
    private long timeBeforeUpload;
    private long timeAfterUpload;
    private long fileSize;
    private Button guardar;

    private EditText nombres;
    private EditText apellidos;
    private EditText telpersonal;
    private EditText tellaboral;
    private EditText telhogar;
    private EditText correo;
    private String latitud, longitud = "";

    @Override
    public void processFinish(String result) throws JSONException {

        String ms = result;
        if(ms.equals("Exito"))
        {
            Toast.makeText(this,"Insertado correctamente", Toast.LENGTH_SHORT).show();
            nombres.setText("");
            apellidos.setText("");
            telpersonal.setText("");
            tellaboral.setText("");
            telhogar.setText("");
            correo.setText("");
            imageToUpload.setImageResource(R.drawable.img_add_image);
        }
        else
        {
            Toast.makeText(this,"Error con el servidor", Toast.LENGTH_SHORT).show();
        }



    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitud = String.valueOf(location.getLongitude());
            latitud = String.valueOf(location.getLatitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private Button obtenerCoordenadas;
    Map<String, String> params = new HashMap<String, String>();
    private final int REQUEST_ACCESS = 0;

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);
        //setTitle("Upload image");
        //queue = Volley.newRequestQueue(this);
        //circularProgress = (CircleView) findViewById(R.id.circular_display);
        //assert circularProgress != null;
        //circularProgress.setTitleText("0 Kb/s");
        //circularProgress.setSubtitleText("Upload speed");
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_ACCESS);
        }


        FloatingActionButton my_fab = (FloatingActionButton) findViewById(R.id.regresar);
        my_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgregarContactoActivity.this,ListarContactosActivity.class);
                startActivity(intent);

            }
        });
        imageToUpload = (ImageView) findViewById(R.id.image_to_upload);
        guardar = (Button) findViewById(R.id.subir);
        obtenerCoordenadas = (Button) findViewById(R.id.obtenercoordenadas);
        nombres = (EditText) findViewById(R.id.txtNombres);
        apellidos = (EditText) findViewById(R.id.txtApellidos);
        telpersonal = (EditText) findViewById(R.id.txtTelefonoPersonal);
        telhogar = (EditText) findViewById(R.id.txtTelefonoHogar);
        tellaboral = (EditText) findViewById(R.id.txtTelefonoLaboral);
        correo = (EditText) findViewById(R.id.txtcorreoelectronico);
        imageToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromDeviceGallery();
            }
        });

        obtenerCoordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(AgregarContactoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AgregarContactoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AgregarContactoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PETICION_PERMISO_LOCALIZACION);
                        return;
                    } else {
                        LocationManager locationManager = (LocationManager) getSystemService(AgregarContactoActivity.this.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


                        if(location==null)
                        {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                            latitud = String.valueOf(location.getLatitude());
                            longitud = String.valueOf(location.getLongitude());
                            Toast.makeText(AgregarContactoActivity.this,"Su ubicación fue tomada",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            latitud = String.valueOf(location.getLatitude());
                            longitud = String.valueOf(location.getLongitude());
                            Toast.makeText(AgregarContactoActivity.this,"Su ubicación fue tomada",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                catch (Exception ex)
                {
                    String e = ex.getMessage();
                }
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                params.put(Helper.IMAGE_STRING, encodedString);
                filename = nombres.getText().toString()+apellidos.getText().toString()+".jpg";
                params.put(Helper.IMAGE_FILENAME, filename);
                params.put("nombres", nombres.getText().toString());
                params.put("apellidos", apellidos.getText().toString());
                params.put("url_foto", Helper.constante+filename);
                params.put("telefono_hogar", telhogar.getText().toString());
                params.put("telefono_personal", telpersonal.getText().toString());
                params.put("telefono_laboral", tellaboral.getText().toString());
                params.put("correo_electronico", correo.getText().toString());
                params.put("latitud", latitud);
                params.put("longitud",longitud);

                WebService ws = new WebService(Helper.PATH_TO_SERVER_IMAGE_UPLOAD, params, AgregarContactoActivity.this,  AgregarContactoActivity.this);
                ws.execute("");




            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_ACCESS)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permiso permitido",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Permiso denegado",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void pickImageFromDeviceGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_FROM_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_FROM_GALLERY && null != data) {
            try {


                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                String fileNameSegments[] = picturePath.split("/");
                filename = fileNameSegments[fileNameSegments.length - 1];
                File file = new File(picturePath);
                Bitmap myImg = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageToUpload.setImageBitmap(myImg);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myImg.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byte_arr = stream.toByteArray();
                fileSize = byte_arr.length;
                encodedString = Base64.encodeToString(byte_arr, 0);
                timeBeforeUpload = System.currentTimeMillis();


                //uploadSelectedImageToServer();
            }
            catch (Exception ex )
            {
                Toast.makeText(this,"No hay permisos",Toast.LENGTH_LONG).show();
            }
        }
    }
}