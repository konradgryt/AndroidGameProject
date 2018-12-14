package com.example.konrad.androidgameproject.GUI


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.konrad.androidgameproject.R
import kotlinx.android.synthetic.main.activity_select_categories.*

class Category : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_categories)

        val active = resources.getDrawable(R.drawable.active)
        val disabled = resources.getDrawable(R.drawable.disabled)

        numberOfActiveCategories = 0

        //Characters
        if (areCharactersOn) {
            isActiveCharacters.setBackground(active)
            numberOfActiveCategories++
        } else if (!areCharactersOn) {
            isActiveCharacters.setBackground(disabled)
        }
        //Vehicles
        if (areVehiclesOn) {
            isActiveVehicles.setBackground(active)
            numberOfActiveCategories++
        } else if (!areVehiclesOn) {
            isActiveVehicles.setBackground(disabled)
        }
        //Planets
        if (arePlanetsOn) {
            isActivePlantes.setBackground(active)
            numberOfActiveCategories++
        } else if (!arePlanetsOn) {
            isActivePlantes.setBackground(disabled)
        }
        //Species
        if (areSpeciesOn) {
            isActiveSpecies.setBackground(active)
            numberOfActiveCategories++
        } else if (!areSpeciesOn) {
            isActiveSpecies.setBackground(disabled)
        }
    }

    fun onClickCategoryCharacters(view: View) {
        playAnimationOnView(isActiveCharacters, Techniques.Pulse)
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
        playAnimationOnView(isActiveVehicles, Techniques.Pulse)
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
        playAnimationOnView(isActivePlantes, Techniques.Pulse)
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
        playAnimationOnView(isActiveSpecies, Techniques.Pulse)
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
        numberOfActiveCategories = 1
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

    private fun playAnimationOnView(view: View, technique: Techniques) {
        if (Options.areAnimationsOn) {
            YoYo.with(technique).duration(500).repeat(0).playOn(view)
        }
    }

    companion object {
        internal var numberOfActiveCategories = 1
        internal var areCharactersOn = true
        internal var areVehiclesOn = false
        internal var arePlanetsOn = false
        internal var areSpeciesOn = false

        fun getActiveCategories(): Array<String> {
            val array: Array<String> = Array(numberOfActiveCategories){
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