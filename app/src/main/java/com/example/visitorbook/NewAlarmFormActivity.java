package com.example.visitorbook;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.visitorbook.verticalstepperform.form.steps.Acoobus;
import com.example.visitorbook.verticalstepperform.form.steps.BusNo;
import com.example.visitorbook.verticalstepperform.form.steps.ConductorName;
import com.example.visitorbook.verticalstepperform.form.steps.DriversName;
import com.example.visitorbook.verticalstepperform.form.steps.DriversNumber;
import com.example.visitorbook.verticalstepperform.form.steps.FinalDes;
import com.example.visitorbook.verticalstepperform.form.steps.StartingDes;
import com.example.visitorbook.verticalstepperform.form.steps.traversName;

import verticalstepperform.VerticalStepperFormView;
import verticalstepperform.listener.StepperFormListener;
public class NewAlarmFormActivity extends AppCompatActivity implements StepperFormListener, DialogInterface.OnClickListener {
    
    public static final String STATE_NEW_ALARM_ADDED = "new_alarm_added";
    public static final String STATE_TITLE = "title";
    public static final String STATE_DESCRIPTION = "description";
    public static final String STATE_TIME_HOUR = "time_hour";
    public static final String STATE_TIME_MINUTES = "time_minutes";
    public static final String STATE_WEEK_DAYS = "week_days";

    private ProgressDialog progressDialog;
    private VerticalStepperFormView verticalStepperForm;

    private traversName nameStep;
    private StartingDes descriptionStep;
    private FinalDes timeStep;
    private DriversName daysStep;
    private DriversNumber daysStep1;
    private ConductorName conductname;
    private Acoobus acoobus;
    private BusNo busNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        String[] stepTitles = getResources().getStringArray(R.array.steps_titles);
        //String[] stepSubtitles = getResources().getStringArray(R.array.steps_subtitles);

        nameStep = new traversName(stepTitles[0]);//, stepSubtitles[0]);
        descriptionStep = new StartingDes(stepTitles[1]);//, stepSubtitles[1]);
        timeStep = new FinalDes(stepTitles[2]);//, stepSubtitles[2]);
        daysStep = new DriversName(stepTitles[3]);
        daysStep1 = new DriversNumber(stepTitles[4]);//, stepSubtitles[3]);
        conductname = new ConductorName(stepTitles[5]);
        busNo=new BusNo(stepTitles[6]);
        acoobus=new Acoobus(stepTitles[7]);


        verticalStepperForm = findViewById(R.id.stepper_form);
        verticalStepperForm.setup(this, nameStep, descriptionStep, timeStep, daysStep,daysStep1,conductname,busNo,acoobus).init();
    }

    @Override
    public void onCompletedForm() {
        final Thread dataSavingThread = saveData();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.form_sending_data_message));
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                try {
                    dataSavingThread.interrupt();
                } catch (RuntimeException e) {
                    // No need to do anything here
                } finally {
                    verticalStepperForm.cancelFormCompletionOrCancellationAttempt();
                }
            }
        });
    }

    @Override
    public void onCancelledForm() {
        showCloseConfirmationDialog();
    }

    private Thread saveData() {

        // Fake data saving effect
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    intent.putExtra(STATE_NEW_ALARM_ADDED, true);
                    intent.putExtra(STATE_TITLE, nameStep.getStepData());
                    intent.putExtra(STATE_DESCRIPTION, descriptionStep.getStepData());
                    intent.putExtra(STATE_WEEK_DAYS, daysStep.getStepData());

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return thread;
    }

    private void finishIfPossible() {
        if(verticalStepperForm.isAnyStepCompleted()) {
            showCloseConfirmationDialog();
        } else {
            finish();
        }
    }

    private void showCloseConfirmationDialog() {
        new DiscardAlarmConfirmationFragment().show(getSupportFragmentManager(), null);
    }

    private void dismissDialogIfNecessary() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finishIfPossible();
            return true;
        }

        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        switch (which) {

            // "Discard" button of the Discard Alarm dialog
            case -1:
                finish();
                break;

            // "Cancel" button of the Discard Alarm dialog
            case -2:
                verticalStepperForm.cancelFormCompletionOrCancellationAttempt();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        finishIfPossible();
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissDialogIfNecessary();
    }

    @Override
    protected void onStop() {
        super.onStop();

        dismissDialogIfNecessary();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString(STATE_TITLE, nameStep.getStepData());
        savedInstanceState.putString(STATE_DESCRIPTION, descriptionStep.getStepData());


        // IMPORTANT: The call to super method must be here at the end
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        if(savedInstanceState.containsKey(STATE_TITLE)) {
            String title = savedInstanceState.getString(STATE_TITLE);
            nameStep.restoreStepData(title);
        }

        if(savedInstanceState.containsKey(STATE_DESCRIPTION)) {
            String description = savedInstanceState.getString(STATE_DESCRIPTION);
            descriptionStep.restoreStepData(description);
        }

        if(savedInstanceState.containsKey(STATE_TIME_HOUR)
                && savedInstanceState.containsKey(STATE_TIME_MINUTES)) {
            int hour = savedInstanceState.getInt(STATE_TIME_HOUR);
            int minutes = savedInstanceState.getInt(STATE_TIME_MINUTES);

        }

        if(savedInstanceState.containsKey(STATE_WEEK_DAYS)) {
            boolean[] alarmDays = savedInstanceState.getBooleanArray(STATE_WEEK_DAYS);

        }

        // IMPORTANT: The call to super method must be here at the end
        super.onRestoreInstanceState(savedInstanceState);
    }

    public static class DiscardAlarmConfirmationFragment extends DialogFragment {
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            NewAlarmFormActivity activity = (NewAlarmFormActivity)getActivity();
            if (activity == null) {
                throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.form_discard_question)
                    .setMessage(R.string.form_info_will_be_lost)
                    .setPositiveButton(R.string.form_discard, activity)
                    .setNegativeButton(R.string.form_discard_cancel, activity)
                    .setCancelable(false);
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);

            return dialog;
        }
    }
}
