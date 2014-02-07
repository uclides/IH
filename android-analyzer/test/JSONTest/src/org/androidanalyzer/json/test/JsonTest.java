package org.androidanalyzer.json.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.androidanalyzer.core.Data;
import org.androidanalyzer.transport.impl.json.HTTPJSONReporter;

import org.androidanalyzer.json.test.R;

import android.app.Activity;
import android.os.Bundle;

public class JsonTest extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		execute();
	}

	private void execute() {
        try {
		String tag = "JSON";
		String inputSrcA = "A";
		String inputSrcM = "M";
		String confLevelTCC = "TCC";
		String confLevelUC = "UC";
		String confLevelU = "U";

		Data root = new Data();
		root.setComment("Root Comments");
		root.setConfirmationLevel(confLevelTCC);
		root.setInputSource(inputSrcA);

            root.setName("ROOT");


        // Adds history report node
		Data history_report = new Data();
		history_report.setComment("History Report Comments");
		history_report.setConfirmationLevel(confLevelU);
		history_report.setInputSource(inputSrcA);
		history_report.setName("History report");

		Data name_h = new Data();
		name_h.setComment("Name Comments");
		name_h.setConfirmationLevel(confLevelU);
		name_h.setInputSource(inputSrcA);
		name_h.setName("Name");
		name_h.setValue("2009/12/17/ - Kaloyan");

		ArrayList<Data> list_history_rpt = new ArrayList();
		list_history_rpt.add(name_h);
		history_report.setValue(list_history_rpt);

		// Adds Manufacturer node
		Data manufacturer = new Data();
		manufacturer.setComment("Manufacturer Comments");
		manufacturer.setConfirmationLevel(confLevelU);
		manufacturer.setInputSource(inputSrcA);
		manufacturer.setName("Manufacturer");

		Data name_m = new Data();
		name_m.setComment("Name Comments");
		name_m.setConfirmationLevel(confLevelU);
		name_m.setInputSource(inputSrcA);
		name_m.setName("Name");
		name_m.setValue("Samsung");

		ArrayList<Data> list_m = new ArrayList();
		list_m.add(name_m);
		manufacturer.setValue(list_m);

		// Adds Device node
		Data device = new Data();
		device.setComment("Device Comments");
		device.setConfirmationLevel(confLevelU);
		device.setInputSource(inputSrcA);
		device.setName("Device");

		Data name_d = new Data();
		name_d.setComment("Name Comments");
		name_d.setConfirmationLevel(confLevelU);
		name_d.setInputSource(inputSrcA);
		name_d.setName("Name");
		name_d.setValue("SHW-M100S");

		ArrayList<Data> list_d = new ArrayList();
		list_d.add(name_d);
		device.setValue(list_d);

		// Adds Operator node
		Data operator = new Data();
		operator.setComment("Operator Comments");
		operator.setConfirmationLevel(confLevelU);
		operator.setInputSource(inputSrcA);
		operator.setName("Operator");

		Data name_o = new Data();
		name_o.setComment("Name Comments");
		name_o.setConfirmationLevel(confLevelU);
		name_o.setInputSource(inputSrcA);
		name_o.setName("Name");
		name_o.setValue("M-TEL");

		ArrayList<Data> list_o = new ArrayList();
		list_o.add(name_o);
		operator.setValue(list_o);

		// Adds Firmware version node
		Data frm_version = new Data();
		frm_version.setComment("Firmware version Comments");
		frm_version.setConfirmationLevel(confLevelU);
		frm_version.setInputSource(inputSrcA);
		frm_version.setName("Firmware version");

		Data name_fv = new Data();
		name_fv.setComment("Version Comments");
		name_fv.setConfirmationLevel(confLevelU);
		name_fv.setInputSource(inputSrcA);
		name_fv.setName("Version");
		name_fv.setValue("2.1");

		ArrayList<Data> list_fv = new ArrayList();
		list_fv.add(name_fv);
		frm_version.setValue(list_fv);

		// Adds display node
		Data display = new Data();
		display.setComment("Display Comments");
		display.setConfirmationLevel(confLevelUC);
		display.setInputSource(inputSrcM);
		display.setName("Display");

		Data display_0 = new Data();
		display_0.setComment("The display is very fancy");
		display_0.setConfirmationLevel(confLevelUC);
		display_0.setInputSource(inputSrcM);
		display_0.setName("Display_0");

		Data location = new Data();
		location.setComment("Location Comments");
		location.setConfirmationLevel(confLevelU);
		location.setInputSource(inputSrcA);
		location.setName("Location");
		location.setValue("front");

		Data display_technology = new Data();
		display_technology.setComment("Display Technology Comments");
		display_technology.setConfirmationLevel(confLevelU);
		display_technology.setInputSource(inputSrcA);
		display_technology.setName("Display technology");
		display_technology.setValue("oled");

		ArrayList<Data> list_dsp_0 = new ArrayList();
		list_dsp_0.add(location);
		list_dsp_0.add(display_technology);
		display_0.setValue(list_dsp_0);

		ArrayList<Data> list_dsp = new ArrayList();
		list_dsp.add(display_0);
		display.setValue(list_dsp);

		// Adds to Root
		ArrayList<Data> list_root = new ArrayList();
		list_root.add(history_report);
		list_root.add(manufacturer);
		/*list_root.add(device);
		list_root.add(operator);
		list_root.add(frm_version);
		list_root.add(display);*/
		root.setValue(list_root);

		HTTPJSONReporter reporter = new HTTPJSONReporter();
		try {
			reporter.send(root, new URL("http://172.22.55.24:3128/DataServlet"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

}