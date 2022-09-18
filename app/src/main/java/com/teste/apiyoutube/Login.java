package com.teste.apiyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity
{


    TextInputEditText inputUser, inputSenha;
    Button btnLogin;
    TextView txtLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUser = findViewById(R.id.edtLoginUsuario);
        inputSenha = findViewById(R.id.edtLoginSenha);
        btnLogin = findViewById(R.id.btnLogin);
        txtLogin = findViewById(R.id.txtLogin);
        progressBar = findViewById(R.id.progressLogin);


        txtLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String username;
                String password;
                username = String.valueOf(inputUser.getText());
                password = String.valueOf(inputSenha.getText());
                if (checarVazio(username, password))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];

                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];

                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://localhost/api_youtube/login.php", "POST", field, data);
                            if (putData.startPut())
                            {
                                if (putData.onComplete())
                                {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Login realizado com sucesso!"))
                                    {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Login realizado", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Todos os campos precisam ser preenchidos", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    private boolean checarVazio(String a, String b)
    {
        if (!a.equals("") && !b.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

}