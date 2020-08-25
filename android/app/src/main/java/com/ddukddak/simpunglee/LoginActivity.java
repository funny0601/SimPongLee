
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

        import androidx.appcompat.app.AppCompatActivity;

        import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    String url = "http://192.168.0.34:8090/";

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
    private void loginUser(String et_login_email, String et_login_password) {
        ContentValues values = new ContentValues();

        values.put("email", et_login_email);
        values.put("password", et_login_password);

        NetworkTask loginUser = new NetworkTask(url+"selectUser", values);

        try {
            String check = loginUser.execute().get();
            Toast.makeText(getApplicationContext(), check+"님 반갑습니다~", Toast.LENGTH_LONG).show();
            Log.d("tag", check);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
