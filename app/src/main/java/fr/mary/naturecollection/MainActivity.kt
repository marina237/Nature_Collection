package fr.mary.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.mary.naturecollection.fragments.HomeFragment

/*Activité = Morceau de l application*/
/*Main activité = Page par défault*/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //charger notre PlantRepository

        val repo = PlantRepository()

        //MAJ la liste de plantes
        //instruction a faire apres avoir récupéré les data
        repo.updateData{

            /*injection du fragment dans notre boite (fragment container)*/
            val transaction = supportFragmentManager.beginTransaction()
            /*this : permet de placer l activité courante en parametre afin de l afficher*/
            transaction.replace(R.id.fragment_container,HomeFragment(this))
            /* empeche le retour sur le composant initial */
            transaction.addToBackStack(null)
            /*commit : pr envoyer les changements*/
            transaction.commit()


        }


    }
}