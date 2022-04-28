package fr.mary.naturecollection

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.mary.naturecollection.PlantRepository.Singleton.databaseRef
import fr.mary.naturecollection.PlantRepository.Singleton.plantList
import javax.security.auth.callback.Callback

class PlantRepository {


    //permet de ne pas créer une nouvelle liste de plantes a chaque fois qu on lance l app
    //en gros ca va juste actualiser la liste sans en creer une nouv
    object Singleton{

        // Se connecter a la reference "plants" sur firebase
        val databaseRef = FirebaseDatabase.getInstance("https://naturecollection-f0dd3-default-rtdb.firebaseio.com/").getReference("plants")

        //creation liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

    }

    //Unit = liste d instruction
    fun updateData(callback:()->Unit){
        //absorber les données depuis la database pr ensuite lls donner a notre liste de plantes "plantList"
        //Event Listener = evenement sur lequel on va ecouter --> ici qd les data vont etre MAJ

        databaseRef.addValueEventListener(object : ValueEventListener{


            //Snapshot : obj contenant toute les data recuperee sur la forme d'une LISTE D OBJET --> pas encore plantObject
            override fun onDataChange(snapshot: DataSnapshot) {

                //on retire les plantes qui étaient dans ma liste
                plantList.clear()


                //recolter la liste
                //children = UN objet de la liste
                for (ds in snapshot.children){
                    //construire un obj plante
                    val plant = ds.getValue(PlantModel ::  class.java)

                    //verifier que la plante n est pas null --> ie qu elle a bien ete chargee
                    if (plant != null){
                        plantList.add(plant)
                    }


                 }

                //actionner le callback
                callback()

            }

            //si on triuve pas les elements
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }


//MAJ objet plante en BDD
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

}