package com.example.knowyourgovernment;

import static android.graphics.Color.parseColor;
import static android.graphics.Color.rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knowyourgovernment.Model.Channel;
import com.example.knowyourgovernment.Model.Official;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class OfficialAvticity extends AppCompatActivity {
    private ImageView imghinhAnh;
    private TextView tvLocation, tvTitle, tvName, tvParty, tvAddress, tvPhone, tvEmail, tvWebsite;
    private ImageButton ibtnTwitter, ibtnGoogle, ibtnFacebook, ibtnYoutube;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_avticity);


        addControls();
        addEvents();


    }

    private void addEvents() {

        getInforOfficial();
    }

    private void addControls() {
        imghinhAnh = (ImageView) findViewById(R.id.imgHinhAnh);

        tvLocation = findViewById(R.id.tvLocation);
        tvTitle = findViewById(R.id.tvTitle);
        tvName = findViewById(R.id.tvName);
        tvParty = findViewById(R.id.tvParty);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvWebsite = findViewById(R.id.tvWebsite);

        ibtnTwitter = findViewById(R.id.ibtnTwitter);
        ibtnGoogle = findViewById(R.id.ibtnGoogle);
        ibtnFacebook = findViewById(R.id.ibtnFacebook);
        ibtnYoutube = findViewById(R.id.ibtnYoutube);

        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
    }

    public void getInforOfficial() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String loc = bundle.getString("location");
            Official official = (Official) bundle.getSerializable("official");


            String photoURL = official.getPhotoURL().trim();
            loadProfilePicture(photoURL.trim()); //load anh

            String title = (!official.getTitle().equals("") ? official.getTitle() : "No Data Provided");
            String name = (!official.getName().equals("") ? official.getName() : "No Data Provided");
            String party = (!official.getParty().equals("") ? official.getParty() : "No Data Provided");
            String location = (!loc.equals("") ? loc : "No Data Provided");
            String address = (!official.getAddress().equals("") ? official.getAddress() : "No Data Provided");
            String phone = (!official.getPhones().equals("") ? official.getPhones() : "No Data Provided");
            String email = (!official.getEmails().equals("") ? official.getEmails() : "No Data Provided");
            String urls = (!official.getUrls().equals("") ? official.getUrls() : "No Data Provided");


            tvTitle.setText(title);
            tvName.setText(name);
            tvParty.setText(party);
            tvLocation.setText(location);
            tvAddress.setText(address);
            tvAddress.setPaintFlags(tvAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //under line
            tvPhone.setText(phone);
            tvPhone.setPaintFlags(tvPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //under line
            tvEmail.setText(email);
            tvWebsite.setText(urls);


            ArrayList<Channel> channels = new ArrayList<>();
            channels = official.getChannels();
            for (Channel c : channels) {
                if(c.getType().equalsIgnoreCase("FaceBook")){
                    ibtnFacebook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkFaceBook(c.getId());
                        }
                    });
                }
                else{
                    ibtnFacebook.setVisibility(View.GONE);
                }

                if(c.getType().equalsIgnoreCase("Twitter")){
                    ibtnTwitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkTwitter(c.getId());
                        }
                    });
                }
                else{
                    ibtnTwitter.setVisibility(View.GONE);
                }
                if(c.getType().equalsIgnoreCase("GooglePlus")){
                    ibtnGoogle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkGooglePlus(c.getId());
                        }
                    });
                }
                else{
                    ibtnGoogle.setVisibility(View.GONE);
                }
                if(c.getType().equalsIgnoreCase("Youtube")){
                    ibtnYoutube.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linkYoutube(c.getId());
                        }
                    });
                }
                else{
                    ibtnYoutube.setVisibility(View.GONE);
                }
            }


            //change background color
            if (party.contains("Democratic Party"))
                rootLayout.setBackgroundColor(rgb(0,0,255));
            if (party.contains("Republican Party"))
                rootLayout.setBackgroundColor(rgb (255,0,0));
            if (party.contains("Nonpartisan") ||party.contains("Unknown") )
                rootLayout.setBackgroundColor(rgb (0,0,0));


            //send to PhotoActivity
            imghinhAnh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openPhotoActivity(loc,title,name,photoURL);
                }
            });
        }

    }

    public void linkFaceBook(String id){
        String FACEBOOK_URL = "https://www.facebook.com/" + id;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana",0).versionCode;
            if (versionCode >= 3002850) {
                //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else {
                //older versions of fb app
                urlToUse = "fb://page/" + id;
            }
        }
        catch ( PackageManager.NameNotFoundException e)
        {
            urlToUse = FACEBOOK_URL; //normal

        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void linkTwitter(String id){
         Intent intent = null;
         String name = id;
         try {
           // get the Twitter app if possible
             getPackageManager().getPackageInfo("com.twitter.android", 0);
             intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         } catch (Exception e) {
            // no Twitter app, revert to browser
             intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" +name));
         }
         startActivity(intent);
    }

    public void linkGooglePlus(String id){
        String name = id;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", name);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + name)));
        }
    }
    void linkYoutube(String id){
        String name = id;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
        }
    }
    void loadProfilePicture(String URL)
    {

        if(URL.equals(""))
        {
            imghinhAnh.setImageResource(R.drawable.no_image);
        }
        else
        {
            Log.d("error", "bp: loadProfilePicture: URL: " + URL);


            Picasso.get()
                    .load(URL)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .resize(350,350)
                    .centerCrop()
                    .into(imghinhAnh);
        }
    }
    public void openPhotoActivity(String location, String title, String name, String url ){
        Intent intent = new Intent(OfficialAvticity.this, PhotoActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("location", location);
        bundle.putString("title", title);
        bundle.putString("name", name);
        bundle.putString("url", url);

        intent.putExtras(bundle);
        startActivity(intent);
    }

}