package ua.kiev.prog.case1;

import ua.kiev.prog.shared.Client;

import java.util.List;
import java.util.Map;

public interface ClientDAO {
    void init();
    void addClient(String name, int age);
    void updateClient(int id, Map<String, Object> newValues);
    void deleteClient(int id);
    List<Client> getAll();
    long count();
}
