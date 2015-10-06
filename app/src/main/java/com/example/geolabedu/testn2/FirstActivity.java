package com.example.geolabedu.testn2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.geolabedu.testn2.Adapter.MyRecyclerAdapter;
import com.example.geolabedu.testn2.database.DBHelper;
import com.example.geolabedu.testn2.database.VehiclContracts;
import com.example.geolabedu.testn2.database.VehicleData;

import java.util.ArrayList;


public class FirstActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener{

    DBHelper dbHelper;
    RecyclerView recyclerView;
    public static SQLiteDatabase sqLiteDatabase;
    android.support.v7.widget.Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        dbHelper=new DBHelper(this);
        sqLiteDatabase=dbHelper.getWritableDatabase();

        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);

        navigationView= (NavigationView) findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);//akontrolebs ghiaa tu ara
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);//itokshi ar vici ras aketebs
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        //statusbar change color
        Window window =getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.statusrose));

//        if (getIntent().hasExtra("list")) {
//            ArrayList data = (ArrayList) getIntent().getExtras().getSerializable("list");
//            MyRecyclerAdapter adapter=new MyRecyclerAdapter(this, data);
//            recyclerView.setAdapter(adapter);
//        }
        /*
        * orderby date
        * */
        if(sqLiteDatabase.isDatabaseIntegrityOk()){
            Cursor c = sqLiteDatabase.query(VehiclContracts.VEHICLE_TABLE_NAME, null, null, null, null, null, null);

            ArrayList list=new ArrayList();

            if(c.moveToFirst()){
                do {
                    String image=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_MAIN_IMAGE));
                    String mail=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_PERSON_EMAIL));
                    String nomeri=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_PERSON_PHONE));
                    String categ=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_CATEGORY));
                    String modeli=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_MODEL));
                    String weli=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_AGE));
                    Integer ID=c.getInt(c.getColumnIndex(VehiclContracts.VEHICLE_ID));
                    String decsk=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_DESCRIPTION));
                    String calendar=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_DATE_ADD));
                    VehicleData item2 = new VehicleData(ID,image,mail,nomeri,categ,modeli,weli,decsk,calendar);

                    list.add(item2);
                 } while(c.moveToNext());
        }
            MyRecyclerAdapter adapter=new MyRecyclerAdapter(this, list);
            recyclerView.setAdapter(adapter);

        }else{
            ArrayList list=new ArrayList();
            VehicleData data=new VehicleData();
            list.add(data);
            MyRecyclerAdapter myRecyclerAdapter=new MyRecyclerAdapter(this, list);
            recyclerView.setAdapter(myRecyclerAdapter);
        }

//        listView= (ListView) findViewById(R.id.listView);
//        viewImage=(ImageView)findViewById(R.id.imageView);
//        CustomAdapter adapter=new CustomAdapter(this,list);
//        listView.setAdapter(adapter);
//        adapter=new MyRecyclerAdapter(list);
//        recyclerView.setAdapter(adapter);

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this,ChooseActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
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


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigationview_item_1:
                Toast.makeText(this,"click_1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigationview_item_2:
                Intent intent=new Intent(this,ChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.navigationview_item_3:
                Intent contectintent=new Intent(this,CaucasusContactActivity.class);
                startActivity(contectintent);
                break;
        }
        return true;
    }
}
