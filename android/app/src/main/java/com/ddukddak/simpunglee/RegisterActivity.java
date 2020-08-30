package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    String url = "http://3.35.65.128:8080/simpunglee/";

    EditText name;
    EditText nickname;
    EditText email;
    EditText password;
    EditText passwordck;
    EditText phonenumber;
    Button register;
    TextView valiateButton;
    String et_nickname;
    String et_name;
    String et_email;
    String et_password;
    String et_passwordck;
    String et_phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        valiateButton = (TextView)findViewById(R.id.validateButton);
        register = (Button)findViewById(R.id.btn_register);
        name = (EditText)findViewById(R.id.name);
        nickname = (EditText)findViewById(R.id.nickname);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        passwordck = (EditText)findViewById(R.id.passwordck);
        phonenumber = (EditText)findViewById(R.id.phonenumber);


        // 닉네임 중복 확인 버튼
        valiateButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                et_nickname= nickname.getText().toString();
                nickname.setText(checkUser(et_nickname));
            }
        });

        // 회원가입 버튼
        register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int new_user_id=-99;
                et_name= name.getText().toString();
                et_nickname= nickname.getText().toString();
                et_email= email.getText().toString();
                et_password= password.getText().toString();
                et_passwordck= passwordck.getText().toString();
                et_phonenumber= phonenumber.getText().toString();

                if(!(et_password.equals(et_passwordck))){ // 비밀번호가 같지 않을 경우
                    passwordck.setText(null);
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.",
                            Toast.LENGTH_LONG).show();
                    return; // 비밀번호 틀리니까 다시 확인하라고 하고 저장하지 않음
                }

                // 비밀번호 제대로 입력했을 경우에 서버에 저장하고
                try {
                    new_user_id =saveUser(et_name, et_nickname, et_email, et_password,et_phonenumber);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("new_user_id", new_user_id+"");
                setResult(RESULT_OK, intent);
                // 다시 로그인 액티비티로 넘어가게 하기
                // 신규 가입자의 경우, 신규 가입자 유저 아이디를 반환
                finish();
            }
        });

    }
    private String checkUser(String nickname) {
        ContentValues values = new ContentValues();

        values.put("nickname", nickname);

        NetworkTask checkUser = new NetworkTask(url+"checkUser", values);
        try { //닉네임 확인
            int check = Integer.parseInt(checkUser.execute().get());
            if(check ==1 ) {
                Toast.makeText(getApplicationContext(),  "이미 있는 닉네임입니다.", Toast.LENGTH_LONG).show();
                return " ";
            }
            if(check==0) {
                Toast.makeText(getApplicationContext(),  "사용하실 수 있는 닉네임입니다.", Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return nickname;
    }



    private int saveUser(String name, String nickname, String email, String password, String phonenumber) throws ExecutionException, InterruptedException {

        int new_user=-99;
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("nickname", nickname);
        values.put("email", email);
        values.put("password",password);
        values.put("phonenumber",phonenumber);

        NetworkTask insertUser = new NetworkTask(url+"insertUser", values);

        new_user= Integer.parseInt(insertUser.execute().get());
        return new_user;
    }
}
