package com.example.contactapp

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageHelper {
    companion object {
        fun loadImageByUri(avatarUri:String?, view:View, avatar:ImageView) {
            avatar.setImageBitmap(null)
            if (avatarUri != null) {
                Glide
                    .with(view)
                    .load(avatarUri)
                    .into(avatar)
            } else {
                Glide
                    .with(view)
                    .load(R.drawable.ic_user_colored)
                    .into(avatar)
            }
        }
    }
}