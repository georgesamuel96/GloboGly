package com.example.georgesamuel.globogly.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.georgesamuel.globogly.R
import com.example.georgesamuel.globogly.helpers.SampleData
import com.example.georgesamuel.globogly.models.Destination
import com.example.georgesamuel.globogly.services.DestinationService
import com.example.georgesamuel.globogly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_detail.*
import retrofit2.Call
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_detail)

		setSupportActionBar(detail_toolbar)
		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val bundle: Bundle? = intent.extras

		if (bundle?.containsKey(ARG_ITEM_ID)!!) {

			val id = intent.getIntExtra(ARG_ITEM_ID, 0)

			loadDetails(id)

			initUpdateButton(id)

			initDeleteButton(id)
		}
	}

	private fun loadDetails(id: Int) {
		val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall = destinationService.getDestination(id)
		requestCall.enqueue(object: retrofit2.Callback<Destination>{
			override fun onFailure(call: Call<Destination>, t: Throwable) {
				Toast.makeText(this@DestinationDetailActivity, "Error Occurred $t", Toast.LENGTH_LONG).show()
			}

			override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
				if(response.isSuccessful){
					val destination = response.body()
					destination?.let {
						et_city.setText(destination.city)
						et_description.setText(destination.description)
						et_country.setText(destination.country)
						collapsing_toolbar.title = destination.city
					}
				}
				else if(response.code() == 401){
					Toast.makeText(this@DestinationDetailActivity,
						getString(R.string.session_expired), Toast.LENGTH_LONG).show()
				}
				else {
					Toast.makeText(this@DestinationDetailActivity,
						getString(R.string.failed_to_retrive) + " Details",
						Toast.LENGTH_LONG).show()
				}
			}
		})
	}

	private fun initUpdateButton(id: Int) {

		btn_update.setOnClickListener {

			val city = et_city.text.toString()
			val description = et_description.text.toString()
			val country = et_country.text.toString()

            // To be replaced by retrofit code
            val destination = Destination()
            destination.id = id
            destination.city = city
            destination.description = description
            destination.country = country

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.updateDestination(id, city, description, country)
            requestCall.enqueue(object: retrofit2.Callback<Destination>{
                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity, "Error Occurred $t", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@DestinationDetailActivity, getString(R.string.successfully_updated),
                            Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this@DestinationDetailActivity, getString(R.string.failed_to_update_item)
                            , Toast.LENGTH_LONG).show()
                    }
                }
            })
		}
	}

	private fun initDeleteButton(id: Int) {

		btn_delete.setOnClickListener {

			val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall = destinationService.deleteDestination(id)
			requestCall.enqueue(object: retrofit2.Callback<Unit> {
				override fun onFailure(call: Call<Unit>, t: Throwable) {
					Toast.makeText(this@DestinationDetailActivity, "Error Occurred $t", Toast.LENGTH_LONG).show()
				}

				override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
					if(response.isSuccessful){
						Toast.makeText(this@DestinationDetailActivity, getString(R.string.successfully_deleted),
							Toast.LENGTH_LONG).show()
						finish()
					}
					else {
						Toast.makeText(this@DestinationDetailActivity, getString(R.string.failed_to_delete_item)
							, Toast.LENGTH_LONG).show()
					}
				}
			})
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id = item.itemId
		if (id == android.R.id.home) {
			navigateUpTo(Intent(this, DestinationListActivity::class.java))
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	companion object {

		const val ARG_ITEM_ID = "item_id"
	}
}
