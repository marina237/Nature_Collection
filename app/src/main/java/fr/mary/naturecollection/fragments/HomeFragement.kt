package fr.mary.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.mary.naturecollection.MainActivity

import fr.mary.naturecollection.PlantRepository.Singleton.plantList
import fr.mary.naturecollection.R
import fr.mary.naturecollection.adapter.PlantAdapter
import fr.mary.naturecollection.adapter.PlantItemDecoration


class HomeFragment(
    //constructeur

    private val context : MainActivity
) : Fragment(){

    //Permet d injecter ton layout fragment home --> va injecter la view du layout home
    //inflate = injecter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home,container,false)




        //enregistre premiere plante ds la liste (pissenlit) --> mtn c dans la BDD firebase


        //recuperation du premier recyclerView ( il contient l "item" horizontal )
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)

        //va charger les plantes --> en fait il va appeler la class PlantAdapter(type d item) afin de générer les 5 items
        horizontalRecyclerView.adapter = PlantAdapter(context,plantList.filter { !it.liked },R.layout.item_horizontal_plant)


        //recuperation du second recyclerView ( il contient l "item" vertical)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        //en fait il va appeler la class PlantAdapter(type d item) afin de générer les 5 items
        verticalRecyclerView.adapter = PlantAdapter(context,plantList,R.layout.item_vertical_plant)

        //class en parametre
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        //Ajout home layout sur la page
        return view
    }



}