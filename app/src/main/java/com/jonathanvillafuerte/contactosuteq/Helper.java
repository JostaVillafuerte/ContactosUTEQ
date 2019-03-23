package com.jonathanvillafuerte.contactosuteq;

import android.content.Context;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;

public class Helper {

    public static final String PATH_TO_SERVER_IMAGE_UPLOAD = "https://villafuertejonathan.000webhostapp.com/web_services/insertar.php";

    public static final String LISTARDATOS = "https://villafuertejonathan.000webhostapp.com/web_services/listar.php";
    public static final String IMAGE_STRING = "image_string";
    public static final String IMAGE_FILENAME = "filename";
    public static final String constante = "https://villafuertejonathan.000webhostapp.com/web_services/imagenes_contactos/"; //aca concatenamos la imagen q contiene 'nombre+apellido'.'extension'

    public static String getUrl(EditText editText){
        return editText.getText().toString();
    }
    public static boolean isTextNullOrEmpty(String inputValue){
        if(inputValue.equals("") || inputValue.isEmpty()){
            return true;
        }
        return false;
    }
    public static void displayNoticeMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}