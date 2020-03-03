package com.example.proyectosataapp.usuarios;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.models.UserResponseRegister;

import java.util.List;

import okhttp3.ResponseBody;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository repo;
    private MutableLiveData<Boolean> refreshListsUsers = new MutableLiveData<>();
    private MutableLiveData<List<UserResponseRegister>> listUsersNoValidated = new MutableLiveData<>();
    private MutableLiveData<List<UserResponseRegister>> listUsers = new MutableLiveData<>();
    private MutableLiveData<Integer> sizeListUsersNoValidated = new MutableLiveData<>();

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        repo = new UsuarioRepository();

    }
    public MutableLiveData<Boolean> isRefreshListUsers(){return this.refreshListsUsers;}
    public LiveData<List<UserResponseRegister>> getUsersFromRepo(){return repo.getUsers();}
    public LiveData<List<UserResponseRegister>> getUsersNoValidatedFromRepo(){return repo.getUsersNoValidated();}
    public LiveData<ResponseBody> getImg(String idUser){return repo.getImg(idUser);}
    public LiveData<UserResponseRegister> validarUsuario(String idUser){return repo.validarUser(idUser);}
    public void isRefreshListUsers(boolean refresh){
        this.refreshListsUsers.postValue(refresh);
    }
    public void setListUsersNoValidated(List<UserResponseRegister> listUsersNoValidated){this.listUsersNoValidated.postValue(listUsersNoValidated);}
    public LiveData<List<UserResponseRegister>> getUsersNoValidated(){return this.listUsersNoValidated;}
    public void setListUsers(List<UserResponseRegister> listUsers){this.listUsers.postValue(listUsers);}
    public LiveData<List<UserResponseRegister>> getUsers(){return this.listUsers;}
    public void setSizeListUsersNoValidated(Integer size){ this.sizeListUsersNoValidated.postValue(size); }
    public MutableLiveData<Integer> getSizeListUsersNoValidated(){return this.sizeListUsersNoValidated;}

}
