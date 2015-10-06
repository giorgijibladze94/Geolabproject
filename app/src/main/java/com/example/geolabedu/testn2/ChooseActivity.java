package com.example.geolabedu.testn2;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.geolabedu.testn2.Adapter.CustomViewPagerAdapter;
import com.example.geolabedu.testn2.database.VehiclContracts;
import com.example.geolabedu.testn2.database.VehicleData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ChooseActivity extends ActionBarActivity {

    String path;
    ViewPager viewPager;
    long l;
    ImageView image;
    EditText editmail,editnomeri,editweli,editdisck,choose_edittext;
    String st;
    Button imagebutton;
    public static CardView cardView;
    Uri url;
    Spinner spinnercate,spinnermodel,spinnerweli;
    ArrayAdapter<CharSequence> spinnerAdapter,arrayAdapter;
    String categ,modeli,weli;
    Toolbar choostoolbar;

    ArrayList<String> arrayList;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        arrayList = new ArrayList<>();
        adapter=new CustomViewPagerAdapter(this, arrayList);


        image= (ImageView) findViewById(R.id.image);
        spinnerweli= (Spinner) findViewById(R.id.spinnerweli);
        spinnercate= (Spinner) findViewById(R.id.categ);
        spinnermodel= (Spinner) findViewById(R.id.modeli);
        cardView= (CardView) findViewById(R.id.cardlist_item);
        editmail= (EditText) findViewById(R.id.mail);
        editnomeri= (EditText) findViewById(R.id.phone);
        editdisck= (EditText) findViewById(R.id.diskription);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        //bt= (Button) findViewById(R.id.button);
        imagebutton= (Button) findViewById(R.id.imagebutton);
        //imageView= (ImageView) findViewById(R.id.imageView);

        choostoolbar= (Toolbar) findViewById(R.id.choos_toolbar);

        choose_edittext= (EditText) findViewById(R.id.choose_edittext);

        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/bpg_mtavruli_normal.ttf");
        choose_edittext.setTypeface(typeface);

        editmail.setTypeface(typeface);
        editnomeri.setTypeface(typeface);
        editdisck.setTypeface(typeface);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.statusrose));

        setSupportActionBar(choostoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//back ghilakis damateba

        getSupportActionBar().setTitle(null);

        spinnerfill();

        if(getIntent().hasExtra("edit")){
            editdata();
        }

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

//        bt.setOnClickListener(new View.OnClickListener() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onClick(View v) {
//                String string=st;
//                String mail=String.valueOf(editmail.getText());
//                String nomeri=String.valueOf(editnomeri.getText());
//
//                //spinneris itemis amogheba
//                weli=spinnerweli.getSelectedItem().toString();
//                modeli=spinnermodel.getSelectedItem().toString();
//
//                String decskr= String.valueOf(editdisck.getText());
//                VehicleData vehicleData=new VehicleData(string,mail,nomeri,categ,modeli,weli,decskr,dataformat);
//                List list=new ArrayList();
//                list.add(vehicleData);
//
//
//                /*
//                * bazidan unda amovshalo zedmeti informaciebi
//                * */
//
//
//                ContentValues values=new ContentValues();
//                VehicleData data= (VehicleData) list.get(0);
//                values.put(VehiclContracts.VEHICLE_IMAGE, data.getImage());
//                values.put(VehiclContracts.VEHICLE_PERSON_EMAIL,data.getMail());
//                values.put(VehiclContracts.VEHICLE_PERSON_PHONE,data.getNomeri());
//                values.put(VehiclContracts.VEHICLE_CATEGORY,data.getCateg());
//                values.put(VehiclContracts.VEHICLE_MODEL,data.getModeli());
//                values.put(VehiclContracts.VEHICLE_AGE,data.getWeli());
//                values.put(VehiclContracts.VEHICLE_DESCRIPTION,data.getDecskr());
//                values.put(VehiclContracts.VEHICLE_DATE_ADD,data.getCalendar());
//                FirstActivity.sqLiteDatabase.insert(VehiclContracts.VEHICLE_TABLE_NAME, null, values);
//
//                Intent intent=new Intent(ChooseActivity.this,FirstActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void editdata(){
        Toast.makeText(this,"raghaca",Toast.LENGTH_LONG).show();
        int data= (int) getIntent().getExtras().getSerializable("edit");

        Cursor c=FirstActivity.sqLiteDatabase.query(VehiclContracts.VEHICLE_TABLE_NAME,null,VehiclContracts.VEHICLE_ID + "  ="+ data,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String mail=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_PERSON_EMAIL));
                String nomeri=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_PERSON_PHONE));
                String weli=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_AGE));
                String desc=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_DESCRIPTION));

//                spinnerAdapter=ArrayAdapter.createFromResource(this,R.array.spinner_array_categ,android.R.layout.simple_spinner_item);
//                spinnercate.setAdapter(spinnerAdapter);


                editmail.setText(mail);
                editnomeri.setText(nomeri);
                editweli.setText(weli);
                editdisck.setText(desc);
            }while (c.moveToNext());
        }
    }

    private void spinnerfill() {
        spinnerAdapter=ArrayAdapter.createFromResource(ChooseActivity.this, R.array.spinner_array_categ, android.R.layout.simple_spinner_item);
        spinnercate.setAdapter(spinnerAdapter);
        arrayAdapter=ArrayAdapter.createFromResource(ChooseActivity.this,R.array.vehicle_age,android.R.layout.simple_spinner_item);
        spinnerweli.setAdapter(arrayAdapter);
        spinnercate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //aq unda davwero rom meore spinneri iyos pirvelze damokidebuli
                int count=parent.getCount();
                categ= (String) spinnercate.getItemAtPosition(position);
                switch (position){
                    case 0:
                        ArrayAdapter adapter=ArrayAdapter.createFromResource(ChooseActivity.this,R.array.spinner_array_model_null,android.R.layout.simple_spinner_item);
                        spinnermodel.setAdapter(adapter);
                        break;
                    case 1:
                        ArrayAdapter adapter_bmw=ArrayAdapter.createFromResource(ChooseActivity.this,R.array.spinner_array_model_bmw,android.R.layout.simple_spinner_item);
                        spinnermodel.setAdapter(adapter_bmw);
                        break;
                    case 2:
                        ArrayAdapter adapter_merc=ArrayAdapter.createFromResource(ChooseActivity.this,R.array.spinner_array_model_merc,android.R.layout.simple_spinner_item);
                        spinnermodel.setAdapter(adapter_merc);
                        break;
                    //dasamatebelia case-ebi :)))))))))
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.SECOND,1);
        l=calendar.getTimeInMillis();

        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), l + ".jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    CustomViewPagerAdapter adapter;
    int i=500,b=500;
    Bitmap compresbitmap;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals(l+".jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    compresbitmap=Bitmap.createScaledBitmap(bitmap,i,b,true);
                    url=getImageUri(ChooseActivity.this,compresbitmap);

                    st=getRealPathFromURI(url);


                    arrayList.add(st);

                    if(arrayList!=null){
                        image.setVisibility(View.INVISIBLE);//xilvadoba
                        adapter.notifyDataSetChanged();
                        viewPager.setAdapter(adapter);
                    }


                    path= android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");

                compresbitmap=Bitmap.createScaledBitmap(thumbnail,i,b,true);

                url=getImageUri(this,compresbitmap);
                st=getRealPathFromURI(url);

                arrayList.add(st);
                if(arrayList!=null){
                    image.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                    viewPager.setAdapter(adapter);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case android.R.id.home:
                Intent myIntent = new Intent(getApplicationContext(), FirstActivity.class);
                startActivityForResult(myIntent, 0);
                return true;
            case R.id.save:
                Toast.makeText(ChooseActivity.this,"click",Toast.LENGTH_SHORT).show();
                //String string=st;
                String mail=String.valueOf(editmail.getText());
                String nomeri=String.valueOf(editnomeri.getText());

                Calendar c=Calendar.getInstance();
                SimpleDateFormat sdf=new SimpleDateFormat("MMM/dd/yyyy");
                final String dataformat=sdf.format(c.getTime());

                //spinneris itemis amogheba
                weli=spinnerweli.getSelectedItem().toString();
                modeli=spinnermodel.getSelectedItem().toString();

                String decskr= String.valueOf(editdisck.getText());
                VehicleData vehicleData=new VehicleData(arrayList,mail,nomeri,categ,modeli,weli,decskr,dataformat);
                List list=new ArrayList();
                list.add(vehicleData);


                /*
                * bazidan unda amovshalo zedmeti informaciebi
                * */


                ContentValues values=new ContentValues();
                VehicleData data= (VehicleData) list.get(0);

                values.put(VehiclContracts.VEHICLE_PERSON_EMAIL, data.getMail());
                values.put(VehiclContracts.VEHICLE_PERSON_PHONE, data.getNomeri());
                values.put(VehiclContracts.VEHICLE_CATEGORY, data.getCateg());
                values.put(VehiclContracts.VEHICLE_MODEL, data.getModeli());
                values.put(VehiclContracts.VEHICLE_AGE, data.getWeli());

                values.put(VehiclContracts.VEHICLE_MAIN_IMAGE,arrayList.get(0));//amomaqvs listidan 0vani elementi rom davayeno recyclerviewze

                values.put(VehiclContracts.VEHICLE_DESCRIPTION, data.getDecskr());
                values.put(VehiclContracts.VEHICLE_DATE_ADD, data.getCalendar());


                long id5  = FirstActivity.sqLiteDatabase.insert(VehiclContracts.VEHICLE_TABLE_NAME, null, values);

                for(int i=0;i<arrayList.size();i++) {
                    ContentValues values1=new ContentValues();
                    values1.put(VehiclContracts.VEHICLE_PARENT_ID,id5);
                    values1.put(VehiclContracts.VEHICLE_IMAGE, data.getImage(i));
                    FirstActivity.sqLiteDatabase.insert(VehiclContracts.VEHICLE_IMAGE_TABLE,null,values1);
                }

                Intent intent=new Intent(ChooseActivity.this,FirstActivity.class);
                startActivity(intent);
                return true;

        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
