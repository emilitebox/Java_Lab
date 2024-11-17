package dev.emiliomartinez.bugtracker.dao;
import java.util.List;
import java.util.Optional;

public interface IDAO<T> {
    Optional<T> obtenerPorId(Integer id);
    List<T> obtenerTodos();
    void guardar(T t);
    void actualizar(T t);
    void eliminar(Integer id);
}
