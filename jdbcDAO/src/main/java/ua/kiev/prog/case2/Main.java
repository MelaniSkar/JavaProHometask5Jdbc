package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/dao";
    static final String DB_USER = "**";
    static final String DB_PASSWORD = "**";

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory(
                DB_CONNECTION, DB_USER, DB_PASSWORD
        );

        Connection conn = factory.getConnection();
        try {
            ClientDAOEx dao = new ClientDAOEx(conn, "clients");

            Client c = new Client("test", 1);
            dao.add(c);

            List<Client> list = dao.getAll(Client.class);
            printClients(list);
            c = list.get(0);
            c.setName("newName");
            c.setAge(10);
            dao.update(c);

            dao.delete(list.get(0));
        } finally {
            sc.close();
            if (conn != null) conn.close();
        }
    }

    private static void printClients(List<Client> clients) {
        for (Client cli : clients)
            System.out.println(cli);
    }
}
