package fr.mary.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.mary.naturecollection.*


//Classe permettant d adapter a chaque plante son image
class PlantAdapter(

    //Constructeur
    val context : MainActivity,
    private val plantList : List<PlantModel>,
    private val layoutId:Int



) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    //Boîte pour ranger tous les composants à contrôler
    //ViewHolder hérite de RecyclerView
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //val = constante qui va recuperer la view de la plante
        val plantImage: ImageView = view.findViewById<ImageView>(R.id.image_item)
        //Recup nom + description de la plante --> recup le nom qui est dans le fichier xml

        // ? : si on ne trouve pas de texte view on fait rien
        //car dans les name_item horizontaux on a pas de text
        val plantName : TextView? = view.findViewById(R.id.name_item)
        val plantDescription : TextView? = view.findViewById(R.id.description_item)
        val starIcon: ImageView = view.findViewById<ImageView>(R.id.star_icon)
    }

    //Permet d injecter le layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //génération de la view
        val view = LayoutInflater
            .from(parent.context)
                .inflate(layoutId,parent,false)

        //On retourne la view crée
        return ViewHolder(view)
    }


    //MAJ du modèle
    //s execute pr chaque element d une liste
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //recuperation ds infos de la plante i

        val currentPlant = plantList[position]

        //recup le repository

        val repo = PlantRepository()




        //utiliser glide pr recuperer l image a partir de son lien et l ajouter a notre composant
        //context = BDD interne contenant les data de l app (version app, ...)

        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //MAJ du nom de la plante
        //ie que le plantName du xml prendra la valeur de la plante actuelle qui est dans le PlantModel
        holder.plantName?.text = currentPlant.name

        //MAJ Description de la plante
        holder.plantDescription?.text = currentPlant.description

        //verifier si la plante a été liké ou non
        if(currentPlant.liked){
            //on remplace l icon initiale par l icon "like"
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        //ajout interaction sur l etoile onclick
        holder.starIcon.setOnClickListener{
            //inverser si le bouton est "like" ou "unlike"
            //si la plante est like initialement , au clic elle prend l etat inverse soit unlike
            currentPlant.liked=!currentPlant.liked

            //MAJ l objet plante
            repo.updatePlant(currentPlant)

        }

        //Interaction pour afficher le popup lors du clic sur une plante
        holder.itemView.setOnClickListener{

            //affichage de la popup
            PlantPopup(this,currentPlant).show()

        }


    }

    //Nb item qu on va renvoyer
    //Génère ici 5 images de horizontal Plant
    override fun getItemCount(): Int = plantList.size


}

