package com.example.geolabedu.testn2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geolabedu.testn2.Adapter.CustomViewPagerAdapter;
import com.example.geolabedu.testn2.database.DBHelper;
import com.example.geolabedu.testn2.database.VehiclContracts;
import com.example.geolabedu.testn2.database.VehicleData;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    int ID;
    DBHelper dbHelper;
    public static SQLiteDatabase sqLiteDatabase;
    TextView textView,description;
    Toolbar toolbar;
    ViewPager viewPager;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper=new DBHelper(this);
        sqLiteDatabase=dbHelper.getWritableDatabase();

        viewPager= (ViewPager) findViewById(R.id.viewpager);
        textView= (TextView) findViewById(R.id.detal_calendar);
        description= (TextView) findViewById(R.id.description);


        VehicleData data= (VehicleData) getIntent().getExtras().getSerializable("data");

        Cursor cursor=sqLiteDatabase.query(VehiclContracts.VEHICLE_IMAGE_TABLE,null,VehiclContracts.VEHICLE_PARENT_ID + "= "+data.getID(),null,null,null,null);

        ArrayList list=new ArrayList();
        if (cursor.moveToFirst()){
            do {
                String image = cursor.getString(cursor.getColumnIndex(VehiclContracts.VEHICLE_IMAGE));
                VehicleData itemimage=new VehicleData(image);

                list.add(itemimage.getImage2());
            }while (cursor.moveToNext());
        }

        CustomViewPagerAdapter pagerAdapter=new CustomViewPagerAdapter(this,list);
        viewPager.setAdapter(pagerAdapter);

        String calendar=data.getCalendar();
        String desck=data.getDecskr();

        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/Helvetica-Bold.otf");
        textView.setTypeface(typeface);

        textView.setText(calendar);
        description.setText(desck);

        ID=data.getID();

        toolbar= (Toolbar) findViewById(R.id.detal_toolbar);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.statusrose));


        setSupportActionBar(toolbar);

        //toolbar title change
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){

            case R.id.edit:
                Toast.makeText(this,"click edit",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this,ChooseActivity.class);
                intent.putExtra("edit",ID);
                startActivity(intent);
                return true;
            case R.id.delete:
                Toast.makeText(this,"click delete",Toast.LENGTH_LONG).show();
                return true;
            case R.id.send:
                Toast.makeText(this,"click send",Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
