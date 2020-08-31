package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    final private static int RESULT_REQUEST_CODE = 102;
    EditText login_email;
    EditText login_password;
    Button login;
    Button login_register;
    int new_user_id=-999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.btn_login);
        login_register = (Button)findViewById(R.id.btn_register);

        login_email = (EditText)findViewById(R.id.email);
        login_password = (EditText)findViewById(R.id.password);

        // 회원가입 버튼
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 그냥 회원가입 화면으로 넘어감
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, RESULT_REQUEST_CODE);
            }
        });

        //  로그인 버튼
        // 여기서 HomeActivity로 userid 값은 가지고 넘어가야 합니당!
        login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_login_email= login_email.getText().toString();
                String et_login_password= login_password.getText().toString();

                // 제대로 입력하지 않았을 때 아예 안드로이드에서 막기
                if(et_login_email.isEmpty()||et_login_password.isEmpty()){
                    Toast.makeText(getApplicationContext(),  "이메일과 비밀번호를 모두 입력해주세요.", Toast.LENGTH_LONG).show();
                    login_email.setText("");
                    login_password.setText("");
                    return;
                }

                String result = loginUser(et_login_email, et_login_password);
                System.out.println(result);
                return;
            }
        });

    }

    private String loginUser(String et_login_email, String et_login_password) {
        ContentValues values = new ContentValues();

        values.put("email", et_login_email);
        values.put("password", et_login_password);

        NetworkTask loginUser = new NetworkTask("selectUser", values);

        try {

            String check = loginUser.execute().get();
            check = check.replaceAll("\"", ""); // string 처리 먼저해줘야함

            if(check.equals("no")) {
                Toast.makeText(getApplicationContext(),  "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                Log.d("tag", check);
                login_email.setText("");
                login_password.setText("");
                return  "inputWrong";
            }

            else if(!check.equals("no")){
                // 방금 회원가입한 회원인지 비교
                if(new_user_id!=-999){
                    ContentValues loginValues = new ContentValues();
                    loginValues.put("nickname", check);
                    NetworkTask getUserID = new NetworkTask("getId",loginValues);
                    int user_id_loggined = Integer.parseInt(getUserID.execute().get());
                    // 방금 로그인한 사람이 방금 가입한 사람임
                    if (new_user_id==user_id_loggined){
                        Intent intent = new Intent(getApplicationContext(), SelfDiagnosisInitialStartActivity.class);
                        intent.putExtra("userid", user_id_loggined);
                        intent.putExtra("categoryid", 1);
                        intent.putExtra("nickname", check.toString());
                        System.out.println("categoryid"+check);
                        startActivity(intent);
                        finish();
                    }
                    // 로그인한 사람이 방금 가입한 사람은 아님
                    else{
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("nickname", check.toString());
                        System.out.println("nickname"+check);
                        startActivity(intent);
                        finish();
                    }
                }
                // 걍 가입도 안했고 바로 로그인 시도하는 사람
                else{
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("nickname", check.toString());
                    System.out.println("nickname"+check);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            Toast.makeText(getApplicationContext(),  "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
            login_email.setText("");
            login_password.setText("");
            return  "inputNull";
        }
        return et_login_email;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_REQUEST_CODE) {
            if(resultCode == RESULT_CANCELED) {
                Toast.makeText(LoginActivity.this, "결과가 성공이 아님.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(resultCode == RESULT_OK) {
                new_user_id = Integer.parseInt(data.getStringExtra("new_user_id"));
                Toast.makeText(LoginActivity.this, "새로 가입된 유저 아이디 받아옴", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
