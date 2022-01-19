package com.example.learnwordsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    View root;
    private TextView text_username;
    private EditText text_email;
    private TextView text_verification;
    private Button button_edit_username;
    private Button button_edit_email;
    private Button button_verify_email;
    private Button button_change_password;
    private Button button_delete_account;
    private View button_go_back;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    String Username;
    String Email;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {
        EditProfileFragment editProfileFragment=new EditProfileFragment();
        return editProfileFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        text_username = root.findViewById(R.id.text_username);
        text_email = root.findViewById(R.id.text_email);
        text_verification = root.findViewById(R.id.text_email_verification);
        button_edit_email = root.findViewById(R.id.button_edit_email);
        button_verify_email = root.findViewById(R.id.button_verify_email);
        button_change_password = root.findViewById(R.id.button_edit_password);
        button_delete_account = root.findViewById(R.id.button_delete_account);
        button_go_back = root.findViewById(R.id.button_go_back);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Username = firebaseUser.getDisplayName();
        Email = firebaseUser.getEmail();

        text_username.setText(Username);
        text_email.setText(Email);

        button_edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = text_email.getText().toString();
                if (TextUtils.isEmpty(email) == true){
                    text_email.setError("You need to enter your email");
                    return;
                }
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false){
                    text_email.setError("Email is incorrect");
                    return;
                }

                firebaseUser.updateEmail(email).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Successfully changed Email", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Changing Email failed: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        button_verify_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Link to verify your email was sent", Toast.LENGTH_LONG).show();
            }
        });

        button_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Change password", Toast.LENGTH_LONG).show();
            }
        });

        button_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser.delete().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Successfully deleted account", Toast.LENGTH_LONG).show();

                            Activity activity = getActivity();
                            Intent intent = new Intent(activity, LoginActivity.class);
                            startActivity(intent);
                            activity.finish();
                        }
                        else{
                            Toast.makeText(getContext(), "Deleting account failed: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        button_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Activity activity = getActivity();
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    activity.finish();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }
}