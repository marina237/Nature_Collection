package fr.mary.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.mary.naturecollection.MainActivity
import fr.mary.naturecollection.PlantRepository.Singleton.plantList
import fr.mary.naturecollection.R
import fr.mary.naturecollection.adapter.PlantAdapter
import fr.mary.naturecollection.adapter.PlantItemDecoration

class CollectionFragment(
    private val context : MainActivity

) : Fragment() {
    //instruction a la creation de la view

    //injection du fichier xml contenant les infos a injecter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
      val view = inflater.inflate(R.layout.fragment_collection, container, false)
        //recuperer la recycler view
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recylcler_list)
        //ajout du composant dans la view
        //filter : ici pour afficher que les items liked
        collectionRecyclerView.adapter = PlantAdapter(context, plantList.filter{ it.liked },R.layout.item_vertical_plant)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)

        //ajout espace entre les item
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())



        return view
    }

}