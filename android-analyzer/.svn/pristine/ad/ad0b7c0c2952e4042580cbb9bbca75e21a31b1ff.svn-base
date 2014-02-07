package org.androidanalyzer.gui;

import org.androidanalyzer.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 *  This Activity displays the About info of the Android Analyzer application.
 *  It is shown in the About tab of the Settings activity. 
 *
 */
public class AboutActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getResources().getString(R.string.about_title);
		setContentView(R.layout.about_layout);
		setTitle(title);
		TextView aaUrl = (TextView)findViewById(R.id.aa_url);
		String aaLink = "<a href="+getString(R.string.aa_url_link)+">"+getString(R.string.aa_url_text)+"</a>";
		aaUrl.setText(Html.fromHtml(aaLink), TextView.BufferType.SPANNABLE);
		aaUrl.setMovementMethod(LinkMovementMethod.getInstance());
		TextView afUrl = (TextView)findViewById(R.id.af_url);
		String afLink = "<a href="+getString(R.string.af_url_link)+">"+getString(R.string.af_url_text)+"</a>";
		afUrl.setText(Html.fromHtml(afLink), TextView.BufferType.SPANNABLE);
		afUrl.setMovementMethod(LinkMovementMethod.getInstance());
		TextView contactName = (TextView)findViewById(R.id.about_vendor_name);
		String authorMail = "<a href="+getString(R.string.about_vendor_mail)+">"+getString(R.string.about_vendor_name)+"</a>";
		contactName.setText(Html.fromHtml(authorMail), TextView.BufferType.SPANNABLE);
		contactName.setMovementMethod(LinkMovementMethod.getInstance());
		//concat version
		String appVersionName = getResources().getString(R.string.app_version_name);
		TextView aaVersion = (TextView)findViewById(R.id.about_version);
		aaVersion.setText(aaVersion.getText()+" "+appVersionName);
	}
	
	

}
