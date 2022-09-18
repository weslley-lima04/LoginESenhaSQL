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

public class SignUp extends AppCompatActivity
{

    TextInputEditText inputFullname, inputEmail, inputUser, inputSenha;
    Button btnCadastro;
    TextView txtCadastro;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputFullname = findViewById(R.id.edtFullname);
        inputEmail = findViewById(R.id.edtEmail);
        inputUser = findViewById(R.id.edtUsername);
        inputSenha = findViewById(R.id.edtPassword);

        btnCadastro = findViewById(R.id.btnCadastro);
        txtCadastro = findViewById(R.id.txtCadastro);
        progressBar = findViewById(R.id.progress);

        txtCadastro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        btnCadastro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String fullname, email, username, password;

                fullname = String.valueOf(inputFullname.getText());
                email = String.valueOf(inputEmail.getText());
                username = String.valueOf(inputUser.getText());
                password = String.valueOf(inputSenha.getText());



                if (checarVazio(fullname, email, username, password))
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
                            String[] field = {"fullname", "username", "password", "email"};

                            //Creating array for data
                            String[] data = {fullname, username, password, email};

                            PutData putData = new PutData("http://192.168.1.3/api_youtube/signup.php", "POST", field, data);
                            if (putData.startPut())
                            {
                                if (putData.onComplete())
                                {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Cadastro realizado com sucesso!"))
                                    {
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Cadastro realizado", Toast.LENGTH_LONG).show();
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

    private boolean checarVazio(String a, String b, String c, String d)
    {
        if (!a.equals("") && !b.equals("") && !c.equals("") && !d.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

}