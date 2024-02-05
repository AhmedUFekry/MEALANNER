package com.example.mealanner.UILayer.LoginActivity.SignUpFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {
    Button loginBtn;
    Button signUPbtn;

    TextView userName;
    TextView password;
    TextView confirmPassword;
    FirebaseAuth mAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginBtn = view.findViewById(R.id.loginfomsignupBtn);
        userName = view.findViewById(R.id.usernamesignUp);
        password  = view.findViewById(R.id.passwordsignUp);
        confirmPassword  = view.findViewById(R.id.confirmpasswordsignUp);
        signUPbtn = view.findViewById(R.id.signupfomsignupBtn);

        signUPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email , pass , confPass;
                email = String.valueOf(userName.getText());
                pass = String.valueOf(password.getText());
                confPass = String.valueOf(confirmPassword.getText());
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity().getApplicationContext(),"pls enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getActivity().getApplicationContext(),"pls enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confPass)){
                    Toast.makeText(getActivity().getApplicationContext(),"pls enter your confirmed password",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if(password.getText() != confirmPassword.getText()){
                    Toast.makeText(getActivity().getApplicationContext(),"Check your Password Again",Toast.LENGTH_SHORT).show();
                    return;
                }*/
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success");
                                    Toast.makeText(getActivity().getApplicationContext(), "Authentication Succeded.",Toast.LENGTH_SHORT).show();

                                    //FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity().getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });
    }
}