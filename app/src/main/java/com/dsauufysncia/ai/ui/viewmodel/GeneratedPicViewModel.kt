package com.dsauufysncia.ai.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsauufysncia.ai.db.AiDbBase
import com.dsauufysncia.ai.entity.TemplateStyleEntityItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeneratedPicViewModel : ViewModel() {
    val templateStyles = MutableLiveData<List<TemplateStyleEntityItem>>()
    val dao = AiDbBase.getDb(com.dsauufysncia.ai.AiArtApplication.appContext).dao()
    fun getTemplateStyle(){
        viewModelScope.launch (Dispatchers.IO){
            val templateStyle =  dao.getTemplateStyle()
            if(templateStyle.isNotEmpty()){
                templateStyles.postValue(templateStyle)
            }
        }
    }
}