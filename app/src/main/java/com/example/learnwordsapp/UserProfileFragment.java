package com.example.learnwordsapp;

import static android.content.Context.MODE_PRIVATE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserProfileFragment extends Fragment {
    private static final int REQUEST_CODE = 0101;

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
    Bitmap profileImage;
    Boolean cameraGranted = false;
    String currentPhotoPath;
    String currentPhotoPath_s;
    Uri outputFileUri = null;
    Uri outputFileUri_s = null;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Switch darkMode_switch;


    public static final String MyPREFERENCES = "nightModePref";
    public static final String KEY_ISNIGHTMODE = "nightModePref";
    SharedPreferences sharedPreferences;


    private FusedLocationProviderClient fusedLocationClient;

    private ActivityResultLauncher<String> mCameraPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    String TAG = "CAMERA_PERM_REQ";
                    if(result) {
                        Log.e(TAG, "onActivityResult: PERMISSION GRANTED");
                    } else {
                        Log.e(TAG, "onActivityResult: PERMISSION DENIED");
                    }
                }
            });

    ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), outputFileUri);

                            Bitmap profileImage = Bitmap.createScaledBitmap(bitmap,  120 ,120, true);
                            saveBitmapFile(profileImage, currentPhotoPath_s);
                            //profileImage = RotateBitmap(profileImage,270);
                            uploadImage(currentPhotoPath_s);
                            avatar_image.setImageBitmap(profileImage);

                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            });

    private ActivityResultLauncher<String> storagePermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    String TAG = "STORAE_PERM_REQ";
                    if(result) {
                        Log.e(TAG, "onActivityResult: PERMISSION GRANTED");

                    } else {

                        Log.e(TAG, "onActivityResult: PERMISSION DENIED");
                    }
                }
            });



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

        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        notif_switch = root.findViewById(R.id.switch_notification);
       // sound_switch = root.findViewById(R.id.switch_sound);
        location_text = root.findViewById(R.id.location_text);
        edit_profile_button = root.findViewById(R.id.button_editprofile);
        edit_avatar_button = root.findViewById(R.id.button_editavatar);
        logout_button = root.findViewById(R.id.button_logout);
        statistics_button = root.findViewById(R.id.button_statistics);
        avatar_image = root.findViewById(R.id.image_avatar);
        change_language_button = root.findViewById(R.id.button_changeMyLang);
        darkMode_switch = root.findViewById(R.id.switch_mode);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(container.getContext());


        checkNightModeActivated();

        darkMode_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightModeState(true);
                getActivity().recreate();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightModeState(false);
                getActivity().recreate();            }

        });


        permLocateUser();

        dowloadProfileImage();

        edit_avatar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.CAMERA)
                        !=PackageManager.PERMISSION_GRANTED) {
                    mCameraPermissionResult.launch(Manifest.permission.CAMERA);
                }
                if(ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        !=PackageManager.PERMISSION_GRANTED){
                    storagePermissionResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                try {
                    saveImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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


//        darkMode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                }
//            }
//        });

        return root;
    }


    private void saveNightModeState(boolean nightMode) {

        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);
        editor.apply();

    }

    public void checkNightModeActivated(){
        if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)){
            darkMode_switch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            darkMode_switch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void uploadImage(@NonNull String path) {
        String UID = mAuth.getCurrentUser().getUid();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("/Users");
        storageRef
                .child(UID)
                .child("PhotoURI")
                .setValue(path)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "File '" + path + "' is your profile image", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Setting your profile image failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void dowloadProfileImage() {
        String UID = mAuth.getCurrentUser().getUid();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        Task<DataSnapshot> storageRef = storage.getReference("/Users")
                .child(UID)
                .child("PhotoURI")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Object ori = dataSnapshot.getValue();
                        if(ori==null)
                            return;

                        currentPhotoPath_s = ori.toString();
                        outputFileUri_s = Uri.fromFile(new File(currentPhotoPath_s));

                        try {
                            Bitmap profileImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), outputFileUri_s);
                            avatar_image.setImageBitmap(profileImage);

                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });


    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void saveImage() throws IOException {

        File image = createImageFile();
        outputFileUri = FileProvider.getUriForFile(getContext(),
                "com.example.learnwordsapp.fileprovider",
                image);

            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraResultLauncher.launch(camera);

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_LW_" + timeStamp + "_";
        String imageFileName2 = "IMG_LW_" + timeStamp + "_S";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image1 = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        currentPhotoPath = image1.getAbsolutePath();
        outputFileUri = Uri.fromFile(image1);


        File image2 = File.createTempFile(
                imageFileName2,
                ".jpg",
                storageDir
        );
        currentPhotoPath_s = image2.getAbsolutePath();
        outputFileUri_s = Uri.fromFile(image2);

        return image1;
    }

    private void saveBitmapFile(@NonNull Bitmap bmp, @NonNull String f){
        String file_path = f;
        File file = new File(file_path);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            if(fOut != null){
                bmp.compress(Bitmap.CompressFormat.JPEG, 95, fOut);
                fOut.flush();
                fOut.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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