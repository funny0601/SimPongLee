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

    String url = "http://3.35.65.128:8080/simpunglee/";

    EditText login_email;
    EditText login_password;
    Button login;
    Button login_register;

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
                startActivity(intent);
            }
        });

        //  로그인 버튼
        // 여기서 HomeActivity로 userid 값은 가지고 넘어가야 합니당!
        login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_login_email= login_email.getText().toString();
                String et_login_password= login_password.getText().toString();
                loginUser(et_login_email, et_login_password);

            }
        });

    }
    private String loginUser(String et_login_email, String et_login_password) {
        ContentValues values = new ContentValues();

        values.put("email", et_login_email);
        values.put("password", et_login_password);

        NetworkTask loginUser = new NetworkTask(url+"selectUser", values);

        try {
            String check = loginUser.execute().get();

            if(check.equals("")) {
                Toast.makeText(getApplicationContext(),  "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                Log.d("tag", check);
                login_email.setText(null);
                login_password.setText(null);
                return  "";
            }
            if(!check.equals("")){
                check = check.replaceAll("\"", "");

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("nickname", check.toString());
                System.out.println("nickname"+check);
                startActivity(intent);
                finish();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return et_login_email;
    }
}
