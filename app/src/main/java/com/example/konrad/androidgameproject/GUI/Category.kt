package com.example.konrad.androidgameproject.GUI


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.konrad.androidgameproject.R
import kotlinx.android.synthetic.main.activity_select_categories.*

class Category : AppCompatActivity() {
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
        numberOfActiveCategories = 1
    }

    fun onClickCategoryCharacters(view: View) {
        if (!areCharactersOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActiveCharacters.setBackground(active)
            areCharactersOn = true
            numberOfActiveCategories++
        } else if (areCharactersOn && numberOfActiveCategories > 1) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActiveCharacters.setBackground(disabled)
            areCharactersOn = false
            numberOfActiveCategories--
        }
    }

    fun onClickCategoryVehicles(view: View) {
        if (!areVehiclesOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActiveVehicles.setBackground(active)
            areVehiclesOn = true
            numberOfActiveCategories++
        } else if (areVehiclesOn && numberOfActiveCategories > 1) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActiveVehicles.setBackground(disabled)
            areVehiclesOn = false
            numberOfActiveCategories--
        }
    }

    fun onClickCategoryPlanets(view: View) {
        if (!arePlanetsOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActivePlantes.setBackground(active)
            arePlanetsOn = true
            numberOfActiveCategories++
        } else if (arePlanetsOn && numberOfActiveCategories > 1) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActivePlantes.setBackground(disabled)
            arePlanetsOn = false
            numberOfActiveCategories--
        }
    }

    fun onClickCategorySpecies(view: View) {
        if (!areSpeciesOn) {
            val active = resources.getDrawable(R.drawable.active)
            isActiveSpecies.setBackground(active)
            areSpeciesOn = true
            numberOfActiveCategories++
        } else if (areSpeciesOn && numberOfActiveCategories > 1) {
            val disabled = resources.getDrawable(R.drawable.disabled)
            isActiveSpecies.setBackground(disabled)
            areSpeciesOn = false
            numberOfActiveCategories--
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



    companion object {
        internal var numberOfActiveCategories = 1
        internal var areCharactersOn = true
        internal var areVehiclesOn = false
        internal var arePlanetsOn = false
        internal var areSpeciesOn = false

        fun getActiveCategories(): Array<String> {
            var array: Array<String> = Array(numberOfActiveCategories){
                i -> ""
            }
//        for (index in 0..numberOfActiveCategories) {
//            array.set(index, )
//        }
            var index = 0
            if (areCharactersOn) {
                array.set(index, "person")
                index++
            }
            if (areVehiclesOn) {
                array.set(index, "vehicle")
                index++
            }
            if (arePlanetsOn) {
                array.set(index, "planet")
                index++
            }
            if (areSpeciesOn) {
                array.set(index, "species")
                index++
            }
            return array
        }
    }
}