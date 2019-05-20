package project.b;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.Proxy.Type.HTTP;


public class askAQuestion_activity extends appHelper {
    EditText nameView,subjectView,textFieldView,emailView;
    int resumeCounter=0;
    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_aquestion);
        nameView=findViewById(R.id.editText_Name_ContactForm);
        subjectView=findViewById(R.id.editText_Subject_ContactForm);
        textFieldView=findViewById(R.id.editText_TextField_ContactForm);
        emailView=findViewById(R.id.editText_E_mail_ContactForm);

        layout = new LayoutHelper(this);

        layout.generateAskQuestionPage(findViewById(R.id.page_container));

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_grey_24dp};
        String[] text = new String[]{"home","Study programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);
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

    //https://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[a-z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void confirmContactForm(View v){
        String email=emailView.getText().toString().toLowerCase();
        String name = nameView.getText().toString();
        String subject = subjectView.getText().toString();
        String textField = textFieldView.getText().toString();
        if(name.length()>0 && subject.length()>0&&textField.length()>0) {
            //https://developer.android.com/training/basics/intents/sending.html#java
            if(isEmailValid(email)) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/html");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"0967161@hr.nl"}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Form Openday: " + subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, textField + "\n\nThis email was send by " + name+" with the opendag app.\nPleas anwser on: "+email);
                startActivity(emailIntent);
            }else{
                Toast.makeText(this,R.string.email_not_valid,Toast.LENGTH_LONG).show();
            }
        }else{
            String finalText=getString(R.string.fields_empty)+" ";
            if (name.length()==0){
                finalText+=getText(R.string.name)+", ";
            }
            if (email.length() == 0) {
                finalText+=getString(R.string.email)+", ";
            }
            if (email.length()==0){
                finalText+=getString(R.string.subject)+", ";
            }

            if (textField.length() == 0) {
                finalText+=getString(R.string.question)+", ";
            }

            finalText=finalText.substring(0,finalText.length()-2);
            Toast.makeText(this,finalText,Toast.LENGTH_LONG).show();
        }
    }
}
