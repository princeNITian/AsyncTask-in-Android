package com.example.prince.hsamp14;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText etNum;
    Button btnResult;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum = (EditText) findViewById(R.id.etNum);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnResult = (Button) findViewById(R.id.btnSubmit);
      //  tvResult.setVisibility(View.GONE);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noOfTimes = Integer.parseInt(etNum.getText().toString().trim());

               new ProcessDiceInBackground().execute(noOfTimes);
            }
        });

    }

    public class ProcessDiceInBackground extends AsyncTask<Integer,Integer,String>
    {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(Integer.parseInt(etNum.getText().toString().trim()));
            dialog.show();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int ones=0,twos=0,threes=0,fours=0,fives=0,sixes=0, randomNumber;
           // int noOfTimes = Integer.parseInt(etNum.getText().toString().trim());

            Random random = new Random();
            String results;

            double currentProgress=0;
            double previousProgress = 0;

            for(int i =0;i<integers[0];i++)
            {

                currentProgress  = (double) i/integers[0];
                if(currentProgress-previousProgress >= 0.02)
                {
                    publishProgress(i);
                    previousProgress = currentProgress;
                }
                randomNumber = random.nextInt(6)+1;
                switch ((randomNumber))
                {
                    case 1: ones++; break;
                    case 2: twos++; break;
                    case 3: threes++; break;
                    case 4: fours++; break;
                    case 5: fives++; break;
                    case 6: sixes++; break;
                    default: break;
                }
            }
            results = "Results: \n1: "+ones +"\n2: "+twos+"\n3: "+threes+"\n4: "+fours+"\n5: "+fives+"\n6: "+sixes;

            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            dialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            tvResult.setText(s);
            Toast.makeText(MainActivity.this,"Progress Done!",Toast.LENGTH_SHORT).show();
        }
    }
}
