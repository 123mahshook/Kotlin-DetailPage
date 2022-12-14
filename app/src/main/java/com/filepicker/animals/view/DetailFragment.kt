package com.filepicker.animals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.filepicker.animals.R
import com.filepicker.animals.model.Users
import com.filepicker.animals.util.getProgressDrawable
import com.filepicker.animals.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    var users:Users?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            users=DetailFragmentArgs.fromBundle(it).animal
        }
        context?.let {
            animalImage.loadImage(users?.userImage, getProgressDrawable(it))
        }

        animalName.text=users?.firstName
        animalLocation.text=users?.lastName
        animalLifespan.text=users?.email
        animalDiet.text=users?.firstName
        users?.userImage?.let {
        setupBackgroundColor(it)
        }


    }

    private fun setupBackgroundColor(url:String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object :CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate(){
                            palette->
                            val intColor=palette?.lightMutedSwatch?.rgb?:0
                            animalLayout.setBackgroundColor(intColor)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })

    }
    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonList.setOnClickListener{
            val action=DetailFragmentDirections.actionList()
Navigation.findNavController(it).navigate(action)}}*/
}