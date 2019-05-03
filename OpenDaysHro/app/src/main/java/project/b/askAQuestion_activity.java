package project.b;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static java.net.Proxy.Type.HTTP;


public class askAQuestion_activity extends AppCompatActivity {
    EditText nameView,subjectView,textFieldView;
    int resumeCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_aquestion);
        nameView=findViewById(R.id.editText_Name_ContactForm);
        subjectView=findViewById(R.id.editText_Subject_ContactForm);
        textFieldView=findViewById(R.id.editText_TextField_ContactForm);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(resumeCounter==0){
            resumeCounter+=1;
        }else{
            finish();
        }
    }

    public void confirmContactForm(View v){
        String name=nameView.getText().toString();
        String subject=subjectView.getText().toString();
        String textField=textFieldView.getText().toString();
        //https://developer.android.com/training/basics/intents/sending.html#java
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"thijsgeurts1@gmail.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Form Openday: "+subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, textField+"\nThis email was send by "+name);
        startActivity(emailIntent);
    }
}
