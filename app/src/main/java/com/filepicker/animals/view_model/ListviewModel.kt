package com.filepicker.animals.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.filepicker.animals.model.*
import com.filepicker.animals.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListviewModel(application: Application):AndroidViewModel(application) {
    val users by lazy {MutableLiveData<List<Users>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy{MutableLiveData<Boolean>()}

    private val disposable=CompositeDisposable()
    private val apiService=AnimalApiService()

    private val prefs=SharedPreferencesHelper(getApplication())

    private var invalidApiKey=false

    fun refresh(){
        loading.value=true
        getKey()
     /*   invalidApiKey=false
        val key=prefs.getApiKey()
        if(key.isNullOrEmpty()){
            getKey()
        }else {
            getAnimals(key)
        }*/

        //getAnimals()
    }

    fun hardRefresh(){
        loading.value=true
        getKey()
    }

    private  fun getKey(){
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<Page>(){
                    /*override fun onSuccess(key: ApiKey) {
                        if(key.key.isNullOrEmpty()){
                            loadError.value=true
                            loading.value=false
                        }else{
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value=false
                        loadError.value=true

                    }*/
                    override fun onSuccess(page: Page) {
                        loadError.value=false
                        users.value=page.userData
                        loading.value=false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value=false
                        users.value=null
                        loadError.value=true
                    }

                })
        )

    }

    /*private fun getAnimals(key:String){
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value=false
                        animals.value=list
                        loading.value=false

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        if(!invalidApiKey){
                            invalidApiKey=true
                            getKey()
                        }else{
                            loading.value=false
                            animals.value=null
                            loadError.value=true
                        }

                    }

                })
        )
    }*/

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
/*val a1=Animal("aligator")
        val a2=Animal("bee")
        val a3=Animal("cat")
        val a4=Animal("dog")
        val a5=Animal("elephant")
        val a6=Animal("flaming")

        val animalList= arrayListOf(a1,a2,a3,a4,a5,a6)

        animals.value=animalList
        loadError.value=false
        loading.value=false
    }*/
