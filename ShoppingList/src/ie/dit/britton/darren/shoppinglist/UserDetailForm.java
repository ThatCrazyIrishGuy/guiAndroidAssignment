package ie.dit.britton.darren.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class UserDetailForm extends ActionBarActivity 
{

	Button submit;
	EditText name;
	RadioGroup gender;
	Spinner job;
	EditText age;
	EditText money;
	EditText email;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_form);
        
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                Store store = Store.getInstance();
				store.populate(getResources().getStringArray(R.array.items),getResources().getStringArray(R.array.descriptions));
            }
        };
        
        thread.run();
        
        int spinnerID =R.id.job;
        
        Spinner spinner = (Spinner) findViewById(spinnerID);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	    		R.array.jobTitles, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
        
        submit = (Button)findViewById(R.id.submit);
        name   = (EditText)findViewById(R.id.name);
        gender = (RadioGroup)findViewById(R.id.gender);
        job = (Spinner)findViewById(spinnerID);
        age   = (EditText)findViewById(R.id.age);
        money   = (EditText)findViewById(R.id.money);
        email   = (EditText)findViewById(R.id.email);
        
       submit.setOnClickListener(
            new OnClickListener()
            {
                public void onClick(View view)
                {
            		boolean nextIntent =setUserDetails();
            		if (nextIntent)
            		{
                		loadShoppingList();
            		}
            		else
            		{
            	    	String message = "Please complete all fields / Enter valid values";
            	    	
            	    	MakeToast(message);
            		}
                }
            });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_detail_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	  switch (item.getItemId()) {
    	    case R.id.action_load_demo_values:
    	        MakeToast("Uh this was hard so i didn't do it");
    	        return true;
    	    case R.id.action_settings:
    	        // Settings option clicked.
    	        return true;
    	    default:
    	        return super.onOptionsItemSelected(item);
    	    }
    }
    
    private boolean setUserDetails()
    {
        User user = User.getInstance();
        
        if (name.getText().length() != 0)
        {
        	Log.i("info","Name: " +name.getText().toString());
            user.setName(name.getText().toString());
        }
        else
        {
        	return false;
        }
        
        try
        {
            int selectedID = gender.getCheckedRadioButtonId();
            RadioButton selectedGender = (RadioButton) findViewById(selectedID);
            user.setGender(selectedGender.getText().toString());
        }
        catch(Exception e)
        {
        	return false;
        }
        
        user.setJob(job.getSelectedItem().toString());
        
        try
        {
        	//Log.i("info","Age: " +Short.parseShort(age.getText().toString()));
            user.setAge(Short.parseShort(age.getText().toString()));
        }
        catch(Exception e)
        {
        	return false;
        }
        
        try
        {
        	//Log.i("info","Money: " +Double.parseDouble(money.getText().toString()));
            user.setMoney(Double.parseDouble(money.getText().toString()));
        }
        catch(Exception e)
        {
        	return false;
        }
        
        try
        {
        	//Log.i("info","Email: " +email.getText().toString());
            user.setEmail(email.getText().toString());
        }
        catch(Exception e)
        {
        	return false;
        }
        
//        Log.i("info","Name: " +user.getName());
//        Log.i("info","Gender: " +user.getGender());
//        Log.i("info","Job: " +user.getJob());
//        Log.i("info","Age: " +user.getAge());
//        Log.i("info","Money: " +user.getMoney());
//        Log.i("info","Email: " +user.getEmail());

    	return true;
        
    }
    
    private void loadShoppingList()
    {
		Intent intent = new Intent(UserDetailForm.this,ShoppingListView.class);
		startActivity(intent);
    }
    
    public void MakeToast(String message)
    {
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
