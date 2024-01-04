package com.kiudysng.ddproject.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiudysng.ddproject.AiArtApplication
import com.kiudysng.ddproject.db.AiDbBase
import com.kiudysng.ddproject.entity.TemplateStyleEntityItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeneratedPicViewModel : ViewModel() {
    val templateStyles = MutableLiveData<List<TemplateStyleEntityItem>>()
    val dao = AiDbBase.getDb(AiArtApplication.appContext).dao()
    fun getTemplateStyle(){
        viewModelScope.launch (Dispatchers.IO){
            val templateStyle =  dao.getTemplateStyle()
            if(templateStyle.isNotEmpty()){
                templateStyles.postValue(templateStyle)
            }
        }
    }
}