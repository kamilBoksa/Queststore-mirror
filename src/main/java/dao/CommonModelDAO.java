package dao;

import model.CommonModel;

public interface CommonModelDAO<T extends CommonModel> {

    boolean saveModel(T t);

}
