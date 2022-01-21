package com.example.learnwordsapp;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserProfileFragment extends Fragment {
    private static final int REQUEST_CODE = 0101;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    View root;
    Switch notif_switch;
    Switch sound_switch;
    private TextView location_text;
    private TextView language_text;
    Button edit_profile_button;
    Button edit_avatar_button;
    Button change_language_button;
    Button statistics_button;
    Button logout_button;
    ImageView avatar_image;

    private MediaPlayer musicP;
    private Switch darkMode_switch;

    private FusedLocationProviderClient fusedLocationClient;

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
        loadLocale();
        root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        notif_switch = root.findViewById(R.id.switch_notification);
        sound_switch = root.findViewById(R.id.switch_sound);
        location_text = root.findViewById(R.id.location_text);
        edit_profile_button = root.findViewById(R.id.button_editprofile);
        edit_avatar_button = root.findViewById(R.id.button_editavatar);
        logout_button = root.findViewById(R.id.button_logout);
        statistics_button = root.findViewById(R.id.button_statistics);
        avatar_image = root.findViewById(R.id.image_avatar);
        change_language_button = root.findViewById(R.id.button_changeMyLang);
        darkMode_switch = root.findViewById(R.id.switch_mode);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(string.app_name));

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(container.getContext());

        permLocateUser();

        edit_avatar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //askCameraPermissions();
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, CAMERA_REQUEST_CODE);
            }
        });

        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Activity activity = getActivity();
                    Intent intent = new Intent(activity, EditProfileActivity.class);
                    startActivity(intent);
                    assert activity != null;
                    activity.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                try {
                    Activity activity = getActivity();
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                    assert activity != null;
                    activity.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        change_language_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });

        statistics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = StatisticsFragment.newInstance();
                FragmentTransaction r = getActivity().getSupportFragmentManager().beginTransaction();
                r.replace(R.id.frame_layout, f);
                r.commit();
            }
        });

        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    musicP.setLooping(false);
                    musicP.stop();

                } else {
                    musicP = MediaPlayer.create(root.getContext(), R.raw.music);
                    musicP.setLooping(true);
                    musicP.start();
                }
            }
        });

        notif_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        darkMode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            avatar_image.setImageBitmap(image);
            //saveAvatar(avatar);
        }
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {

        }
    }

    private void permLocateUser() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }
        locateUser();
    }

    private void locateUser() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            location_text.setText(addressList.get(0).getCountryName());
                        } catch (IOException e) {
                            location_text.setText(R.string.no_location);
                            e.printStackTrace();
                        }
                    } else {
                        location_text.setText(R.string.no_location);
                    }
                }
            });
        }
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {getString(R.string.polishLanguage), getString(R.string.englishLanguage)};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle(R.string.chooseLanguage);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("pl");
                } else if (i == 1) {
                    setLocale("en");
                }
                Fragment f = UserProfileFragment.newInstance();
                FragmentTransaction r = getActivity().getSupportFragmentManager().beginTransaction();
                r.replace(R.id.frame_layout, f);
                r.commit();

                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("MyLanguage", lang);
        editor.apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("MyLanguage", "");
        setLocale(language);
    }
}