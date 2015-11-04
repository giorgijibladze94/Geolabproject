package com.example.geolabedu.autoservice;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geolabedu.autoservice.Adapter.MyRecyclerAdapter;
import com.example.geolabedu.autoservice.database.DBHelper;
import com.example.geolabedu.autoservice.database.VehiclContracts;
import com.example.geolabedu.autoservice.database.VehicleData;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class FirstActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    public static SQLiteDatabase sqLiteDatabase;
    android.support.v7.widget.Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CallbackManager callbackManager;
    AccessToken accessToken;
    TextView textView;
    String name, url;
    ImageView image;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_first);

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textView);
        image = (ImageView) findViewById(R.id.imageView);

        fblogin();

        getSupportActionBar().setTitle(null);

        navigationView = (NavigationView) findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);//akontrolebs ghiaa tu ara
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);//itokshi ar vici ras aketebs
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        //statusbar change color
        Window window = getWindow();
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
        if (sqLiteDatabase.isDatabaseIntegrityOk()) {
            Cursor c = sqLiteDatabase.query(VehiclContracts.VEHICLE_TABLE_NAME, null, null, null, null, null, null);

            ArrayList list = new ArrayList();

            if (c.moveToFirst()) {
                do {
                    String image = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_MAIN_IMAGE));
                    String mail = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_PERSON_EMAIL));
                    String nomeri = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_PERSON_PHONE));
                    String categ = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_CATEGORY));
                    String modeli = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_MODEL));
                    String weli = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_AGE));
                    Integer ID = c.getInt(c.getColumnIndex(VehiclContracts.VEHICLE_ID));
                    String decsk = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_DESCRIPTION));
                    String calendar = c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_DATE_ADD));
                    VehicleData item2 = new VehicleData(ID, image, mail, nomeri, categ, modeli, weli, decsk, calendar);

                    list.add(item2);
                } while (c.moveToNext());
            }
            MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, list);
            recyclerView.setAdapter(adapter);

        } else {
            ArrayList list = new ArrayList();
            VehicleData data = new VehicleData();
            list.add(data);
            MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(this, list);
            recyclerView.setAdapter(myRecyclerAdapter);
        }

//        listView= (ListView) findViewById(R.id.listView);
//        viewImage=(ImageView)findViewById(R.id.imageView);
//        CustomAdapter adapter=new CustomAdapter(this,list);
//        listView.setAdapter(adapter);
//        adapter=new MyRecyclerAdapter(list);
//        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, ChooseActivity.class);
                startActivity(intent);

            }
        });


    }

    private void fblogin() {
        accessToken = AccessToken.getCurrentAccessToken();

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Bundle params = new Bundle();
                params.putString("fields", "id,name,email,gender,cover,picture.type(large)");
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "me",
                        params,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                if (response != null) {
                                    try {
                                        JSONObject data = response.getJSONObject();
                                        name = response.getJSONObject().getString("name");
                                        textView.setText(name);
                                        if (data.has("picture")) {
                                            url = data.getJSONObject("picture").getJSONObject("data").getString("url");

                                            /*
                                            * picasoti suratis chamotvirtva da bitmapshi chagdeba,rata headershi wriuli surati gamochndes
                                            * */
                                            Picasso.with(FirstActivity.this)
                                                    .load(url)
                                                    .into(new com.squareup.picasso.Target() {
                                                        @Override
                                                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                                            new Thread(new Runnable() {

                                                                @Override
                                                                public void run() {

                                                                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/image1.jpg");
                                                                    try {
                                                                        file.createNewFile();
                                                                        FileOutputStream ostream = new FileOutputStream(file);
                                                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                                                                        image.setImageBitmap(getCircleBitmap(bitmap));
                                                                        ostream.close();
                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }

                                                                }
                                                            }).start();
                                                        }

                                                        @Override
                                                        public void onBitmapFailed(Drawable errorDrawable) {

                                                        }

                                                        @Override
                                                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                                                        }
                                                    });

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
                Toast.makeText(this, "click_1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigationview_item_2:
                Intent intent = new Intent(this, ChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.navigationview_item_3:
                Intent contectintent = new Intent(this, CaucasusContactActivity.class);
                startActivity(contectintent);
                break;
        }
        return true;
    }
}
