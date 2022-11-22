package com.sinhvien.android_java_onmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinhvien.android_java_onmovie.adapter.SeasonAdapter;
import com.sinhvien.android_java_onmovie.model.Country;
import com.sinhvien.android_java_onmovie.model.Genre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieDetail extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    private SeasonAdapter seasonAdapter;
    private RecyclerView rv_season;

    DatabaseReference mDB;
    FirebaseAuth fAuth;

    ArrayList f_genres, f_genres_name, watch_lists, f_casts;

    ImageView backdrop_img, addlist, back;
    TextView tv_content_limitedAge, tv_country, tv_name, tv_content, tv_genres;

    String id, name, backdrop, age, country, content, userID, names = "";
    Boolean checkAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        /* Lấy thông tin bundles từ trang home */
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        backdrop  = bundle.getString("backdrop");
        name  = bundle.getString("name");
        age  = bundle.getString("limitedAge");
        country  = bundle.getString("country");
        content = bundle.getString("desc");
        f_genres = bundle.getStringArrayList("genres");
        f_casts = bundle.getStringArrayList("cast");

        /* Khai báo các view items */
        viewPager2 = findViewById(R.id.viewpager_detail);
        tabLayout = findViewById(R.id.bottom_navigation);

        backdrop_img = findViewById(R.id.backdrop_img);
        tv_content_limitedAge = findViewById(R.id.tv_content_limitedAge);
        tv_country = findViewById(R.id.tv_country);
        tv_name = findViewById(R.id.tv_movie_name);
        tv_content = findViewById(R.id.txtContent);
        tv_genres = findViewById(R.id.txtGen);

        /* Set nút back */
        back = findViewById(R.id.back_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /* Xử lý nút thêm vào danh sách yêu thích */
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        mDB = FirebaseDatabase.getInstance().getReference();

        addlist = findViewById(R.id.ic_addlist);

        // Set onclick add to movie list
        addlist.setOnClickListener(view -> {
            if(checkAdd.equals(true)) {
                for(int i = 0; i < watch_lists.size(); i++) {
                    if(watch_lists.get(i).equals(id)) {
                       watch_lists.remove(i);
                    }
                }
                mDB.child("users").child(userID).child("watch_lists").setValue(watch_lists);
                Log.d("XXX", "" + convertArrayListToHashMap(watch_lists));
                Toast.makeText(this, "Xoa", Toast.LENGTH_SHORT).show();
            }
            else {
                watch_lists.add(id);
                mDB.child("users").child(userID).child("watch_lists").updateChildren(convertArrayListToHashMap(watch_lists));
                Toast.makeText(this, "Them", Toast.LENGTH_SHORT).show();
            }

        });

        setInformation();


        DetailAdapter detailAdapter = new DetailAdapter(this);
        viewPager2.setAdapter(detailAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("TRAILER");
                        break;
                    case 1:
                        tab.setText("DIỄN VIÊN");
                        break;
                    case 2:
                        tab.setText("ĐỀ XUẤT");
                        break;
                }
            }
        }).attach();
    }

    public void checkAddFilm () {
        mDB.child("users").child(userID).child("watch_lists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("CCC", "" + snapshot);
                if(snapshot.getValue() == null) {
                    checkAdd = false;
                    watch_lists = new ArrayList();
                } else {
                    ArrayList listFilm = (ArrayList) snapshot.getValue();
                    watch_lists = new ArrayList(listFilm);

                    if(listFilm.contains(id)){
                        addlist.setImageResource(R.drawable.ic_addlist);
                        checkAdd = true;
                    }
                    else {
                        addlist.setImageResource(R.drawable.ic_nonaddlist);
                        checkAdd = false;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCountries() {
        mDB = FirebaseDatabase.getInstance().getReference();
        mDB.child("countries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot items: snapshot.getChildren()) {
                    Country item = items.getValue(Country.class);
                    if(item.getId().equals(country))
                        tv_country.setText(item.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadGenres() {
        f_genres_name = new ArrayList();
        mDB = FirebaseDatabase.getInstance().getReference();
        mDB.child("genres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot items: snapshot.getChildren()) {
                    Genre item = items.getValue(Genre.class);
                    for(int i = 0; i < f_genres.size(); i++) {
                        String f_genre = (String) f_genres.get(i);
                        if(item.getId().equals(f_genre)) {
                            f_genres_name.add(item.getName());
                        }
                    }
                }
                for(int i = 0; i < f_genres_name.size(); i++) {
                    if(i < f_genres_name.size() - 1) {
                        names += f_genres_name.get(i) + ", ";
                    } else {
                        names += f_genres_name.get(i);
                    }
                }
                tv_genres.setText(names);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setInformation() {
        /* Set texts */
        tv_name.setText(name);
        tv_content_limitedAge.setText(age);
        tv_content.setText(content);

        /* Set backdrop */
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference backdropFilm = storageReference.child("images/backdrops/"+ backdrop);
        backdropFilm.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(backdrop_img);
            }
        });

        /* Load rest informations */
        loadCountries();
        loadGenres();
        checkAddFilm();
    }

    private static HashMap<String, String>
    convertArrayListToHashMap(ArrayList<String> arrayList)
    {
        HashMap<String, String> hashMap = new HashMap<>();

        for(int i = 0; i < arrayList.size(); i++) {
            hashMap.put(String.valueOf(i), arrayList.get(i));
        }

        return hashMap;
    }

}

