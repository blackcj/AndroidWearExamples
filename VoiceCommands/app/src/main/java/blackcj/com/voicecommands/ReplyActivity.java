package blackcj.com.voicecommands;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.widget.TextView;

/**
 * Created by Chris on 11/17/2014.
 */
public class ReplyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        TextView outputText = (TextView)this.findViewById(R.id.output);
        outputText.setText(getMessageText(this.getIntent()));
    }

    /**
     * Obtain the intent that started this activity by calling
     * Activity.getIntent() and pass it into this method to
     * get the associated voice input string.
     */

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(MainActivity.EXTRA_VOICE_REPLY);
        }
        return null;
    }
}
