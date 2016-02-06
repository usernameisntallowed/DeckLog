package com.enochtam.decklog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogDetails extends AppCompatActivity {
    EditText longText;
    EditText latText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Log Details");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        longText = (EditText) findViewById(R.id.longitude);
        latText = (EditText) findViewById(R.id.latitude);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location !=null){
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    longText.setText(Double.toString(longitude));
                    latText.setText(Double.toString(latitude));
                }
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
    }
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_details, menu);
        return true;
    }
    public void saveDetail(View view){
        // on save
    }
    public void getTimeDate(View view){

    }
    public void getCoordinates(View view){
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        if (TextUtils.isEmpty(provider)){
            Toast.makeText(getApplicationContext(),"error getting provider",Toast.LENGTH_SHORT).show();
        }
        PackageManager pm = getApplicationContext().getPackageManager();
        if (pm.checkPermission("android.permission.ACCESS_FINE_LOCATION",getApplicationContext().getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestSingleUpdate(provider, locationListener, null);
            //Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //locationListener.onLocationChanged(l);
        }
        else{
            Toast.makeText(getApplicationContext(),"Unable to obtain Location Permissions",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}