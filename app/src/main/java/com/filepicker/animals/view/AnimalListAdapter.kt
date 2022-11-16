package com.filepicker.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.filepicker.animals.R
import com.filepicker.animals.model.Users
import com.filepicker.animals.util.getProgressDrawable
import com.filepicker.animals.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val userList:ArrayList<Users>):
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(var view:View):RecyclerView.ViewHolder(view)

fun updateAnimalList(newUserList:List<Users>){
    userList.clear()
    userList.addAll(newUserList)
    notifyDataSetChanged()
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val  view=inflater.inflate(R.layout.item_animal,parent,false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
    holder.view.animalName.text =userList[position].firstName
        holder.view.animalImage.loadImage(userList[position].userImage, getProgressDrawable(holder.view.context))
        holder.view.animalLayout.setOnClickListener{
            val action = ListFragmentDirections.actionDetail(userList[position])
            Navigation.findNavController(holder.view).navigate(action)

        }

    }

    override fun getItemCount()=userList.size


}