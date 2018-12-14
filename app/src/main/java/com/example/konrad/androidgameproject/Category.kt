package com.example.konrad.androidgameproject


import android.content.res.Resources
import android.os.Bundle
import android.provider.Contacts
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_select_categories.*

class Category : AppCompatActivity() {
    internal var areCharactersOn = true
    internal var areVehiclesOn = false
    internal var arePlanetsOn = false
    internal var areSpeciesOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_categories)

        val active = resources.getDrawable(R.drawable.active)
        val disabled = resources.getDrawable(R.drawable.disabled)

        //Characters
        if (areCharactersOn) {
            isActiveCharacters.setBackground(active)
        } else if (!areCharactersOn) {
            isActiveCharacters.setBackground(disabled)
        }
        //Vehicles
        if (areVehiclesOn) {
            isActiveVehicles.setBackground(active)
        } else if (!areVehiclesOn) {
            isActiveVehicles.setBackground(disabled)
        }
        //Planets
        if (arePlanetsOn) {
            isActivePlantes.setBackground(active)
        } else if (!arePlanetsOn) {
            isActivePlantes.setBackground(disabled)
        }
        //Species
        if (areSpeciesOn) {
            isActiveSpecies.setBackground(active)
        } else if (!areSpeciesOn) {
            isActiveSpecies.setBackground(disabled)
        }
    }

    fun onClickCategoryCharacters(view: View) {
        if (!areCharactersOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActiveCharacters.setBackground(active)
            areCharactersOn = true
        } else if (areCharactersOn) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActiveCharacters.setBackground(disabled)
            areCharactersOn = false
        }
    }

    fun onClickCategoryVehicles(view: View) {
        if (!areVehiclesOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActiveVehicles.setBackground(active)
            areVehiclesOn = true
        } else if (areCharactersOn) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActiveVehicles.setBackground(disabled)
            areVehiclesOn = false
        }
    }

    fun onClickCategoryPlanets(view: View) {
        if (!arePlanetsOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActivePlantes.setBackground(active)
            arePlanetsOn = true
        } else if (arePlanetsOn) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActivePlantes.setBackground(disabled)
            arePlanetsOn = false
        }
    }

    fun onClickCategorySpecies(view: View) {
        if (!areSpeciesOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActiveSpecies.setBackground(active)
            areSpeciesOn = true
        } else if (areSpeciesOn) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActiveSpecies.setBackground(disabled)
            areSpeciesOn = false
        }
    }

    fun onClickResetToDefault(view: View) {
        areCharactersOn = true
        areVehiclesOn = false
        arePlanetsOn = false
        areSpeciesOn = false
        val active = resources.getDrawable(R.drawable.active)
        val disabled = resources.getDrawable(R.drawable.disabled)

        isActiveCharacters.setBackground(active)
        isActiveVehicles.setBackground(disabled)
        isActivePlantes.setBackground(disabled)
        isActiveSpecies.setBackground(disabled)
    }
}