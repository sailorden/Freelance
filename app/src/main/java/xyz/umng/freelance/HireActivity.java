package xyz.umng.freelance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HireActivity extends AppCompatActivity {

    private static final String TAG = "HireActivity";

    ProgressDialog pd;

    @Bind(R.id.input_firstName_hire) EditText _firstNameText;
    @Bind(R.id.input_lastName_hire) EditText _lastNameText;
    @Bind(R.id.input_email_hire) EditText _emailText;
    @Bind(R.id.input_phone_hire) EditText _phoneText;
    @Bind(R.id.input_project_hire) EditText _projectText;
    @Bind(R.id.input_work_require_hire) EditText _workRequireText;
    @Bind(R.id.input_skills_hire) EditText _skillsText;
    @Bind(R.id.input_more_about_you_hire) EditText _moreAboutYouText;
    @Bind(R.id.btn_submit_hire) Button _submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);

        ButterKnife.bind(this);

        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(HireActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                pd.setIndeterminate(true);
                pd.setCancelable(false);
                pd.setMessage("Submitting...");
                pd.show();
                submit();
            }
        });
    }

    public void submit() {
        Log.d(TAG, "WorkActivity");

        if (!validate()) {
            onSubmitFailed();
            return;
        }

        _submitButton.setEnabled(false);

        String firstName = _firstNameText.getText().toString().trim();
        String lastName = _lastNameText.getText().toString().trim();
        String email = _emailText.getText().toString().trim();
        String phone = _phoneText.getText().toString().trim();
        String project = _projectText.getText().toString().trim();
        String skills = _skillsText.getText().toString().trim();
        String workRequire = _workRequireText.getText().toString().trim();
        String moreAboutYou = _moreAboutYouText.getText().toString().trim();

        ParseObject hire = new ParseObject("Hire");
        hire.put("firstName", firstName);
        hire.put("lastName", lastName);
        hire.put("email", email);
        hire.put("phone", phone);
        hire.put("project", project);
        hire.put("skills", skills);
        hire.put("workRequire", workRequire);
        hire.put("moreAboutYou", moreAboutYou);
        hire.saveInBackground(new SaveCallback() {
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    onSubmitSuccess();
                } else {
                    onSubmitFailed();
                    Toast.makeText(HireActivity.this, "Error\nPlease try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void onSubmitSuccess() {
        _submitButton.setEnabled(true);
        pd.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(HireActivity.this);
        builder.setMessage("Your response has been successfully recorded. We will contact you soon.");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onSubmitFailed() {
        _submitButton.setEnabled(true);
        pd.dismiss();
    }

    public boolean validate() {
        boolean valid = true;

        String firstName = _firstNameText.getText().toString().trim();
        String lastName = _lastNameText.getText().toString().trim();
        String email = _emailText.getText().toString().trim();
        String phone = _phoneText.getText().toString().trim();
        String project = _projectText.getText().toString().trim();
        String skills = _skillsText.getText().toString().trim();
        String workRequire = _workRequireText.getText().toString().trim();
        String moreAboutYou = _moreAboutYouText.getText().toString().trim();

        if (firstName.isEmpty() || firstName.length() < 3) {
            _firstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _firstNameText.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            _lastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lastNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 4 || phone.length() > 10) {
            _phoneText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (workRequire.isEmpty() || workRequire.length() < 3) {
            _workRequireText.setError("at least 3 characters");
            valid = false;
        } else {
            _workRequireText.setError(null);
        }

        if (project.isEmpty() || project.length() < 3) {
            _projectText.setError("at least 3 characters");
            valid = false;
        } else {
            _projectText.setError(null);
        }

        if (skills.isEmpty() || skills.length() < 3) {
            _skillsText.setError("at least 3 characters");
            valid = false;
        } else {
            _skillsText.setError(null);
        }

        if (moreAboutYou.isEmpty() || moreAboutYou.length() < 10) {
            _moreAboutYouText.setError("at least 10 characters");
            valid = false;
        } else {
            _moreAboutYouText.setError(null);
        }

        return valid;
    }
}
