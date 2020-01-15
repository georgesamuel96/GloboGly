package com.example.georgesamuel.globogly.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.georgesamuel.globogly.R
import com.example.georgesamuel.globogly.helpers.SampleData
import com.example.georgesamuel.globogly.models.Destination
import com.example.georgesamuel.globogly.services.DestinationService
import com.example.georgesamuel.globogly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = et_city.text.toString()
			newDestination.description = et_description.text.toString()
			newDestination.country = et_country.text.toString()

			val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall = destinationService.addDestination(newDestination)
			requestCall.enqueue(object: retrofit2.Callback<Destination>{
				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(this@DestinationCreateActivity, "Error Occurred $t", Toast.LENGTH_LONG).show()
				}

				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if(response.isSuccessful){
						Toast.makeText(this@DestinationCreateActivity, getString(R.string.successfully_added),
							Toast.LENGTH_LONG).show()
						finish()
					}
					else {
						Toast.makeText(this@DestinationCreateActivity, getString(R.string.failed_to_add_item)
						, Toast.LENGTH_LONG).show()
					}
				}
			})
		}
	}
}
