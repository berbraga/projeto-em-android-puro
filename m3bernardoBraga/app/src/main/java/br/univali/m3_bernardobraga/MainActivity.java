package br.univali.m3_bernardobraga;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {



    // o que fazer:

    // criar a activity      ok
    //
    // capturar uma foto por dentro do APP          ok
    //
    // pegar as coordenadas do GPS de onde o user bateu a foto
    //
    // enviar para o back uma requisicao

    // =========================================

    // criada a activity


    ImageView iv_foto;
    TextView tv_info;

    Double latitude;
    Double longitude;

    Bitmap image;

    String base64String;

    String temTudo = "";
//    dados dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //  verifica se a permicao da camera foi aceita
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
//
//        }

        askForPermission();
//        instancia a imageView
        iv_foto = (ImageView) findViewById(R.id.imageView);

        tv_info = (TextView) findViewById(R.id.tv_info);

//        instancia o botao para tirar foto
        findViewById(R.id.bt_take_pick).setOnClickListener(new View.OnClickListener() {

            //            metodo para o click do botao de tirar foto
            public void onClick(View v) {
                tirarFoto();
            }
        });
    }



    //    funcao que faz a abertura da intent de tirar foto
    public void tirarFoto () {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        configService();
        startActivityForResult(intent, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            iv_foto.setImageBitmap(image);
            base64String = convert(image);
            tv_info.append("\n\nBase64: "+ base64String);
            temTudo += "\nBase64: "+ base64String;
            System.out.println("================================");
            System.out.println(temTudo);
            System.out.println("================================");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

// ===================== gps =====================



        private void askForPermission() {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA}, 2);
            }
            else configService();
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case 1: {
                    if (grantResults.length > 0 && grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {
                        configService();
                    } else {
                        Toast.makeText(this, "No permission for GPSacessing", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }
        }



    public void configService(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    Double latPoint = location.getLatitude();
                    Double lngPoint = location.getLongitude();

                    System.out.println("==================");

                    System.out.println(latPoint + " " + lngPoint);
                    latitude = latPoint;
                    longitude = lngPoint;
                    temTudo = "Latitude: " + latitude.toString() +" \n\nLongitude: " + longitude.toString();
                    tv_info.setText(temTudo);
                    System.out.println("==================");
                }
//                Location location;
//                latitude = location.getLatitude();

                public void onStatusChanged(String provider, int status, Bundle extras) { }
                public void onProviderEnabled(String provider) { }
                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 900000, 10000, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

// =============== converter bitmap em base64 =============


//    image.
    public String convert (Bitmap image) {

        System.out.println("=======================");



            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            byte[] imageBytes = baos.toByteArray();

            String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
            System.out.println(base64String);
            System.out.println("=======================");

//            dados = new dados(latitude.toString(),longitude.toString(),base64String);

            return base64String;


//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream .toByteArray();
//
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
////        System.out.println(ImageUtil.convert(image));

//        return "";
    }




}