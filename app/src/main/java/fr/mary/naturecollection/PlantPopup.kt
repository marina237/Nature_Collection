package fr.mary.naturecollection

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import fr.mary.naturecollection.adapter.PlantAdapter

//classe qui va gérer la popup
class PlantPopup(private val adapter : PlantAdapter)
    :Dialog(adapter.context) {

        //injecter layout associe au popup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //on ne veut pas de titre sur la fenetre Popup
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //injection du layout
        setContentView(R.layout.popup_plants_details)
    }


}