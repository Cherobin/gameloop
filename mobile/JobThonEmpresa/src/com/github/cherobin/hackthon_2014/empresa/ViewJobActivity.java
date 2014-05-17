package com.github.cherobin.hackthon_2014.empresa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.cherobin.hackthon_2014.VO.JobId;
import com.github.cherobin.hackthon_2014.VO.JobView;
import com.google.gson.Gson;

public class ViewJobActivity extends Activity {
	
	ListView viewJobsList;
	List<JobView> listJobs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_job);
		
		listJobs = new ArrayList<JobView>();
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		viewJobsList = (ListView) findViewById(R.id.listViewViewJobs);
		
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().getSerializable("job") != null) {// editajob

				JobId myObject = (JobId) getIntent().getExtras().getSerializable(
						"job");

				if (myObject != null) { // edit job

					getJob(myObject.id);
				}

			}
		}
		
		if(listJobs.size()!=0){
			viewJobsList.setAdapter(new VagaAdapter(ViewJobActivity.this,
					null, listJobs));
		}else {
			Toast.makeText(getBaseContext(), "Nenhum CV cadastrado!", Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void getJob(long id) {
		 
		Gson gson = new Gson();		 
		HttpResponse response = null;
		HttpGet getMethod = new HttpGet("http://gdgjobthom.appspot.com/analises/vaga/"+id);		 
		try {
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(getMethod);
			String result = EntityUtils.toString(response.getEntity());
			JobView[] myJobsArray = gson.fromJson(result, JobView[].class);
			listJobs = Arrays.asList(myJobsArray);	

		} catch (Exception e) { 
		}
	}
	
	
	public class VagaAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater inflater;
		private List<JobView> viewJobsList;
		public VagaAdapter(Activity activity, Context context,
				List<JobView> vagas) {
			super();
			this.context = context;
			inflater = LayoutInflater.from(activity);
			viewJobsList = vagas;
			
		}
 
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
 			return viewJobsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = convertView;
			view = inflater.inflate(R.layout.item_jobs, parent, false);
 
			TextView nome = (TextView) view.findViewById(R.id.textViewListClientName);
			ProgressBar porcen = (ProgressBar) view.findViewById(R.id.progressBarJobs);
			
			nome.setText(viewJobsList.get(position).nomeCurriculo);
			porcen.setProgress((int) viewJobsList.get(position).compatibilidade);
// 			Toast.makeText(getBaseContext(), "aaa "+viewJobsList.get(position).nomeCurriculo, Toast.LENGTH_LONG).show();
			 
			return view;
		}

		 

	}
}
