package ua.kiev.prog.case1;

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
            ClientDAO dao = new ClientDAOImpl(conn);
            dao.init();

            while (true) {
                System.out.println("1: add client");
                System.out.println("2: view clients");
                System.out.println("3: view count");
                System.out.println("4: update client");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        System.out.print("Enter client name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter client age: ");
                        String sAge = sc.nextLine();
                        int age = Integer.parseInt(sAge);

                        dao.addClient(name, age);
                        break;
                    case "2":
                        List<Client> list = dao.getAll();
                        for (Client client : list) {
                            System.out.println(client);
                        }
                        break;
                    case "3":
                        System.out.println(dao.count());
                        break;
                    case "4":
                        System.out.println("Enter client id to update");
                        String clientIdStr = sc.nextLine();
                        int clientId = Integer.parseInt(clientIdStr);
                        System.out.print("To update client name, enter new name, else enter '/' : ");
                        String newName = sc.nextLine();
                        System.out.print("To update client age, enter new age, else enter '-1' : ");
                        String newAgeStr = sc.nextLine();
                        int newAge = Integer.parseInt(newAgeStr);
                        Map<String, Object> newValues = new HashMap<>();
                        if (newName != "/") newValues.put("name", newName);
                        if (newAge != -1) newValues.put("age", newAge);
                        dao.updateClient(clientId, newValues);
                        break;
                    default:
                        return;
                }
            }
        } finally {
            sc.close();
            if (conn != null) conn.close();
        }
    }
}
