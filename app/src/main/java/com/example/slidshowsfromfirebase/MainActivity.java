package com.example.slidshowsfromfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemChangeListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageSlider mainSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // making Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN  ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainSlider = findViewById(R.id.image_slider);
        // now create an arraylist of model already this dependency gives a model class
        final ArrayList<SlideModel> list = new ArrayList<>();
        // now fetch the data from firebase
        FirebaseDatabase.getInstance().getReference().child("Slider")
                // now add a method of add listner
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       //  now it will b e run in the loop and fetch to every  single node
                        for (DataSnapshot data : snapshot.getChildren()){
                            // now from it object fetch the every data
                            // this three property are given by this model class - url , title , scaletype
                            list.add(new SlideModel(data.child("url").getValue().toString() , data.child("title").getValue().toString() , ScaleTypes.FIT));


                        }
                        // now every node get fetch
                        // now set it on our slider XML
                        mainSlider.setImageList(list);

                        // now making the index of the each slider
                        mainSlider.setItemChangeListener(new ItemChangeListener() {
                            @Override
                            public void onItemChanged(int i) {
                                // on click over the any item image show its index position
                                // simple done by model class object
                                Toast.makeText(getApplicationContext() , list.get(i).getTitle().toString() , Toast.LENGTH_SHORT).show();
                                
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}