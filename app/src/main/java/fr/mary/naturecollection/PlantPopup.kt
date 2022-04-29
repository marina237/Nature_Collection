package fr.mary.naturecollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.mary.naturecollection.adapter.PlantAdapter

//classe qui va gérer la popup
class PlantPopup(
    private val adapter : PlantAdapter,
    private val currentPlant : PlantModel )
    :Dialog(adapter.context) {

        //injecter layout associe au popup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //on ne veut pas de titre sur la fenetre Popup
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //injection du layout
        setContentView(R.layout.popup_plants_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    //Maj button sur la page principale --> permet d avoir une actualisation en tant réel
    private fun updateStar(button : ImageView){
        if (currentPlant.liked){
            button.setImageResource(R.drawable.ic_star)
        }else{
            button.setImageResource(R.drawable.ic_unstar)
        }
    }


    private fun setupStarButton() {
        //recuperer
        //on utilise ici une val car on va utiliser le findView plusieurs fois
        val starButton = findViewById<ImageView>(R.id.star_button)

        updateStar(starButton)


        //interaction qd on clique sur l etoile
        starButton.setOnClickListener {
            currentPlant.liked = ! currentPlant.liked
            val repo = PlantRepository()
            repo.updatePlant(currentPlant)

            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
      findViewById<ImageView>(R.id.delete_button).setOnClickListener {
          //supprimer la plante de la BDD
          //1. on génère une instance du repository
          val repo = PlantRepository()
          //2. execute l instruction a la database de reference
          repo.deletePlant(currentPlant)
          //3. on ferme la popup
          dismiss()
      }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            // fermer la fenetre popup
            dismiss()

        }
    }

    //setup = configuration
    private fun setupComponents() {
        //recupérer l image de la plante correspondante
        val plantImage = findViewById<ImageView>(R.id.image_item)

        //le context permet d accéder à la MainActivity
        //into : pour mettre l url de l image dans mon ImageView
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)


        //recuperer le nom de la plante correspondante
        findViewById<TextView>(R.id.popup_plant_name).text = currentPlant.name

        //recuperer la description de la plante correspondante
        findViewById<TextView>(R.id.popup_plant_description_subtitle).text = currentPlant.description

        //recuperer la grow de la plante
        findViewById<TextView>(R.id.popup_plant_grow_subtitle).text = currentPlant.grow

        //Recuperer la conso en eau de la plante
        findViewById<TextView>(R.id.popup_plant_water_subtitle).text = currentPlant.water
    }


}