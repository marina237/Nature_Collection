package fr.mary.naturecollection.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PlantItemDecoration : RecyclerView.ItemDecoration() {

    //permet de créer l espace entre les items verticaux
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
       outRect.bottom=20
    }
}