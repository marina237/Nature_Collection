package fr.mary.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.mary.naturecollection.fragments.AddPlantFragment
import fr.mary.naturecollection.fragments.CollectionFragment
import fr.mary.naturecollection.fragments.HomeFragment

/*Activité = Morceau de l application*/
/*Main activité = Page par défault*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //page affichée par défaut
        loadFragment(HomeFragment(this),R.string.home_page_title)

        //importation bottomNavigationView
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener{

            //switch en java
            when(it.itemId)
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this),R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page ->{
                    loadFragment(CollectionFragment(this),R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plant_page ->{
                    loadFragment(AddPlantFragment(this),R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }


                else -> {false}
            }

        }




    }

    //charger le fragment  et l afficher
    private fun loadFragment(fragment: Fragment, string : Int ) {

        //charger notre PlantRepository
        val repo = PlantRepository()

        //recuperer le titre de la page

        findViewById<TextView>(R.id.page_title).text= resources.getString(string)

        //MAJ la liste de plantes
        //instruction a faire apres avoir récupéré les data
        repo.updateData{

            /*injection du fragment dans notre boite (fragment container)*/
            val transaction = supportFragmentManager.beginTransaction()

            /*this : permet de placer l activité courante en parametre afin de l afficher*/
            transaction.replace(R.id.fragment_container,fragment)

            /* empeche le retour sur le composant initial */
            transaction.addToBackStack(null)

            /*commit : pr envoyer les changements*/
            transaction.commit()


        }


    }
}