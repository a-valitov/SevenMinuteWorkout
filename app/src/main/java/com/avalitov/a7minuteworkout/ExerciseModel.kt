package com.avalitov.a7minuteworkout

class ExerciseModel(private var id: Int,
                    private var name: String,
                    private var imageNumber: Int,
                    private var isCompleted: Boolean,
                    private var isSelected: Boolean) {

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImageNumber(): Int {
        return imageNumber
    }

    fun setImageNumber(imageNumber: Int) {
        this.imageNumber = imageNumber
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

}