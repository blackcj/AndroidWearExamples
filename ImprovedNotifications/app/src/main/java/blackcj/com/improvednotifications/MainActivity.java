package blackcj.com.improvednotifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;

import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
     * Create the intent you are going to launch when your notification is pressed
     * and let the PendingIntent handle it
     */
        Intent open_activity_intent = new Intent(this, ActivatedActivity.class);
        open_activity_intent.putExtra(NOTIFICATION_ID, notification_id);
        PendingIntent pending_intent = PendingIntent.getActivity(this, 0, open_activity_intent, PendingIntent.FLAG_CANCEL_CURRENT);

    /*
     * Step 3
     * Here you create the notification and start adding all the attributes you are going to use
     */
        notification_builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(pending_intent)
      /*
       * This method specifies that our notification must have all the default characteristics of a notification
       * like sound and vibration
       */
                .setDefaults(Notification.DEFAULT_ALL)
      /* This method is going to dismiss the notification once it is pressed */
                .setAutoCancel(true);


    /*
     * Step 4
     * Here we instantiate the Notification Manager object to start/stop the notifications
     */
        notification_manager = NotificationManagerCompat.from(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
         * Step 5
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
