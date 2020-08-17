package com.ddukddak.simpunglee;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.ddukddak.simpunglee.R.id.diary_content;

public class BadwordFilter extends Service {
    @Nullable
    String TAG = "BadwordFilter";
    List<String> list = new ArrayList<String>();
    Boolean isBadword = false;

    @Override
    public void onStart(Intent intent, int id) {
        super.onStart(intent, id);
        Log.d(TAG, "onStart");
        readData();
        Log.d(TAG, "text : " + intent.getStringExtra("TextToFilter"));
        filterBadWords(intent.getStringExtra("TextToFilter"));
    }

    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.word_filter);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                String[] token = line.split(",");

                list.add(token[0].trim());

                // Log.d(TAG, "CREATED : " + token[0]);
            }
        } catch (IOException e) {
            //Log.wtf(TAG, "error reading datafile on line" + line, e);
            e.printStackTrace();
        }
    }

    private void filterBadWords(String str) {
        int i = 0, j = 1;
        StringTokenizer st = new StringTokenizer(str);
        String s = st.nextToken();
        while(st.hasMoreTokens()) {
            s = st.nextToken();
            j++;
        }

        String[] strSplit = new String[j];
        st = new StringTokenizer(str);
        j = 0;
        while(j != strSplit.length) {
            strSplit[j] = st.nextToken();
            //Log.d("print strSplit", strSplit[j]);
            j++;
        }
        while(i != list.size()) {
            j = 0;
            while(j != strSplit.length) {
                if(strSplit[j].equals(list.get(i))) {
                    // 욕이 감지될 경우에 욕 부분을 없애버림
                    // 욕이 나왔으니까 욕 나옴 체크하기
                    isBadword = true;
                    Log.d("check strSplit", strSplit[j]);
                    Log.d("check list", list.get(i));
                    strSplit[j] = "";
                }
                //Log.d("check j", String.valueOf(j));
                j++;
            }
            //Log.d("check i", String.valueOf(i));
            i++;
        }

        String text = "";
        j = 0;
        while(j != strSplit.length) {
            text += strSplit[j] + " ";
            //text.concat(strSplit[j]);
            j++;
        }
        //      Log.d("filtered list", list.get(i));
        //    Log.d("filtered word", strSplit[j]);
        //if(!word.equals(list.get(i)))

        Log.d(TAG, "filtered text : " + text);
        if(isBadword) {
            Toast.makeText(getApplicationContext(), "긍정적인 말을 써보는 건 어떨까요?", Toast.LENGTH_LONG).show();
            // 만약 욕설 카운팅 할거면 여기서 세야될듯
        }else{
            // Toast.makeText(getApplicationContext(), "욕설 없음. 깔끔", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("TAG","onDestroy()");
        super.onDestroy();
    }

}