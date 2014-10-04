
package edu.wetisai.orc;

import edu.wetisai.ocr.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(ArrayAdapter.createFromResource(this, R.array.sampe_list, android.R.layout.simple_list_item_1));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Class purpose;
        
        switch (position) {
            case 0:
                purpose = BitmapORC.class;
                break;
                
            case 1:
                purpose = CameraORC.class;
                break;
                
            default:
                return;
        }
        
        Intent intent = new Intent(this, purpose);
        startActivity(intent);
    }
}
