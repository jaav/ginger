package be.sushi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends Activity
{
    Button start;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start = (Button)findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
