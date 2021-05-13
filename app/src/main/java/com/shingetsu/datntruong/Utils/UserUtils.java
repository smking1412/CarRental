package com.shingetsu.datntruong.Utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class UserUtils {
    public static void updateuser(View view, Map<String, Object> updateData) {
        FirebaseDatabase.getInstance()
                .getReference(Common.USER_INFO_REFERENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(updateData)
                .addOnFailureListener(e -> Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show())
                .addOnSuccessListener(unused -> Snackbar.make(view, "Cập nhật thành công", Snackbar.LENGTH_SHORT).show());
    }
}
