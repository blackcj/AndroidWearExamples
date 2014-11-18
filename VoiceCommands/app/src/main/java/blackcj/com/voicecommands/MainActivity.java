package blackcj.com.voicecommands;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    /* Widgets you are going to use */
    private Button button;

    /*
     * This is the notification id
     * You can use it to dismiss the notification calling the .cancel() method on the notification_manager object
     */
    private int notification_id = 1;
    private final String NOTIFICATION_ID = "notification_id";

    // Key for the string that's delivered in the action's intent
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    /* These are the classes you use to start the notification */
    private NotificationCompat.Builder notification_builder;
    private NotificationManagerCompat notification_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /*
     * Step 1
     * Instantiation of the button you use to start the notification
     */
        button = (Button) findViewById(R.id.notification_button);


    /*
     * Step 2
     * Create the remote input that you will you to accept voice commands
     */
        String replyLabel = getResources().getString(R.string.reply_label);
        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

    /*
     * Step 3
     * Create the intent you are going to launch when your notification is pressed
     * and let the PendingIntent handle it
     */
        Intent replyIntent = new Intent(this, ReplyActivity.class);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 0, replyIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

        // Create the reply action and add the remote input
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.reply,
                        getString(R.string.label), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

    /*
     * Step 4
     * Here you create the notification and start adding all the attributes you are going to use
     */
        notification_builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_text))
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
      /*
       * This method specifies that our notification must have all the default characteristics of a notification
       * like sound and vibration
       */
                        .setDefaults(Notification.DEFAULT_ALL)
      /* This method is going to dismiss the notification once it is pressed */
                        .setAutoCancel(true);
    /*
     * Step 5
     * Here we instantiate the Notification Manager object to start/stop the notifications
     */
        notification_manager =
                NotificationManagerCompat.from(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        /*
         * Step 6
         * The notification is going to appear when you press the button on the screen
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_manager.notify(notification_id, notification_builder.build());
            }
        });
    }
}
