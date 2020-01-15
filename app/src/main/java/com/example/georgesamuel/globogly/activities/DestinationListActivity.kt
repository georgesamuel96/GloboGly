package com.example.georgesamuel.globogly.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.georgesamuel.globogly.R
import com.example.georgesamuel.globogly.helpers.DestinationAdapter
import com.example.georgesamuel.globogly.models.Destination
import com.example.georgesamuel.globogly.services.DestinationService
import com.example.georgesamuel.globogly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		setSupportActionBar(toolbar)
		toolbar.title = title

		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {
		val destinationService: DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall = destinationService.getDestinationList()
		requestCall.enqueue(object : Callback<List<Destination>> {
			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
				Toast.makeText(this@DestinationListActivity, "Error Occurred $t", Toast.LENGTH_LONG).show()
			}

			override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
				if(response.isSuccessful){
					val destinationList = response.body()!!
					destiny_recycler_view.adapter = DestinationAdapter(destinationList)
				}
				else if(response.code() == 401){
					Toast.makeText(this@DestinationListActivity,
						"Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
				}
				else {
					Toast.makeText(this@DestinationListActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
				}
			}
		})
    }
}
