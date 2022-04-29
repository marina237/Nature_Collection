package fr.mary.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.mary.naturecollection.MainActivity
import fr.mary.naturecollection.PlantModel
import fr.mary.naturecollection.PlantRepository
import fr.mary.naturecollection.PlantRepository.Singleton.downloadUri
import fr.mary.naturecollection.R
import java.util.*

class AddPlantFragment(
    private val context : MainActivity
) : Fragment(){

    private var file : Uri? = null

    private var uploadedImage : ImageView? = null


    //injection du layout qu on a crée précédement dans le fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant,container,false)

        //recuperer uploadedImage pr lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)


        //recuperer le bouton pr charger l image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //interaction pr que au clic ça affiche les images du tel
        pickupImageButton.setOnClickListener{
            pickupImage()
        }

        //recuperer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener{
           sendForm(view)
        }

        //on renvoi la view pr qu elle soit prise en compte dabs le fragment
        return view
    }

    private fun sendForm(view: View?) {

        val repo = PlantRepository()

        // instruction à faire qd tu télécharges l image
        repo.uploadImage(file!!){
            val plantName = view?.findViewById<EditText>(R.id.name_input)?.text.toString()
            val plantDescription = view?.findViewById<EditText>(R.id.description_input)?.text.toString()
            val grow = view?.findViewById<Spinner>(R.id.grow_spinner)?.selectedItem.toString()
            val water = view?.findViewById<Spinner>(R.id.water_spinner)?.selectedItem.toString()
            val downloadImageUrl = downloadUri

            //créer un nouv objet PlantModel
            //on met les infos pr construire une plante
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUrl.toString(),
                false,
                grow,
                water


            )
            //envoyer dans la BDD
            repo.inserPlant(plant)
        }


    }

    private fun pickupImage() {
        //intent : permet d accéder aux ressources du tel ( image, contact ...)
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT

        //demarre une instruction et attend un resultat ici recuperer l image
        //request code = numero de l action faite
        startActivityForResult(Intent.createChooser(intent,"Select picture"),47)
    }

    //recolter les infos apres avoir recu le resultat

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        //Si le user a bien recupere une image
        if (requestCode == 47 && resultCode == Activity.RESULT_OK){

            //verifier si les data receptionnee sont nulles
                //si elles sont nulles , on arrete

            if(data == null || data.data ==null) return

            //sinon on recupere bien l image selectionnée
            val file = data.data


            //MAJ aperçu de l image
            uploadedImage?.setImageURI(file)

            /*heberger image sur le bucket

            val repo = PlantRepository()
            // !! = bypass = j execute meme s il y a des erreurs
            repo.uploadImage(selectedImage!!)*/

        }

    }


}