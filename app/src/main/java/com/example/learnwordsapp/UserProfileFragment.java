package com.example.learnwordsapp.Auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.learnwordsapp.R;


public class UserProfileFragment extends Fragment {
    public static final int CAMERA_REQUEST_CODE = 102;
    View root;
    Switch notifSwitch;
    Switch soundSwitch;
    Button editprofileBtn;
    Button editavatarBtn;
    Button logoutBtn;
    ImageView avatar;

    public UserProfileFragment() {
        // Required empty public constructor
    }



    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_profile, container, false);
        notifSwitch = root.findViewById(R.id.switch_notification);
        soundSwitch = root.findViewById(R.id.switch_sound);
        editprofileBtn = root.findViewById(R.id.button_editprofile);
        editavatarBtn = root.findViewById(R.id.button_editavatar);
        logoutBtn = root.findViewById(R.id.button_logout);

        editavatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //askCameraPermissions();
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,CAMERA_REQUEST_CODE);
            }
        });

        editprofileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {

                } else {
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    //for Android 5-7
                    intent.putExtra("app_package", getActivity().getPackageName());
                    intent.putExtra("app_uid", getActivity().getApplicationInfo().uid);
                    // for Android 8 and above
                    intent.putExtra("android.provider.extra.APP_PACKAGE", getActivity().getPackageName());

                    startActivity(intent);

                }
            }
        });

        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST_CODE){
            Bitmap image=(Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(image);
            //saveAvatar(avatar);
        }
    }
}