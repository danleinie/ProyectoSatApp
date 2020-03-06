package com.example.proyectosataapp.usuarios;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.models.DtoName;
import com.example.proyectosataapp.models.PasswordRequest;
import com.example.proyectosataapp.models.UserResponseRegister;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository repo;
    private MutableLiveData<List<UserResponseRegister>> listUsersNoValidated = new MutableLiveData<>();
    private MutableLiveData<List<UserResponseRegister>> listUsers = new MutableLiveData<>();
    private MutableLiveData<Integer> sizeListUsersNoValidated = new MutableLiveData<>();
    private MutableLiveData<UserResponseRegister> newUserValidated = new MutableLiveData<>();
    private MutableLiveData<UserResponseRegister> userLogeado = new MutableLiveData<>();
    private MutableLiveData<String> idUserToRemove = new MutableLiveData<>();

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        repo = new UsuarioRepository();

    }
    public LiveData<List<UserResponseRegister>> getUsersFromRepo(){return repo.getUsers();}
    public LiveData<List<UserResponseRegister>> getUsersNoValidatedFromRepo(){return repo.getUsersNoValidated();}
    public LiveData<ResponseBody> getImg(String idUser){return repo.getImg(idUser);}
    public LiveData<UserResponseRegister> validarUsuario(String idUser){return repo.validarUser(idUser);}
    public void setListUsersNoValidated(List<UserResponseRegister> listUsersNoValidated){this.listUsersNoValidated.postValue(listUsersNoValidated);}
    public LiveData<List<UserResponseRegister>> getUsersNoValidated(){return this.listUsersNoValidated;}
    public void setListUsers(List<UserResponseRegister> listUsers){this.listUsers.postValue(listUsers);}
    public LiveData<List<UserResponseRegister>> getUsers(){return this.listUsers;}
    public void setSizeListUsersNoValidated(Integer size){ this.sizeListUsersNoValidated.postValue(size); }
    public MutableLiveData<Integer> getSizeListUsersNoValidated(){return this.sizeListUsersNoValidated;}
    public void setNewUserValidated(UserResponseRegister newUserValidated){this.newUserValidated.postValue(newUserValidated);}
    public LiveData<UserResponseRegister> getNewUserValidated(){return this.newUserValidated;}
    public LiveData<String> getUserIdDeleted(){return this.idUserToRemove;}
    public LiveData<UserResponseRegister> changeToTecnico(String idUser){return repo.changeToTecnico(idUser);}
    public LiveData<UserResponseRegister> getUser(String idUser){return repo.getUser(idUser);}
    public LiveData<UserResponseRegister> getUserLogeadoFromRepo(){return repo.getUserLogeado();}
    public void setUserLogeado(UserResponseRegister userLogeado){ this.userLogeado.postValue(userLogeado);}
    public LiveData<UserResponseRegister> getUserLogeado(){return this.userLogeado;}
    public LiveData<UserResponseRegister> updatePhoto(String idUser, MultipartBody.Part avatar){return repo.updatePhoto(idUser,avatar);}
    public LiveData<UserResponseRegister> updatePassword(String autHeader, String idUser, PasswordRequest password){return repo.updatePassword(autHeader,idUser,password);}
    public LiveData<UserResponseRegister> deletePhoto(String idUser){return repo.deletePhoto(idUser);}
    public LiveData<UserResponseRegister> changeName(String idUser, DtoName newName){return repo.changeName(idUser,newName);}
    public LiveData<Boolean> deleteUserFromRepo(String idUser){
        this.idUserToRemove.postValue(idUser);
        return repo.deleteUser(idUser);
    }

}
