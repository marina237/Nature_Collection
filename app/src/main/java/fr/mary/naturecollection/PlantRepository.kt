package fr.mary.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.mary.naturecollection.PlantRepository.Singleton.databaseRef
import fr.mary.naturecollection.PlantRepository.Singleton.downloadUri
import fr.mary.naturecollection.PlantRepository.Singleton.plantList
import fr.mary.naturecollection.PlantRepository.Singleton.storageReference
import java.net.URI
import java.util.*
import javax.security.auth.callback.Callback

class PlantRepository {


    //permet de ne pas créer une nouvelle liste de plantes a chaque fois qu on lance l app
    //en gros ca va juste actualiser la liste sans en creer une nouv
    object Singleton{

        //donner le lien pour accéder au bucket
        private val BUCKET_URL: String = "gs://naturecollection-f0dd3.appspot.com"

        //se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // Se connecter a la reference "plants" sur firebase
        val databaseRef = FirebaseDatabase.getInstance("https://naturecollection-f0dd3-default-rtdb.firebaseio.com/").getReference("plants")

        //creation liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        //contient le lien de l image courante
        var downloadUri : Uri? = null

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

    //créer une fonction pr envoyer des fichiers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit){
        //vérifier que ce fichier n est pas null
        if (file != null){
            //générer un nom de fichier alea pr éviter les doublons
            val fileName = UUID.randomUUID().toString() + ".jpg"

            //chemin de l endroit où on va enregistrer la ressource ds la BDD
            val ref = storageReference.child(fileName)

            // envoi le fichier dans une tache d envoi
            val uploadTask = ref.putFile(file)

            //demarrer la tache d envoi
            //Snapshot = nouv data qui vient s enregistrer
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                //si il y a eu un pblm lors de l envoi du fichier
                if (!task.isSuccessful){
                    //let : permet d envoyer l erreur
                        //throw pr déclencher l exception
                            // ? : si it est null , on declenchera pas l exception
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl

            }).addOnCompleteListener{task ->
                // vérifier si tout a bien fonctionné
                if (task.isSuccessful){
                    //récupérer l image
                     downloadUri = task.result

                    callback()
                }
            }
        }

    }
    //MAJ objet plante en BDD
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //insérer une nouvelle plante dans la BDD
    fun inserPlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)


    //supprimer une plante de la BDD
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()
}