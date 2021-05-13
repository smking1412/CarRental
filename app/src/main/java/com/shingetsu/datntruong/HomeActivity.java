package com.shingetsu.datntruong;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shingetsu.datntruong.Models.User;
import com.shingetsu.datntruong.Utils.Common;
import com.shingetsu.datntruong.Utils.UserUtils;
import com.shingetsu.datntruong.databinding.ActivityHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 500 ;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;

    private AlertDialog waitingDialog;
    private StorageReference storageReference;
    private Uri imageUri;
    private ImageView img_avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

//        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            if (data != null && data.getData() != null){
                imageUri = data.getData();
                img_avatar.setImageURI(imageUri);

                showDialogUpload();
            }
        }
    }

    private void showDialogUpload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Đổi avatar").setMessage("Bạn có chắc muốn đổi avatar ?")
                .setNegativeButton("KHÔNG", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("ĐỒNG Ý", (dialogInterface, i) -> {
                   if (imageUri != null){
                       waitingDialog.setMessage("Up Loading...");
                       waitingDialog.show();

                       String unique_name = FirebaseAuth.getInstance().getCurrentUser().getUid();
                       StorageReference avatarFolder = storageReference.child("avatar/"+unique_name);

                       avatarFolder.putFile(imageUri)
                               .addOnFailureListener(e -> {
                                   waitingDialog.dismiss();
                                   Snackbar.make(drawer, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                               }).addOnCompleteListener(task -> {
                           if(task.isSuccessful()){
                               avatarFolder.getDownloadUrl().addOnSuccessListener(uri -> {
                                   Map<String,Object> updateData =  new HashMap<>();
                                   updateData.put("avatar",uri.toString());

                                   UserUtils.updateuser(drawer,updateData);
                               });
                           }
                           waitingDialog.dismiss();
                       }).addOnProgressListener(snapshot -> {
                           double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                           waitingDialog.setMessage(new StringBuilder("Uploading: ").append(progress).append("%"));
                       });
                   }

                }).setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(getResources().getColor(R.color.colorAccent));

        });
        dialog.show();
    }

    private void init() {

        waitingDialog  = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("waiting...")
                .create();
        storageReference = FirebaseStorage.getInstance().getReference();

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Đăng Xuất").setMessage("Bạn có chắc chắn đăng xuất ?")
                        .setNegativeButton("KHÔNG", (dialogInterface, i) -> dialogInterface.dismiss())
                        .setPositiveButton("ĐỒNG Ý", (dialogInterface, i) -> {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        })
                        .setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(dialogInterface -> {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(getResources().getColor(R.color.colorAccent));

                });
                dialog.show();
            }
            return true;
        });

        //set data
        View headerview = navigationView.getHeaderView(0);
        TextView txt_name = (TextView) headerview.findViewById(R.id.txt_name_user);
        TextView txt_mail = (TextView) headerview.findViewById(R.id.txt_mail);
        img_avatar = (ImageView) headerview.findViewById(R.id.img_avatar);

        txt_name.setText(Common.buildWelcomeMessage());
        txt_mail.setText(Common.userModel != null ? Common.userModel.getEmail() : "");

        img_avatar.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        });
        if(Common.userModel != null && Common.userModel.getAvatar() != null &&
                !TextUtils.isEmpty(Common.userModel.getAvatar()))
        {
            Glide.with(this)
                    .load(Common.userModel.getAvatar())
                    .into(img_avatar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}